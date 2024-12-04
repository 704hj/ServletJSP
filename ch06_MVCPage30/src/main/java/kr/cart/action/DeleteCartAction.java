package kr.cart.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DeleteCartAction implements Action{
  
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = 
				            new HashMap<String,String>();
		HttpSession session = request.getSession();
		Long user_num = (Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			
			CartDAO dao = CartDAO.getInstance();
			dao.deleteCart(Long.parseLong(
					      request.getParameter("cart_num")));
			mapAjax.put("result", "success");
		}
		//JSON 데이터로 변환
		return StringUtil.parseJSON(request, mapAjax);
	}

}



