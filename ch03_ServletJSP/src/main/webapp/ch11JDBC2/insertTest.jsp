<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 정보 등록</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<%
	//전송된 데이터에 대한 인코딩
	request.setCharacterEncoding("utf-8");

	//전송된 데이터 반환
	String name = request.getParameter("name");
	int price = Integer.parseInt(request.getParameter("price"));
	int stock = Integer.parseInt(request.getParameter("stock"));
	String origin = request.getParameter("origin");
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;
	
	try{
		//Connection 객체 반환
		conn = DBUtil.getConnection();
		
		//SQL문
		sql = "INSERT INTO tproduct (num,name,price,stock,origin,"
		    + "reg_date) VALUES (tproduct_seq.nextval,?,?,?,?,sysdate)";
		
		//JDBC 수행3단계 : PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, name);
		pstmt.setInt(2, price);
		pstmt.setInt(3, stock);
		pstmt.setString(4, origin);
		
		//JDBC 수행4단계 : SQL문 실행
		pstmt.executeUpdate();
%>
		<div class="result-display">
			<div class="align-center">
				상품등록 성공!<p>
				<input type="button" value="목록 보기"
			       onclick="location.href='selectTest.jsp'">
			</div>
		</div>
<%	
	}catch(Exception e){
%>
		<div class="result-display">
			<div class="align-center">
				오류 발생! 상품등록 실패!<p>
				<input type="button" value="상품등록폼"
			      onclick="location.href='insertTestForm.jsp'">
			</div>
		</div>
<%	
		e.printStackTrace();
	}finally{
		//자원 정리
		DBUtil.executeClose(null, pstmt, conn);
	}
%>
</body>
</html>










