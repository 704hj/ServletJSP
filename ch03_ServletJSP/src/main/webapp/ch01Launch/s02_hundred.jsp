<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1부터100까지의 합</title>
</head>
<body>
<h1>1부터100까지의 합</h1>
<!-- 자바영역 생성 -->
<%
	int total = 0;
	for(int i=0;i<=100;i++){
		total += i;
	}
	//출력
	out.println("1부터 100까지의 합 : " + total);
%>
</body>
</html>