package kr.spring.mvc04.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class CookieController {
	
	@GetMapping("/cookie/make.do")
	public String make(HttpServletResponse response) {
		//쿠키를 생성해서 클라이언트에 전송
		response.addCookie(new Cookie("auth","1"));
		
				//뷰 이름
		return "cookie/make"; 
	}
	
	@GetMapping("/cookie/view.do")
	public String view(			//쿠키가 없어도 기본값인 0이 출력 됨
			@CookieValue(value="auth",defaultValue="0") String auth) {
		System.out.println("auth 쿠키 : " + auth);

		return "cookie/view";
	}
}
