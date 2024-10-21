<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%
	String name = request.getParameter("name");
	String age = request.getParameter("age");
%>
이름 : <%= name %>, 나이 : <%= age %>