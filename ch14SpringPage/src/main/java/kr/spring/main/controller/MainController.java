package kr.spring.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.member.service.MemberService;
import kr.spring.member.vo.MemberVO;

@Controller
public class MainController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/")
	public String init() {
		return "redirect:/main/main";//확장자 없는 형태가 트렌드임.do안함
	}
	@GetMapping("/main/main")
	public String main() {
		//return "main/main";//첫번째 main : 폴더, 두번째 main:main.jsp
		 return "main";//tiles 설정명 (main.xml 파일에서의 <definition name="main" name명을 호출)
	}
	
	//관리자 페이지
	@GetMapping("/main/admin")
	public String adminMain(Model model) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("start", 1);
		map.put("end", 5);
		List<MemberVO> memberList = memberService.selectList(map);
		
		model.addAttribute("memberList", memberList);
		
		return "admin";
	}
	
}
