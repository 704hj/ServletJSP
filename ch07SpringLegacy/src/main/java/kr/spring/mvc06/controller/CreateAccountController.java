package kr.spring.mvc06.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.mvc06.vo.MemberVO;

@Controller
public class CreateAccountController {
	
	//View에서 form 커스텀 태그를 사용하면 반드시 자바빈 초기화를 해야함
	//자바빈(VO)초기화
	@ModelAttribute
	public MemberVO initCommand() {
		return new MemberVO();
	}
	
	@GetMapping("/account/create.do")
	public String form() {
				//뷰 이름
		return "account/creationForm";
	}

	@PostMapping("/account/create.do")// 유효성검사에서 에러를 갖는 BindingResult
	public String submit(@Valid MemberVO vo, BindingResult result) {
		
		System.out.println("전송된 데이터 : " + vo);
		
		//유효성 체크 결과 오류가 있으면 폼을 호출
		if(result.hasErrors()) {
			return "account/creationForm";
		}
		
		return "account/created";
	}
}
