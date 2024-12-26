<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 회원권한 수정 - 관리자 시작 -->
<div class="page-main">
	<h2>회원관리(관리자 전용)</h2>
	<form action="admin_list" id="search_form" method="get">
		<ul class="search">
			<li>
				<select name="keyfield">
					<option value="1" <c:if test="${param.keyfield==1}">selected</c:if>>아이디</option>
					<option value="2" <c:if test="${param.keyfield==2}">selected</c:if>>이름</option>
					<option value="3" <c:if test="${param.keyfield==3}">selected</c:if>>이메일</option>
					<option value="4" <c:if test="${param.keyfield==4}">selected</c:if>>전체</option>
				</select>
			</li>
			<li>
				<input type="search" name="keyword" id="keyword" value="${param.keyword}">
			</li>
			<li>
				<input type="submit" value="찾기">
				<input type="button" value="목록" onclick="location.href='admin_list'">
			</li>
		</ul>
	</form>
	<c:if test="${count == 0}">
	<div class="result-display">표시할 회원번호가 없습니다</div>
	</c:if>
	<c:if test="${count > 0}">
	<h2>회원목록</h2>
	<table class="striped-table">
		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>전화번호</th>
			<th>이메일</th>
			<th>가입일</th>
			<th>권한</th>
		</tr>
		<c:forEach var="member" items="${list}">
		<tr>
			<td>
			 	<c:if test="${member.auth==0}">${member.id}</c:if>
			 	<c:if test="${member.auth > 0}"><a href="${pageContext.request.contextPath}/member/admin_update?mem_num=${member.mem_num}">${member.id}</a></c:if>
			</td>
			<td>${member.name}</td>
			<td>${member.phone}</td>
			<td>${member.email}</td>
			<td>${member.reg_date}</td>
			<td>
				<c:if test="${member.auth==0}">탈퇴</c:if>
				<c:if test="${member.auth==1}">정지</c:if>
				<c:if test="${member.auth==2}">일반</c:if>
				<c:if test="${member.auth==9}">관리</c:if>
			</td>
		</tr>
		</c:forEach>
	</table>
	<div class="align-center">${page}</div>
	</c:if>
</div>
<!-- 회원권한 수정 - 관리자 끝 -->





