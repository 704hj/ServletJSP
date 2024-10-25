<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>forEach 태그</title>
</head>
<body>
<h4>1부터 100까지 합</h4>
<c:set var="sum" value="0"/>
<c:forEach var="i" begin="1" end="100" step="1">
	<c:set var="sum" value="${sum + i}"/>
</c:forEach>
결과는 ${sum}

<h4>구구단 : 5단</h4>
<ul>
	<c:forEach var="i" begin="1" end="9"><br>
		<li>5 * ${i } = ${5 * i }</li>
	</c:forEach>
</ul>

<h4>int형 배열</h4>
<c:set var="intArray" value="<%=new int[]{10,20,30,40,50} %>"/>
<c:forEach var="i" items="${intArray}" begin="2" end="4" varStatus="status">
	${status.index} - ${status.count} - [${i}]<br>
</c:forEach>
</body>
</html>