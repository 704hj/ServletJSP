package kr.news.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.news.dao.NewsDAO;
import kr.news.vo.NewsVO;

public class ModifyAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
				request.setCharacterEncoding("utf-8");
				
				//자바빈(VO) 생성
				NewsVO newsVO = new NewsVO();
				newsVO.setNum(Long.parseLong(request.getParameter("num")));
				newsVO.setTitle(request.getParameter("title"));
				newsVO.setWriter(request.getParameter("writer"));
				newsVO.setEmail(request.getParameter("email"));
				newsVO.setPasswd(request.getParameter("passwd"));
				newsVO.setArticle(request.getParameter("article"));
				
				NewsDAO dao = NewsDAO.getInstance();
				//비밀번호 인증을 위해 한 건의 레코드를 자바빈에 담아서 반환
				NewsVO db_news = dao.getNews(newsVO.getNum());
				boolean check = false;
				if(db_news!=null) {
					//비밀번호 일치 여부 체크
					check = db_news.isCheckedPasswd(newsVO.getPasswd());
				}
				if(check) {
					dao.updateNews(newsVO);
					//상세페이지로 이동하기 위해 글번호 저장
					request.setAttribute("num", newsVO.getNum());
				}
				request.setAttribute("check", check);
				//JSP 경로 반환
				return "modify.jsp";
	}

}
