<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그아웃</title>
<!-- 세션의 모든 속성을 제거해서 모든 정보를 삭제하였다가 다시 갱신 되게끔 -->
</head>
<body>
<%
	//세션안의 모든 속성을 제거해서 세션을 갱신
	session.invalidate();
%>
로그아웃했습니다.
<br>                                              <!--'  '로 감싸서 문자열로 인식되게끔 해야함  -->
<input type="button" value="로그인 체크" onclick="location.href='sessionLoginCheck.jsp'">
<input type="button" value="로그인" onclick="location.href='sessionLoginForm.jsp'">
</body>
</html>