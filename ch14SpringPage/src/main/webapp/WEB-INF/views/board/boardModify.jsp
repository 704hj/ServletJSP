<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 게시판 글 수정 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<div class="page-main">
	<h2>글 수정</h2>
	<form:form modelAttribute="boardVO" action="update" id="board_modify" enctype="multipart/form-data">
		<form:hidden path="board_num"/>
		<form:errors element="div" cssClass="error-color"/>
		<ul>
			<li>
				<form:label path="category">분류</form:label>
				<form:select path="category">
					<form:option value="1">자바</form:option>
					<form:option value="2">데이터베이스</form:option>
					<form:option value="3">자바스크립트</form:option>
					<form:option value="4">기타</form:option>
				</form:select>
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
				<c:if test="${!empty boardVO.filename}">
				<div id="file_detail">
					(${boardVO.filename})파일이 등록되어 있습니다.
					<input type="button" value="파일 삭제" id="file_del">
				</div>
				<script type="text/javascript">
				$(function(){
					$('#file_del').click(function(){
						const choice = confirm('삭제하시겠습니까?');
						if(choice){
							//서버와 통신
							$.ajax({
								url:'deleteFile',
								data:{board_num:${boardVO.board_num}},
								type:'get',
								dataType:'json',
								success:function(param){
									if(param.result == 'logout'){
										alert('로그인 후 사용하세요');
									}else if(param.result == 'wrongAccess'){
										alert('잘못된 접속입니다.')
									}else if(param.result == 'success'){
										$('#file_detail').hide();
									}else{
										alert('파일 삭제 오류 발생');
									}
								},
								error:function(){
									alert('네트워크 오류 발생');
								}
							})
						}
					});
				});
				</script>
				</c:if>
			</li>
		</ul>
		<div class="align-center">
			<form:button>전송</form:button>
			<input type="button" value="상세정보" onclick="location.href='detail?board_num=${boardVO.board_num}'">
		</div>
	</form:form>
</div>
<!-- 게시판 글 수정 끝 -->



















