package kr.spring.mvc01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//요청하면 동작하는 클래스임을 명시해야함
@Controller
public class HelloController {
	
	//요청 URL과 실행 메서드 연결
	@RequestMapping("/hello.do")
	public ModelAndView hello() {
		ModelAndView mav = new ModelAndView();
		//뷰 이름 지정		파일명으로만 명시
		mav.setViewName("hello");
		//뷰에서 사용할 데이터 저장("식별값","데이터")
		mav.addObject("greeting","안녕하세요!!");
		
		
		return mav;
	}
	

}
