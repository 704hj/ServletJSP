package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;

public class DeleteFormAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		long num = Long.parseLong(request.getParameter("num"));
		
		request.setAttribute("num", num);
		//JSP 경로 반환
		return "deleteForm.jsp";
	}

}
