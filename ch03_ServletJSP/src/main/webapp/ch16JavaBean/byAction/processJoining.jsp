<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.web.member.MemberVO" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
<%
	//전송된 데이터 인코딩 처리
	request.setCharacterEncoding("utf-8");
%>
<%-- 자바빈 생성 (id="member"<- 식별자 역할이니까 name에 member입력)--%>

<jsp:useBean id="member" class="kr.web.member.MemberVO"/>

<%-- request로부터 전송된 데이트를 반환받아서 자바빈에 저장 --%>
<%-- 멤버변수(property)는 membershipForm.jsp에서의 name값인 id, password,name, email,address 와 비교--%>

<jsp:setProperty property="*" name="member"/>

아이디 : <jsp:getProperty property="id" name="member"/><br>
비밀번호 : <jsp:getProperty property="password" name="member"/><br>
이름 : <jsp:getProperty property="name" name="member"/><br>
이메일 : <jsp:getProperty property="email" name="member"/><br>
주소 : <jsp:getProperty property="address" name="member"/><br>
</body>
</html>
