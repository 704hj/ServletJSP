<%@page import="org.apache.jasper.tagplugins.jstl.core.Import"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.member.dao.MemberDAO" %>
<%
	//전송된 데이터 인코딩 처리
	request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="member" class="kr.member.vo.MemberVO"/>
<%-- request로부터 전송된 데이터를 읽어들여 자바빈에 저장 --%>
<jsp:setProperty property="*" name="member"/>
<%
	MemberDAO dao = MemberDAO.getInstance();
/* 생성자 호출 할 수 없게끔 private로 막아놨고 객체는 메모리에 부담을 덜 주기위해서 여러개 호출해도 하나만 호출된다 */
	//MemberDAO dao2 = MemberDAO.getInstance();
	//MemberDAO dao3 = MemberDAO.getInstance();
	dao.insertMember(member);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/css/style.css" type="text/css">
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.7.1.min.js"></script>
</head>
<body>
<div class="page-main">
	<h1>회원가입 완료</h1>
<div class="result-display">
	<div class="align-center">
		회원가입 성공!<p>
		<button onclick="location.href='main.jsp'">홈으로</button>
	</div>
</div>
</body>
</html>