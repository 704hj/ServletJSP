package kr.web.ch04;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig(
		maxFileSize = 1024 * 1024 * 10,//1개당 10메가->서버의 성능과 비례해서 업로드크기 정해야함
		maxRequestSize = 1024 * 1024 * 50
		)
@WebServlet("/fileMultiUpload")
public class UploadServlet2 extends HttpServlet{
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{

		//컨텍스트 경로상의 파일 업로드 절대 경로 구하기 -> 절대 경로 구하지 않으면 파일 업로드 할 수 없음
		String realFolder = request.getServletContext().getRealPath("/upload");

		//문서 타입 및 캐릭터셋 지정
		response.setContentType("text/html;charset=utf-8");

		//post방식 전송시 한글 안 깨지게 -> 전송된 데이터 인코딩 처리
		request.setCharacterEncoding("utf-8");

		//HTML 출력을 위한 출력 스트림 생성
		PrintWriter out = response.getWriter();
		try {
			out.println("작성자 : " + request.getParameter("name") + "<br>");
			out.println("제목 : " + request.getParameter("title") + "<br>");
			out.println("------------------------------------<br>");

			/*
			 * 
			Part part1 = request.getPart("uploadFile1");
			String fileName = part1.getSubmittedFileName();
			if(!fileName.isEmpty()) {
				part1.write(realFolder+"/"+fileName);
				out.println("<img src=\"/ch03_ServletJSP/upload/"+fileName+"\"><br>");
			}
		
			Part part2 = request.getPart("uploadFile2");
			String fileName2 = part2.getSubmittedFileName();
			if(!fileName2.isEmpty()) {
				part2.write(realFolder+"/"+fileName2);
				out.println("<img src=\"/ch03_ServletJSP/upload/"+fileName2+"\"><br>");
			}
			 */
			
			//코드 간소화
			Collection<Part> parts = request.getParts();
			for(Part p : parts) {
				String fileName = p.getSubmittedFileName();
				if(!fileName.isEmpty()) {
					p.write(realFolder+"/"+fileName);
					out.println("<img src=\"/ch03_ServletJSP/upload/"+fileName+"\"><br>");
				}
			}
			

		}catch(IllegalStateException e) {
			out.println("용량 초과 : " + e.toString());

		}
	}
}