<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.web.member.Member" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>set 태그</title>
</head>
<body>
<%--저장--%>
<%--        속성명       속성값       영역 --%>
<c:set var="msg" value="봄" scope="page"/>
<c:set var="msg2" value="여름" scope="request"/>
<c:set var="msg3" value="가을" scope="session"/>
<c:set var="msg4" value="겨울" scope="application"/>

<%--읽기--%>
${pageScope.msg},${msg}<br>
${requestScope.msg2},${msg2}<br>
${sessionScope.msg3},${msg3}<br>
${applicationScope.msg4},${msg4}<br>

<%
	Member member = new Member();
	//그대로 호출시 EL이 읽지 못함. 4개 영역에 객체 저장을 해야 읽을 수 있음
%>
<%--page 영역에 Member 객체 저장 --%>
<c:set var="member" value="<%= member%>"/>

<%--
	target : 값을 설정하고자 하는 객체 명시
	property : 값이 저장되는 멤버변수
--%>

<c:set target="${member}" property="name" value="홍길동"/>
회원 이름 : ${member.name}<br>
회원 이름 : ${member.getName()}
</body>
</html>

