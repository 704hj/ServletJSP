<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 4개 기본객체와 영역</title>
</head>
<body>
<!-- 다른 페이지에서 pageContext 데이터 정보 불러오기 불가능  -->
page 영역의 msg1 = <%=pageContext.getAttribute("msg1")%><br>
request 영역의 msg2 = <%=request.getAttribute("msg2")%><br>
session 영역의 msg3 = <%=session.getAttribute("msg3")%><br>
</body>
</html>