<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.util.DBUtil"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품정보 수정</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
</head>
<body>
<%
	//전송된 데이터 인코딩 처리
	request.setCharacterEncoding("utf-8");
	//전송된 데이터 반환
	int num = Integer.parseInt(request.getParameter("num"));
	String name = request.getParameter("name");
	int price = Integer.parseInt(request.getParameter("price"));
	int stock = Integer.parseInt(request.getParameter("stock"));
	String origin = request.getParameter("origin");
	
	//DB연동
	Connection conn = null;
	PreparedStatement pstmt = null;
	String sql = null;
	try{
		//Connection 객체 생성
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "UPDATE tproduct SET name=?,price=?,stock=?,origin=? WHERE num=?";
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//?에 데이터 바인딩
		pstmt.setString(1,name);
		pstmt.setInt(2,price);
		pstmt.setInt(3,stock);
		pstmt.setString(4,origin);
		pstmt.setInt(5,num);
		//SQL문 실행
		pstmt.executeUpdate();
%>
	<div class="result-display">
		<div class="align-center">
			글 수정 완료!<p>
			<input type="button" value="글 상세" onclick="location.href='detailTest.jsp?num=<%= num %>'">
		</div>
	</div>
<%
	}catch(Exception e){
%>
	<div class="result-display">
		<div class="align-center">
			오류 발생! 글 수정 실패!<p>
			<input type="button" value="글 수정 폼" onclick="location.href='updateTestForm.jsp?num=<%= num %>'">
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