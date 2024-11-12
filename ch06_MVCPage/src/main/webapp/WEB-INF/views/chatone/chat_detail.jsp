<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<c:if test="${user_auth == 9}">
			<h2>${user_id} 관리자님이 <span id="recv_id"></span>님과 채팅 중</h2>
		</c:if>
		<c:if test="${user_auth != 9}">
			<h2>${user_id}님이 관리자와 채팅</h2>
		</c:if>
		<div id="chatting_message"></div>
		<form id="chatting_form">
			<input type="hidden" name="recv_num" value="${recv_num}" id="recv_num">
			<ul>
				<li>
					<label for="message">내용</label>
					<textarea rows="5" cols="40" name="message" id="message"></textarea>
					<input type="submit" value="전송">
				</li>
			</ul>
		</form>
	</div>
</div>
</body>
</html>