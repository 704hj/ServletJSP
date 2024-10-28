package kr.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WriteAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		request.setAttribute("message", "글 등록 완료");
	
		return "/views2/write.jsp";
	}



}
