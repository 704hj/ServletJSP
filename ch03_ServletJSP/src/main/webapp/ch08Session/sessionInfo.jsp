<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
	Date time = new Date();
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>세션 정보</title>
<!-- session id가 생성되고 그 id로 클라이언트를 식별 -->
</head>
<body>
세션 ID : <%= session.getId() %><br>
세션 생성 시간 : <%= session.getCreationTime() %><br>
<%
	time.setTime(session.getCreationTime());
%>
세션 생성 시간 : <%=sf.format(time)%><br>

최근 접근 시간 : <%= session.getLastAccessedTime() %><br>
<%
	time.setTime(session.getLastAccessedTime());
%>
최근 접근 시간 : <%=sf.format(time) %><br>
</body>
</html>