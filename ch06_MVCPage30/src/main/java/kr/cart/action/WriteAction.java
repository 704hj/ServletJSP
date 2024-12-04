package kr.cart.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.cart.dao.CartDAO;
import kr.cart.vo.CartVO;
import kr.controller.Action;
import kr.item.dao.ItemDAO;
import kr.item.vo.ItemVO;
import kr.util.StringUtil;

public class WriteAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> mapAjax = 
				new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Long user_num = 
				(Long)session.getAttribute("user_num");
		if(user_num==null) {//로그인이 되지 않은 경우
			mapAjax.put("result", "logout");
		}else {//로그인 된 경우
			//전송된 데이터 인코딩 처리
			request.setCharacterEncoding("utf-8");
			//자바빈(VO)생성
			CartVO cart = new CartVO();
			cart.setItem_num(Long.parseLong(
					      request.getParameter("item_num")));
			cart.setOrder_quantity(Integer.parseInt(
					request.getParameter("order_quantity")));
			cart.setMem_num(user_num);
			
			CartDAO dao = CartDAO.getInstance();
			CartVO db_cart = dao.getCart(cart);
			if(db_cart==null) {//동일 상품이 없을 경우
				dao.insertCart(cart);
				mapAjax.put("result", "success");
			}else {//동일 상품이 있을 경우
				//재고수를 구하기 위해서 ItemDAO 호출
				ItemDAO itemDao = ItemDAO.getInstance();
				ItemVO item = 
						itemDao.getItem(db_cart.getItem_num());
				//구매수량 합산 (기존 장바구니에 저장된 구매수량 + 
				//                    새로 입력한 구매수량)
				int order_quantity = 
						db_cart.getOrder_quantity() 
						            + cart.getOrder_quantity();
				if(item.getQuantity()<order_quantity) {
					//상품재고 수량보다 장바구니에 담은 구매수량이 더 많음
					mapAjax.put("result", "overQuantity");
				}else {
					//구매수량을 합산한 구매수량으로 변경
					cart.setOrder_quantity(order_quantity);
					dao.updateCartByItem_num(cart);
					mapAjax.put("result", "success");
				}
			}
		}
		//JSON 데이터 반환
		return StringUtil.parseJSON(request, mapAjax);
	}

}


