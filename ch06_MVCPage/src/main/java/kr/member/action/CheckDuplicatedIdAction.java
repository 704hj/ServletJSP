package kr.member.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.StringUtil;

public class CheckDuplicatedIdAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");
		//전송된 데이터 반환
		String id = request.getParameter("id");

		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(id);

		Map<String,String> mapAjax = new HashMap<String,String>();
		if(member == null) {//아이디 미중복
			mapAjax.put("result", "idNotFound");
		}else {//아이디 중복
			mapAjax.put("result", "idDuplicated");
		}

		/*
			 ObjectMapper mapper = new ObjectMapper();

			//json문자인데 클래스에서는 전달 불가능 jsp에서만 전달 가능->request에 저장해야 호출 할 수 있음
			String ajaxData = mapper.writeValueAsString(mapAjax);
			//request에 저장
			request.setAttribute("ajaxData", ajaxData);
			//JSP 경로 반환 : jsp에서만 전달 가능
			return "common/ajax_view.jsp";
		 */
		//위 여러줄 주석으로 쓰던지 
		//kr.util.StringUtil.java에 공통 자료로 작성하고 불러오는 방식
		//StringUtil에 정의한 형식으로 사용
		return StringUtil.parseJSON(request, mapAjax);

	}
}