package kr.chatone.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.chatone.dao.ChatOneDAO;
import kr.chatone.vo.ChatOneVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class WriteChatOneAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = 
				          new HashMap<String,String>();
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인이 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			
			String recv_num = 
					request.getParameter("recv_num");
			if(recv_num==null || recv_num.isEmpty()) {
				//recv_num이 전송되지 않으면 관리자 채팅으로 셋팅
				recv_num = "4";
			}
			
			ChatOneVO chat = new ChatOneVO();
			chat.setSend_num(user_num);//보내는 사람
			chat.setRecv_num(Long.parseLong(recv_num));//받는 사람
			chat.setMessage(request.getParameter("message"));
			
			ChatOneDAO dao = ChatOneDAO.getInstance();
			dao.insertChat(chat);
			
			mapAjax.put("result", "success");			
		}
		//JSON 문자열 반환
		return StringUtil.parseJSON(request, mapAjax);
	}
	
}



