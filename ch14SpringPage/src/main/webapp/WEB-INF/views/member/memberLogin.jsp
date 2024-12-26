<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="page-main">
	<h2>회원로그인</h2>
	<form:form action="login" id="member_login" modelAttribute="memberVO">
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li class="floating-label">
				<form:input path="id" placeholder="아이디" cssClass="form-input" autocomplete="off"/>
				<form:label path="id">아이디</form:label>
				<form:errors path="id" element="div" cssClass="error-color"/>
			</li>
			
			<li class="floating-label">
				<form:password path="passwd" placeholder="비밀번호" cssClass="form-input"/>
				<form:label path="passwd">비밀번호</form:label>
				<form:errors path="passwd" element="div" cssClass="error-color"/>
			</li>
			
			<li>
				<label for="remember-me"><input type="checkbox" name="remember-me" id="remember-me">로그인상태유지</label>
			</li>
		</ul>
		<div>
			<form:button class="login-btn">로그인</form:button>
		</div>
	</form:form>
	<p class="align-center">
	<input type="button" value="홈으로" onclick="location.href='${pageContext.request.contextPath}/main/main'">
	<input type="button" value="비밀번호 찾기" onclick="location.href='sendPassword'">
	</p>
	<div class="align-center">
	<a href="${pageContext.request.contextPath}/oauth2/authorization/naver" id="naver_login">
		<img width="223" src="${pageContext.request.contextPath}/assets/images_api/naver_login.gif">
	</a>
	<br>
	<a href="${pageContext.request.contextPath}/oauth2/authorization/kakao" id="kakao_login">
		<img width="223" src="${pageContext.request.contextPath}/assets/images_api/kakao_login.jpg">
	</a>
	</div>
</div>















