package kr.spring.mvc09.controller;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DownloadController {
	@GetMapping("/file.do")
	public ModelAndView download(HttpSession session) {
		//file.txt의 컨텍스 경로상의 절대 경로를 구하기
		String path = session.getServletContext().getRealPath("/WEB-INF/file.txt");
		//경로 정보를 갖는 File 객체 생성
		File downloadFile = new File(path);
								//뷰 이름,   속성 명,  		,속성 값
		return new ModelAndView("download","downloadFile",downloadFile);
		
	}
}
