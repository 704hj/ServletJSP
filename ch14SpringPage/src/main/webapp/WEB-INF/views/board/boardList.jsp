<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%><!-- 로그인 관련 태그 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!-- 게시판 목록 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/member.register.js"></script>
<script type="text/javascript">
	let result = '${result}';
	if(result == 'success'){
		alert('글 등록이 완료되었습니다.');
	}
</script>


<div class="page-main">
	<h2>게시판 목록</h2>
	<div>
		<!-- 모든 항목을 표시 				자바 카테고리의 항목만 표시-->
		<a href="list">전체</a> | 
		<a href="list?category=1">자바</a> | 
		<a href="list?category=3">자바스크립트</a> | 
		<a href="list?category=4">기타</a>
	</div>
	<form action="list" id="search_form" method="get">
		<input type="hidden" name="category" value="${param.category}">
		<ul class="search">
			<li>
				<select name="keyfield" id="keyfield">
					<option value="1" <c:if test="${param.keyfield == 1}">selected</c:if>>제목</option>
					<option value="2" <c:if test="${param.keyfield == 2}">selected</c:if>>ID + 별명</option>
					<option value="3" <c:if test="${param.keyfield == 3}">selected</c:if>>내용</option>
					<option value="4" <c:if test="${param.keyfield == 4}">selected</c:if>>제목 + 내용</option>
				</select>
			</li>
			<li>
				<input type="search" name="keyword" id="keyword" value="${param.keyoword}">
			</li>
			<li>
				<input type="submit" value="찾기">
			</li>
		</ul>
		<div class="align-right">
			<select id="order" name="order">
				<option value="1" <c:if test="${param.order == 1}">selected</c:if>>최신순</option>
				<option value="2" <c:if test="${param.order == 2}">selected</c:if>>조회수</option>
				<option value="3" <c:if test="${param.order == 3}">selected</c:if>>좋아요</option>
				<option value="4" <c:if test="${param.order == 4}">selected</c:if>>댓글수</option>
			</select>
			<script>
			$(function(){
				$('#order').change(function(){
					location.href='list?category=${param.category}&keyfield='+$('#keyfield').val()+'&keyword='+$('#keyword').val()+'&order='+$('#order').val();
				});
			});
			</script>
			<c:if test="${!empty principal}">
				<input type="button" value="글쓰기" onclick="location.href='write'">
			</c:if>
		</div>
	</form>
	<c:if test="${count == 0}">
	<div class="result-display">표시할 게시물이 없습니다.</div>
	</c:if>
	<c:if test="${count > 0}">
	<table class="striped-table">
		<tr>
			<th>번호</th>
			<th>분류</th>
			<th width="400">제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>조회수</th>
			<th>좋아요수</th>
		</tr>
		<c:forEach var="board" items="${list}">
		<tr>
			<td class="align-center">${board.board_num}</td>
            <td class="align-center">${board.categoryName}</td>
            <td class="align-left"><a href="detail?board_num=${board.board_num}">${board.title}(${board.re_cnt})</a></td>
            <td class="align-center">
            	<c:if test="${empty board.nick_name}">${board.id}</c:if>
            	<c:if test="${empty board.nick_name}">${board.nick_name}</c:if>
            </td>
            <td class="align-center">${board.reg_date}</td>
            <td class="align-center">${board.hit}</td>
            <td class="align-center">${board.fav_cnt}</td>
		</tr>
		</c:forEach>
	</table>
	<div class="align-center">${page}</div>
	</c:if>
</div>
<!-- 게시판 목록 끝 -->































