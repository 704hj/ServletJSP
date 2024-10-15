package kr.web.ch02;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add") 
public class AddServlet extends HttpServlet{
/*
 *[실습]덧셈 프로그램
 *5 + 7 = 12 
 */
	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException{
		//문서 타입 및 캐릭터셋 지정
		response.setContentType("text/html;charset=utf-8");
		
		//getParameter(String 값 만 가능)  String-> int 변환
		int num1 = Integer.parseInt(request.getParameter("num1"));
		int num2 = Integer.parseInt(request.getParameter("num2"));
	
		//HTML 출력을 위한 출력 스트림 생성
				PrintWriter out =response.getWriter();
				out.println("<html>");
				out.println("<head><title>덧셈</title></head>");
				out.println("<body>");
				
				int sum = num1 + num2;
				out.println(num1 +" + " +num2 + " = " + sum);
				
				out.println("</body>");
				out.println("</html>");
				out.close();
	}
}
