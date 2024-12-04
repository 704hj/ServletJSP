package kr.chatone.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;

public class ChatOneDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Long user_num = 
				 (Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			return "redirect:/member/loginForm.do";
		}
		//recv_num이 없을 경우에 null이고 관리자와 채팅으로
		//설정하기 위해 String 타입으로 저장
		String recv_num = request.getParameter("recv_num");
		
		request.setAttribute("recv_num", recv_num);
		
		return "chatone/chat_detail.jsp";
	}

}





