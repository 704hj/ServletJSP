package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.util.CaptchaUtil;
import kr.spring.util.FileUtil;
import kr.spring.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {
	@Value("${dataconfig.naver-client-id}")
	private String naver_client_id;
	@Value("${dataconfig.naver-client-secret}")
	private String naver_client_secret;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	//자바빈 초기화 필수 왜? 폼 데이터를 입력할 때 빈 객체를 미리 만듦 (만약 폼에서 아무 값도 입력되지 않았을 때, null 대신 빈 객체가 전달되므로 에러(예: NullPointerException)를 방지)
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	/*=============================================
	 * 회원가입
	 *=============================================*/
	//회원가입 폼 호출
	@GetMapping("/registerUser")
	public String form() {
		return "memberRegister";
	}
	//회원가입 데이터 전송
	@PostMapping("/registerUser")							//Model model : Request에 데이터 저장용도-> 데이터를 뷰로 전달
	public String submit(@Valid MemberVO memberVO, BindingResult result,Model model,HttpServletRequest request) {
		log.debug("<<회원가입 - MemberVO>> : " + memberVO);
		
		//전송된 데이터 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			ValidationUtil.printErrorFields(result);
			return form();
		}
		
		//Spring Security 암호화
		memberVO.setPasswd(passwordEncoder.encode(memberVO.getPasswd()));
		
		
		//회원가입
		memberService.insertMember(memberVO);
		
		//UI 메시지 처리
		model.addAttribute("accessTitle", "회원가입");
		model.addAttribute("accessMsg", "회원가입이 완료되었습니다");
		model.addAttribute("accessBtn", "홈으로");
		model.addAttribute("accessUrl", request.getContextPath()+"/main/main");
		
		return "common/resultView";
	}
	/*=============================================
	 * 회원로그인
	 *=============================================*/
	//로그인 폼
	@GetMapping("/login")
	public String formLogin(MemberVO memberVO, BindingResult result, HttpServletRequest request) {//Map<String,?> = Map<String,Object>
		Map<String,?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap != null) {
			String error = (String)flashMap.get("error");
			log.debug("<<로그인 체크 - error>> : "+ error);
			if("username".equals(error)) {
				result.rejectValue("id","Pattern.id","아이디를 입력하세요");
			}
			if("password".equals(error)) {
				result.rejectValue("passwd", "Pattern.passwd","비밀번호를 입력하세요");
			}
			if("username_password".equals(error)) {
				result.rejectValue("id","Pattern.id","아이디를 입력하세요");
				result.rejectValue("passwd", "Pattern.passwd","비밀번호를 입력하세요");
			}
			if("error".equals(error)) {
				result.reject("invalidIdOrPassword");
			}
		}
		return "memberLogin";
	}
	
	/*=============================================
	 * 마이페이지
	 *=============================================*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/myPage")
	public String getMyPage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
		//회원정보
		MemberVO member = memberService.selectMember(principalDetails.getMemberVO().getMem_num());
		
		log.debug("<<회원상세 정보>> : " + member);
		
		model.addAttribute("member",member);
		
		return "myPage";
	}
	/*=============================================
	 * 프로필 사진 출력
	 *=============================================*/
	//프로필 사진 출력(로그인 전용)
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/photoView")
	public String getProfile(@AuthenticationPrincipal PrincipalDetails principalDetails,HttpServletRequest request, Model model) {
		MemberVO user = principalDetails.getMemberVO();
		log.debug("<<photoView>> :" + user);
		if(user==null) {//로르인이 되지 않은 경우
			getBasicProfileImage(request,model);
			
		}else {//로그인 된 경우
			
			MemberVO memberVO = memberService.selectMember(user.getMem_num());
			viewProfile(memberVO,request,model);
		}
				return "imageView";
	}
	
	
	//프로필 사진 출력(회원번호 지정)
	@GetMapping("/viewProfile")
	public String getProfileByMem_num(long mem_num,HttpServletRequest request,Model model) {
		MemberVO memberVO = memberService.selectMember(mem_num);
			viewProfile(memberVO, request, model);
			
		return "imageView";
	}
	
	
	//프로필 사진 처리를 위한 공통 코드
	public void viewProfile(MemberVO memberVO,HttpServletRequest request,Model model) {
		if(memberVO==null || memberVO.getPhoto_name()==null) {
			//DB에 저장된 프로필 이미지가 없기 때문에 기본 이미지 호출
			getBasicProfileImage(request, model);
		}else {//업로드한 프로필 이미지 읽기
			model.addAttribute("imageFile", memberVO.getPhoto());
			model.addAttribute("filename", memberVO.getPhoto_name());
		}
		
	}
	//기본 이미지 읽기
	public void getBasicProfileImage(HttpServletRequest request, Model model) {
		byte[] readbyte = FileUtil.getBytes(request.getServletContext().getRealPath("/assets/image_bundle/face.png"));
		model.addAttribute("imageFile",readbyte);
		model.addAttribute("filename","face.png");//("filename","A") A로 파일 지정 
	}
	
	/*============================
	 * 회원정보 수정
	 *============================*/
	//수정폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/update")
	public String formUpdate(@AuthenticationPrincipal PrincipalDetails principal, Model model) {
		MemberVO memberVO = memberService.selectMember(principal.getMemberVO().getMem_num());
		//소설 로그인시 아래 내용에 공백이 들어가 있어 제거
		memberVO.setPhone(memberVO.getPhone().trim());
		memberVO.setZipcode(memberVO.getZipcode().trim());
		memberVO.setAddress1(memberVO.getAddress1().trim());
		memberVO.setAddress2(memberVO.getAddress2().trim());
		
		model.addAttribute("memberVO", memberVO);
		
		return "memberModify";
	}
	
	//수정폼에서 전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update")
	public String submitUpdate(@Valid MemberVO memberVO,BindingResult result,@AuthenticationPrincipal PrincipalDetails principal) {
		
		log.debug("<<회원정보 수정>> : " +memberVO);
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			ValidationUtil.printErrorFields(result);
			return "memberModify";
		}
		
		memberVO.setMem_num(principal.getMemberVO().getMem_num());
		//회원정보 수정
		memberService.updateMember(memberVO);
		
		//PrincipalDetails에 저장된 자바빈의 nick_name,email 정보 갱신
		principal.getMemberVO().setNick_name(memberVO.getNick_name());
		principal.getMemberVO().setEmail(memberVO.getEmail());
		
		return "redirect:/member/myPage";
	}
	
	/*============================
	 * 비밀번호 변경
	 *============================*/
	//비밀번호 변경 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/changePassword")
	public String formChangePassword() {
		return "memberChangePassword";
	}
	//비밀번호 변경 폼에서 전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/changePassword")																					 
	public String submitChangePassword(@Valid MemberVO memberVO, BindingResult result,HttpSession session,Model model,
			//이동경로 흐름?
			HttpServletRequest request,@AuthenticationPrincipal PrincipalDetails principal) {
		log.debug("<<비밀번호 변경 처리>> : " + memberVO);
		
		//now_passwd,passwd,captcha_chars만 검증
		if(result.hasFieldErrors("now_passwd") || result.hasFieldErrors("passwd") || result.hasFieldErrors("captcha_chars")) {
			ValidationUtil.printErrorFields(result);
			return formChangePassword();
		}
		
		//캡챠 문자 체크 시작
		String code="1";//키 발급시 0, 캡챠 이미지 비교시 1로 세팅
		//캡챠 키 발급시 받은 키 값
		String key = (String)session.getAttribute("captcha_key");
		String value = memberVO.getCaptcha_chars();
		String apiURL = "https://openapi.naver.com/v1/captcha/nkey?code=" + code + "&key=" +key + "&value=" + value;
		
		Map<String,String> requestHeaders = new HashMap<String,String>();
		requestHeaders.put("X-Naver-Client-Id",naver_client_id);
		requestHeaders.put("X-Naver-Client-Secret",naver_client_secret);
		String responseBody = CaptchaUtil.get(apiURL, requestHeaders);
		log.debug("<<비밀번호 변경>> : " + responseBody);
		
		JSONObject jObject =new JSONObject(responseBody);
		boolean captcha_result = jObject.getBoolean("result");
		if(!captcha_result) {
			result.rejectValue("captcha_chars","invalidCaptcha");
			
			return formChangePassword();
		}
		//캡챠 문자 체크 끝
		
		memberVO.setMem_num(principal.getMemberVO().getMem_num());
		
		MemberVO db_member=memberService.selectMember(memberVO.getMem_num());
		
		//폼에서 전송한 비밀번호와 DB에서 읽어온 비밀번호 일치 여부 체크
		if(!passwordEncoder.matches(memberVO.getNow_passwd(), db_member.getPasswd())) {
			result.rejectValue("now_passwd", "invalidPassword");
			return formChangePassword();
		}
		
		//새 비밀번호 암호화
		memberVO.setPasswd(passwordEncoder.encode(memberVO.getPasswd()));
		memberVO.setId(db_member.getId());//자동로그인 해제 하기 위해서 ID 필요
		//비밀번호 변경
		memberService.updatePassword(memberVO);
		//View에 표시할 메시지
		model.addAttribute("message","비밀번호 변경 완료(*재접속시 설정되어 있는 자동로그인 기능 해제*)");
		model.addAttribute("url",request.getContextPath()+"/member/myPage");
		
		return "common/resultAlert";
	}
	
	
	
	
	
	
	
	
	/*==============================
	 * 네이버 캡챠 API
	 * =============================*/
	//캡챠 이미지 호출
	/*
	 * Naver Developers
	 * https://developers.naver.com
	 * Products > 캡챠
	 * 
	 * 애플리케이션 등록(API 이용신청)
	 * 애플리케이션 이름 : SpringPro
	 * 사용 API : 캡챠(이미지)
	 * */
	//캡챠 이미지 읽기
	@GetMapping("/getCaptcha")
	public String getCaptcha(Model model,HttpSession session) {
		String code = "0"; //키 발급시 0, 캡챠 이미지 비교시 1로 세팅
		String key_apiURL = "https://openapi.naver.com/v1/captcha/nkey?code="+code;
		
		Map<String,String> requestHeaders = new HashMap<String,String>();
		//애플리케이션 클라이언트 아이디값
		requestHeaders.put("X-Naver-Client-Id", naver_client_id);
		
		//애플리케이션 클라이언트 시크릿 값
		requestHeaders.put("X-Naver-Client-Secret", naver_client_secret);
		
		String responseBody = CaptchaUtil.get(key_apiURL, requestHeaders);
		log.debug("<<캡챠 이미지 호출>> : " + responseBody);
		
		JSONObject jObject = new JSONObject(responseBody);
		
		String key = jObject.getString("key");
		session.setAttribute("captcha_key", key);
		
		String apiURL = "https://openapi.naver.com/v1/captcha/ncaptcha.bin?key=" + key;
		byte[] response_byte = CaptchaUtil.getCaptchaImage(apiURL, requestHeaders);
		model.addAttribute("imageFile", response_byte);
		model.addAttribute("filename", "captcha.jpg");
		return "imageView";
	}
	
	
}

	






















