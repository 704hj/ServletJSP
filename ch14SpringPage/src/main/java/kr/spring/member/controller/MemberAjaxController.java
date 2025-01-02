package kr.spring.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberAjaxController {
	@Autowired
	private MemberService memberService;
	
	/*============================
	 * 아이디, 별명 중복 체크
	 *============================*/
	@GetMapping("/confirmIdnNickName")
	@ResponseBody //메서드의 반환 값을 HTTP 응답 본문(Body)에 직접 넣어주는 역할
	public Map<String,String> process(String id, String nick_name){
		log.debug("<<아이디 중복 체크>> : " + id);
		log.debug("<<별명 중복 체크>> : " + nick_name);
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		//전송된 데이터 전송-> map에 담는 작업
		Map<String,String> map = new HashMap<String,String>();
		map.put("id", id);
		map.put("nick_name", nick_name);
		MemberVO member = memberService.selectIdAndNickName(map);
		if(id!=null && nick_name==null) {//아이디 중복 체크
			if(member!=null) {
				//아이디 중복
				mapAjax.put("result", "idDuplicated");
			}else {
				if(!Pattern.matches("^[A-Za-z0-9]{4,14}$", id)) {
					//패턴 불일치
					mapAjax.put("result","notMatchPattern");
				}else {
					//패턴 일치하면서 아이디 미중복
					mapAjax.put("result","idNotFound");
				}
			}
		}else if(id==null && nick_name!=null) {//별명 중복 체크
			if(member!=null) {
				//별명 중복
				mapAjax.put("result","nickDuplicated");
			}else {
				if(!Pattern.matches("^[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,10}$", nick_name)) {
					//패턴 불일치
					mapAjax.put("result", "notMatchPattern");
				}else {
					//패턴 일치하면서 별명 미중복
					mapAjax.put("result", "nickNotFound");
				}
			}
			
		}else {
			mapAjax.put("result", "error");
		}
		return mapAjax;
	}
	/*============================
	 * 프로필 사진 업로드
	 *============================*/
	@PostMapping("/updateMyPhoto")
	@ResponseBody //메서드의 반환 값을 HTTP 응답 본문(Body)에 직접 넣어주는 역할
	public Map<String,String> processProfile(
			@AuthenticationPrincipal PrincipalDetails principalDetails,MemberVO memberVO){
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		try {
			MemberVO user = principalDetails.getMemberVO();
			memberVO.setMem_num(user.getMem_num());
			memberService.updateProfile(memberVO);
			mapAjax.put("result", "success"); //mapAjax통신이니까 스크립트 작업해야함 ->js
			
		}catch(NullPointerException e) {
			mapAjax.put("result","logout");
		}
	
	return mapAjax;
	}
}




























