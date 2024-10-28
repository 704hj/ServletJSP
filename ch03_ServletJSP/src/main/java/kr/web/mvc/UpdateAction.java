package kr.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//request에 데이터 저장
		request.setAttribute("message", "게시판 정보 수정");
	    
		return "/views2/update.jsp";
	}

}
