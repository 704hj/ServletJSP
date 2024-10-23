<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import ="kr.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%
	//전송된 데이터 인코딩 처리
	request.setCharacterEncoding("utf-8");
	//전송된 데이터 반환
	int id = Integer.parseInt(request.getParameter("id"));
	int completed = Integer.parseInt(request.getParameter("completed"));
	
	if(completed == 0) completed = 1;
	else completed = 0 ;
	
	Connection conn = null; 
	PreparedStatement pstmt = null;
	String sql = null;
	
	try{
		//Connection 객체 생성
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "UPDATE todo SET completed=? WHERE id=?";
		//PrepardeStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//?에 데이터 바인딩
		pstmt.setInt(1,completed);
		pstmt.setInt(2,id);
		//SQL문 실행
		pstmt.executeUpdate();
		%>
		{"result":"success"}
		<%
	}catch(Exception e){
		%>
		{"result":"failure"}
		<%
		e.printStackTrace();
	}finally{
		DBUtil.executeClose(null, pstmt, conn);
	}
%>
