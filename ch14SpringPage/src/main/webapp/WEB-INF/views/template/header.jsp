<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<sec:authorize access="isAuthenticated()"><!-- 스프링 시큐리티(Spring Security)에서 사용자의 인증 상태와 인증 객체에 변수명으로 접근 -->
	<sec:authentication property="principal" var="principal"/><!-- principal 변수명으로 접근 -->
</sec:authorize>

<!-- 상단 시작 -->
<h2 class="align-center">
	<c:if test="${empty principal || principal.memberVO.auth < 9}"><%-- 사용자 화면 링크 --%>
	<a href="${pageContext.request.contextPath}/main/main">SprigPage</a>
	</c:if>
	<c:if test="${!empty principal && principal.memberVO.auth == 9}"><%-- 사용자 화면 링크 --%>
	<a href="${pageContext.request.contextPath}/main/admin">SprigPage</a>
	</c:if>
</h2>
<div class="align-right">
	<%-- ==============사용자 비로그인 영역 시작============== --%>
	<c:if test="${empty principal || principal.memberVO.auth < 9}">
	<a href="${pageContext.request.contextPath}/board/list">게시판</a>
	</c:if>
	<c:if test="${empty principal}">
	<a href="${pageContext.request.contextPath}/member/registerUser">회원가입</a>
	<a href="${pageContext.request.contextPath}/member/login">로그인</a>
	</c:if>
	<%-- ===============사용자 비로그인 영역 끝=============== --%>
	
	<%-- ===============사용자 로그인 영역 시작=============== --%>
	<c:if test="${!empty principal && principal.memberVO.auth < 9}">
	<a href="${pageContext.request.contextPath}/member/myPage">MY페이지</a>
	</c:if>
	<%-- ================사용자 로그인 영역 끝=============== --%>
	
	<%-- ==============사용자,관리자 공통 로그인 영역 시작============== --%>
	<c:if test="${!empty principal}">
	<img src="${pageContext.request.contextPath}/member/photoView" width="25" height="25" class="my-photo">
		<c:if test="${!empty principal.memberVO.nick_name}">
		[<span class="user_name">${principal.memberVO.nick_name}</span>]
		</c:if>
		<c:if test="${empty principal.memberVO.nick_name}">
		[<span class="user_name">${principal.memberVO.id}</span>]
		</c:if>
		<%--아래와 같이 폼을 만들고 post방식으로 전달해야 스프링 시큐리티가 지원하는 로그아웃 기능을 사용할 수 있음 --%>
		<form action="${pageContext.request.contextPath}/member/logout" method="post" style="display:none;" id="frm_logout">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"> <!--  Spring Security에서 CSRF(Cross-Site Request Forgery) 공격을 방지하기 위해 사용되는 CSRF 토큰을 HTML 폼에 포함시키는 방법 -->
		</form>
		<a href="#" id="logout">로그아웃</a>
		<%-- a링크에서 submit기능을 얻기위해 아래 스크립트 활용 --%>
		<script type="text/javascript">
			const logout = document.getElementById('logout');
			logout.onclick=function(event){
				document.getElementById('frm_logout').submit();
				event.preventDefault();
			}
		</script>
	</c:if>
	<%-- ==============사용자,관리자 공통 로그인 영역 끝============== --%>
</div>
<!-- 상단 끝 -->  





















