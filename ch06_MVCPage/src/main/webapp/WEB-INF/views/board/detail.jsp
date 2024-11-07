<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>${board.title}</h2>
		<ul class="detail-info">
			<li>
				<c:if test="${!empty board.photo}">
				<img src="${pageContext.request.contextPath}/upload/${board.photo}" width="40" height="40" class="my-photo">
				</c:if>
				<c:if test="${empty board.photo}">
				<img src="${pageContext.request.contextPath}/images/face.png" width="40" height="40" class="my-photo">
				</c:if>
			</li>
			<li>
				${board.id}<br>
				조회 : ${board.hit}
			</li>
		</ul>
		<hr size="1" noshade="noshade" width="100%">
		<c:if test="${!empty board.filename}">
		<div class="align-center">
			<img src="${pageContext.request.contextPath}/upload/${board.filename}" 
			                                                   class="detail-img">
		</div>
		</c:if>
		<p>
			${board.content}
		</p>
		<hr size="1" noshade="noshade" width="100%">
		<ul class="detail-sub">
			<li>
				<%-- 좋아요 --%>
			</li>
			<li>
				<c:if test="${!empty board.modify_date}">
				최근 수정일 : ${board.modify_date}
				</c:if>
				작성일 : ${board.reg_date}
			</li>
		</ul>
	</div>
</div>
</body>
</html>



