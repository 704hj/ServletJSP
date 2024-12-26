package kr.spring.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberAdminController {
	@Autowired
	private MemberService memberService;
	
	
	/*==========================
	 * 회원목록 - 관리자
	 *==========================*/
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin_list")
	public String getList(
			@RequestParam(value="pageNum",defaultValue="1")
			int currentPage, String keyfield, String keyword, Model model) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//전체/검색 레코드 수
		int count = memberService.selectRowCount(map);
		
		log.debug("<<count>> : " + count);
		
		PagingUtil page = new PagingUtil(keyfield,keyword,currentPage,count,20,10,"admin_list");
		List<MemberVO> list = null;
		if(count > 0) {
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = memberService.selectList(map);
		}
		
		model.addAttribute("count",count);
		model.addAttribute("list",list);
		model.addAttribute("page",page.getPage());
		
		return "admin_memberList";//tiles 설정
	}

	/*==========================
	 * 회원권한변경 - 관리자
	 *==========================*/
	//수정 폼
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/admin_update")
	public String form(long mem_num, Model model) {
		MemberVO memberVO = memberService.selectMember(mem_num);
		model.addAttribute("memberVO",memberVO);
		
		return "admin_memberModify";
	}
	//수정 폼에서 전송된 데이터 처리
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/admin_update")
	public String submit(MemberVO memberVO,Model model, HttpServletRequest request) {
		log.debug("<<관리자 회원권한 수정>> : " + memberVO);
		//회원권한 수정
		memberService.updateByAdmin(memberVO);
		
		//view에 표시할 메시지
		model.addAttribute("message","회원권한 수정 완료!");
		model.addAttribute("url",request.getContextPath()+"/member/admin_update?mem_num="+memberVO.getMem_num());
		
		return "common/resultAlert";
	}
	
	
}



































