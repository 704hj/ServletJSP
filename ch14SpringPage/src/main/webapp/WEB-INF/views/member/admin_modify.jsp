<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 회원권한 수정 - 관리자 시작 -->
<div class="page-main">
	<h2>회원권한 수정</h2>
	<form:form modelAttribute="memberVO" action="admin_update" id="modify_form">
		<form:hidden path="mem_num"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<label>회원권한</label>
				<c:if test="${memberVO.auth < 9}">
				<!-- 선택가능한 버튼이 나옴 -->
				<form:radiobutton path="auth" value="1"/>정지
				<form:radiobutton path="auth" value="2"/>일반
				</c:if>
				<c:if test="${memberVO.auth == 9}">관리</c:if>
			</li>
		</ul>
		<div class="align-center">
			<c:if test="${memberVO.auth != 9}">
			<form:button>전송</form:button>
			</c:if>
			<input type="button" value="회원목록" onclick="location.href='admin_list'">
		</div>
		<ul>
			<li>
				<span>이름</span>	${memberVO.name}
			</li>
			<li>
				<span>전화번호</span>	${memberVO.phone}
			</li>
			<li>
				<span>이메일</span>	${memberVO.email}
			</li>
			<li>
				<span>우편번호</span>	${memberVO.zipcode}
			</li>
			<li>
				<span>주소</span>	${memberVO.address1} ${memberVO.address2}
			</li>
		</ul>
	</form:form>
</div>
<!-- 회원권한 수정 - 관리자 끝 -->
