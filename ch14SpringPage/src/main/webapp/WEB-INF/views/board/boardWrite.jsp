<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- 게시판 글쓰기 시작 -->
<div class="page-main">
	<h2>글쓰기</h2>
	<form:form modelAttribute="boardVO" action="write" id="board_register" enctype="multipart/form-data">
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<form:label path="category">분류</form:label>
				<form:select path="category">
					<option disabled="disabled" selected>선택하세요</option>
					<form:option value="1">자바</form:option>
					<form:option value="2">데이터베이스</form:option>
					<form:option value="3">자바스크립트</form:option>
					<form:option value="4">기타</form:option>
				</form:select>
				<form:errors path="category"/>
			</li>	
			<li>
				<form:label path="title">제목</form:label>
				<form:input path="title" placeholder="제목을 입력하세요."/>
				<form:errors path="title" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="content">내용</form:label>
				<form:textarea path="content"/>
				<form:errors path="content" cssClass="error-color"/>
			</li>
			<li>
				<form:label path="upload">파일업로드</form:label>
				<input type="file" name="upload" id="upload">
			</li>
		</ul>
		<div class="align-center">
			<form:button>전송</form:button>
			<input type="button" value="목록" onclick="location.href='list'">
		</div>
	</form:form>
</div>
<!-- 게시판 글쓰기 끝 -->



















