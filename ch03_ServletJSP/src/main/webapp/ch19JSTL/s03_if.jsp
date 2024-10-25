<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>if태그</title>
</head>
<body><%-- " "<- boolean영역 --%>
<c:if test="true">
	무조건 수행<br>
</c:if>

<c:if test="${param.name == 'dargon'}">
name 파라미터의 값이 ${param.name}입니다. <br>
</c:if>

<c:if test="${param.name.equals('dragon')}">
name 파라미터의 값이  ${param.name}입니다.<br>
</c:if>

<c:if test="${param.age >= 20}">
당신의 나이는 20세 이상입니다.
</c:if>



</body>
</html>