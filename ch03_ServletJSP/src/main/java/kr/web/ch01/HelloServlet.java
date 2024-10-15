package kr.web.ch01;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//일반 클래스가 Servlet을 상속시키면 Servlet이 된다
@WebServlet("/hello") //호출할 주소를 부여->hello 라는 주소를 부여, 호출 가능
public class HelloServlet extends HttpServlet {
	//HttpServlet의 doGet 재정의
	//doGet()메서드에서 HTML 페이지를 생성해서 클라이언트에 전송
	@Override
	public void doGet(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
		//문서 타입 및 캐릭터셋 지정
		response.setContentType("text/html;charset=utf-8");
		
		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Hello Servlet</title></head>");
		out.println("<body>");
		out.println("처음 작성하는 servlet입니다.");
		out.println("</body>");
		out.println("</html>");
		
		out.close();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
