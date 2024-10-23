<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="kr.util.DBUtil"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>\
<%@ page import="java.sql.ResultSet"%>
<%-- [] : 배열 --%>
[<%
 	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	
	try{
		//Connection 객체 생성
		conn = DBUtil.getConnection();
		//SQL문 작성
		sql = "SELECT * FROM people ORDER BY reg_date DESC";
		//PreparedStatement 객체 생성
		pstmt = conn.prepareStatement(sql);
		//SQL문 실행
		rs = pstmt.executeQuery();
		while(rs.next()){
			String id = rs.getString("id");
			String name = rs.getString("name");
			String job = rs.getString("job");
			String address = rs.getString("address");
			String blood_type = rs.getString("blood_type");
			String reg_date = rs.getString("reg_date");
			
			if(rs.getRow()>1){//쉼표 처리
				out.print(",");
			}
			%>
			{
				"id":"<%= id %>",
				"name":"<%= name %>",
				"job":"<%= job %>",
				"address":"<%= address %>",
				"blood_type":"<%= blood_type %>",
				"reg_date":"<%= reg_date %>"
			}
			<% 
		}
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		DBUtil.executeClose(rs, pstmt, conn);
	}
%>]















