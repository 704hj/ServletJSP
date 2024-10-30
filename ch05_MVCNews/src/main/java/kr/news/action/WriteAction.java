package kr.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
				request.setCharacterEncoding("utf-8");
				
				//자바빈(VO) 생성
				NewsVO newsVO = new NewsVO();
				newsVO.setTitle(request.getParameter("title"));
				newsVO.setWriter(request.getParameter("writer"));
				newsVO.setPasswd(request.getParameter("passwd"));
				newsVO.setEmail(request.getParameter("email"));
				newsVO.setArticle(request.getParameter("article"));
				
				NewsDAO dao = NewsDAO.getInstance();
				dao.registerNews(newsVO);
				
				//JSP 경로 반환
				return "write.jsp";
	}

}
