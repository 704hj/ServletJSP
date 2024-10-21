<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.util.DBUtil" %>    
<%@ page import="java.sql.Connection" %>  
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 목록 보기</title>
<link rel="stylesheet" href="../css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<h2>상품 목록 보기</h2>
	<div class="align-right">
		<input type="button" value="등록"
		       onclick="location.href='insertTestForm.jsp'">
	</div>
<%
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	try{
		//Connection 객체 반환
		conn = DBUtil.getConnection();
	
		//SQL문장
		sql = "SELECT * FROM tproduct ORDER BY num DESC";
		
		//JDBC 수행3단계 : PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		
		//JDBC 수행4단계 : SQL문을 반영하고 결과행을 ResultSet에 담음
		rs = pstmt.executeQuery();
%>
	<table>
		<tr>
			<th>번호</th>
			<th>상품명</th>
			<th>가격</th>
			<th>재고</th>
		</tr>
<%		
		while(rs.next()){
			int num = rs.getInt("num");
			String name = rs.getString("name");
			int price = rs.getInt("price");
			int stock = rs.getInt("stock");
%>
		<tr>
			<td><%=num %></td>
			<td><a href="detailTest.jsp?num=<%=num%>"><%= name %></a></td>
			<td class="align-right"><%= String.format("%,d원",price) %></td>
			<td class="align-right"><%= String.format("%,d",stock) %></td>
		</tr>	
<%			
		}
%>
	</table>
<%
	}catch(Exception e){
%>
		<div class="result-display">
			<span>오류 발생!</span>
		</div>	
<%		
		e.printStackTrace();
	}finally{
		//자원정리
		DBUtil.executeClose(rs, pstmt, conn);
	}
%>		
</div>
</body>
</html>







