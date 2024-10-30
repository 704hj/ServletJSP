package kr.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;

public class DetailAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 글번호 반환
		long num = Long.parseLong(request.getParameter("num"));
		NewsDAO dao = NewsDAO.getInstance();
		NewsVO newsVO = dao.getNews(num);
		
		request.setAttribute("newsVO", newsVO);
		//JSP경로 반환
		return "detail.jsp";
	}

}