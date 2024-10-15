package kr.web.ch03;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/travel")
public class TravelServlet extends HttpServlet{
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		
		//전송된 데이터 인코딩 처리 -> 한글 안 깨지게
		request.setCharacterEncoding("utf-8");
		
		//문서 타입 및 캐릭터셋 지정
		response.setContentType("text/html;charset=utf-8");
		
		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head><title>여행지 선택</title></head>");
		out.println("<body>");
		out.println("<h3>여행을 떠나요~</h3>");
		out.println("<div align=\"center\">");
		
		String name = request.getParameter("name");
		
		out.println("이름 : " + name + "<br>");
		out.println("여행지 : ");
		
		String[] values = request.getParameterValues("city");
		if(values != null) {
			for(int i=0;i<values.length;i++){
				//쉼표 앞에 두기
				if(i>0) out.println(",");
				
				out.print(values[i]);
				//마지막 쉼표 미출력(쉼표 뒤에 두기)
				  
				//if(i<values.length-1) out.println(",");
	        }
		}else {
			out.println("집~~~");
		}
		
		out.println("</body>");
		out.println("</html>");
		out.close();
	}
}
