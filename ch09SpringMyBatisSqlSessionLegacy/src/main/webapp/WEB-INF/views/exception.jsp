<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예외 발생</title>
</head>
<body>
요청을 처리하는 도중에 예외가 발생했습니다:
${exception.message}
<%
	Throwable e = (Throwable)request.getAttribute("exception");
	e.printStackTrace();
%>
</body>
</html>












