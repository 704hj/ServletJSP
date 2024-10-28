package kr.web.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class DispatcherServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		requestPro(request, response);
		
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		requestPro(request, response);
	}
	//doGet, doPost 메서드에서 공통으로 호출하는 작업 메서드 정의
	private void requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ 
		
		Action com = null;//모델 클래스 정보를 갖는 변수
		String view = null;//뷰 정보를 갖는 변수
		
		String command = request.getRequestURI();
		
		System.out.println("1:"+command);
		
		if(command.indexOf(request.getContextPath())==0) {
			command = command.substring(request.getContextPath().length());
		}
		
		System.out.println("2: "+command);
		
		if(command.equals("/list.do")) {
			com = new ListAction();
		}else if(command.equals("/write.do")) {
			com = new WriteAction();
		}else if(command.equals("/detail.do")) {
			com = new DetailAction();
		}else if(command.equals("/update.do")) {
			com = new UpdateAction();
		}
		
		try {
			//데이터 처리 후 뷰 경로 반환
			view = com.execute(request, response);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//forward 방식으로 View(JSP) 호출
				RequestDispatcher dispatcher = request.getRequestDispatcher(view);
				dispatcher.forward(request, response);
	}

}
