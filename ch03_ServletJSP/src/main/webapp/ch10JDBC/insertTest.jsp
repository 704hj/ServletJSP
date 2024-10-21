<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 등록 처리</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<%
	//전송된 데이터 인코딩 처리
	request.setCharacterEncoding("utf-8");
	//전송된 데이터 반환
	String name = request.getParameter("name");
	String title = request.getParameter("title");
	String passwd = request.getParameter("passwd");
	String content = request.getParameter("content");
	
	//DB 연동
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	try{
		//Connection 객체 생성
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "INSERT INTO tboard (num,name,title,passwd,content,reg_date) VALUES (tboard_seq.nextval,?,?,?,?,SYSDATE)";
		
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//?에 데이터 바인딩
		pstmt.setString(1, name);
		pstmt.setString(2, title);
		pstmt.setString(3, passwd);
		pstmt.setString(4, content);
		
		//SQL문 실행
		pstmt.executeUpdate();
%>
		<div class="result-display">
			<div class="align-center">
				글 등록 성공!<p>
				<input type="button" value="목록 보기"
				     onclick="location.href='selectTest.jsp'">
			</div>
		</div>
<%		
	}catch(Exception e){
%>
		<div class="result-display">
			<div class="align-center">
				오류 발생! 글 등록 실패<p>
				<input type="button" value="글쓰기"
				 onclick="location.href='insertTestForm.jsp'">
			</div>
		</div>
<%		
		e.printStackTrace();
	}finally{
		//자원정리
		DBUtil.executeClose(null, pstmt, conn);
	}
%>
</body>
</html>




