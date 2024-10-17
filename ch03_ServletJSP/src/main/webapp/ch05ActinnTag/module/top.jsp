<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 상단 시작 -->
<%
	String company = request.getParameter("company");
%>
<b><%= company %></b>
상단 메뉴 : 
<a href="#">Home</a>
<a href="#">Info</a>
<a href="#">About</a>
<!-- 상단 끝 -->