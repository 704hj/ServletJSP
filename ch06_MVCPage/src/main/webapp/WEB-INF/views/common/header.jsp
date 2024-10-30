<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 헤더 시작 -->
<div id="main_logo">
    <h1><a>회원제 게시판</a></h1>
</div>
<div id="main_nav">
    <ul>
        <c:if test="${!empty user_num}">
            <li class="menu-logout">
                [<span>${user_id}</span>]
                <a href="${pageContext.request.contextPath}/member/logut.do">로그아웃</a>
            </li>
        </c:if>
        <c:if test="${empty user_num}">
            <li><a href="${pageContext.request.contextPath}/member/registerUserForm.do">회원가입</a></li>
            <li><a href="${pageContext.request.contextPath}/member/loginForm.do">로그인</a></li>
        </c:if>
    </ul>
</div>
<!-- 헤더 끝 -->