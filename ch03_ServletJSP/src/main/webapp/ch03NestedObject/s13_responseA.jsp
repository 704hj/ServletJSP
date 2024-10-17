<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>response.sendRedirect() 테스트</title>
</head>
<body>
현재 페이지는 s13_reponseA.jsp 입니다. 화면에 보여지지 않습니다.
</body>
</html>
<%
   //지정한 경로로 리다이렉트 처리
	response.sendRedirect("s14_responseB.jsp");
%>