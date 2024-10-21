<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.util.DBUtil"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 삭제 처리</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<%
	//get 방식-> 인코딩 필요없고 그냥 데이터 받아서 처리 가능
	int num = Integer.parseInt(request.getParameter("num"));
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	try{
		//Connection 객체 생성
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "DELETE FROM tproduct WHERE num=?";
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//?에 데이터 바인딩
		pstmt.setInt(1,num);
		//SQL문 실행
		pstmt.executeUpdate();
%>
	<div class="result-display">
		<div class="align-center">
			상품 삭제 완료!<br>
			<input type="button" value="목록" onclick="location.href='selectTest.jsp'">
		</div>
	</div>
<%	
	}catch(Exception e){
%>
	<div class="result-display">
		<div class="align-center">
			오류 발생!상품 삭제 실패<br>
			<input type="button" value="목록" onclick="location.href='selectTest.jsp'">
		</div>
	</div>
<%
		e.printStackTrace();
	}finally{
		DBUtil.executeClose(null, pstmt, conn);
	}
%>
</body>
</html>