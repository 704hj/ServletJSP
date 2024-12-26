package kr.spring.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/common")
public class CommonController {
	//인증 관련 오류 페이지 호출
	@GetMapping("/accessDenied")
	public String getAccessDenied() {
		return "common/accessDenied";
	}
	//인증 관련 페이지 호출(JSON)
	@GetMapping("/accessLogout")
	public String getAccessLogout() {
		return "common/accessLogout";
	}

}
