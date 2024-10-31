<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h3>${result_title}</h3>
		<div class="result-display">
			<div class="aoign-center">
			${result_msg}
			<p>
			<input type="button" value="확인" onclick="location.href='${result_url}'">
			</div>
		</div>
	</div>
</div>
</body>
</html>