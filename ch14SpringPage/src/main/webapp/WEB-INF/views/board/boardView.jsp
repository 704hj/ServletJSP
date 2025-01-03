<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%><!-- 로그인 관련 태그 -->
<sec:authorize access="isAuthenticated()">
	<sec:authentication property="principal" var="principal"/>
</sec:authorize>
<!-- 글 상세 시작 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/board.fav.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/board.reply.js"></script>
<div class="page-main">
	<h2>[${board.categoryName}]${board.title}</h2>	
	<ul class="detail-info">
		<li>
			<img src="${pageContext.request.contextPath}/member/viewProfile?mem_num=${board.mem_num}" width="40" height="40" class="my-photo"/>		
		</li>
		<li>
			<c:if test="${empty board.nick_name}">${board.id}</c:if>
			<c:if test="${!empty board.nick_name}">${board.nick_name}</c:if>
			<br>
			<c:if test="${empty board.modify_date}">
			작성일 : ${board.reg_date}
			</c:if>
			<c:if test="${!empty board.modify_date}">
			최근 수정일 : ${board.modify_date}
			</c:if>
			조회 : ${board.hit}
		</li>
	</ul>
	<c:if test="${!empty board.filename}">
	<ul>
		<li>첨부파일 : <a href="file?board_num=${board.board_num}">${board.filename}</a></li>
	</ul>
	</c:if>
	<hr size="1" width="100%">
	<c:if test="${
					fn:endsWith(board.filename,'.jpg') ||
					fn:endsWith(board.filename,'.JPG') ||
					fn:endsWith(board.filename,'.jpeg') ||
					fn:endsWith(board.filename,'.JPEG') ||
					fn:endsWith(board.filename,'.gif') ||
					fn:endsWith(board.filename,'.GIF') ||
					fn:endsWith(board.filename,'.png') ||
					fn:endsWith(board.filename,'.PNG')}">
	<div class="align-center">
		<img src="${pageContext.request.contextPath}/assets/upload/${board.filename}" class="detail-img">
	</div>				
	</c:if>
	<div class="detail-content">
		${board.content}
	</div>
	<div>
		<%-- 좋아요 --%>
		<img id="output_fav" data-num="${board.board_num}"
		  data-header="${_csrf.headerName}"
		  data-token="${_csrf.token}"
		  src="${pageContext.request.contextPath}/assets/images/fav01.gif" width="40">
		<span id="output_fcount"></span>
		<%-- 댓글수 --%>
		<span id="output_rcount"></span>
	</div>
	<hr size="1" width="100%">
	<div class="align-right">
		<c:if test="${!empty principal && principal.memberVO.mem_num == board.mem_num}">
		<input type="button" value="수정" onclick="location.href='update?board_num=${board.board_num}'">
		<input type="button" value="삭제" id="delete_btn">
		<script type="text/javascript">
			const delete_btn = document.getElementById('delete_btn')
			delete_btn.onclick=function(){
				const choice = confirm('삭제하시겠습니까?');
				if(choice){
					location.replace('delete?board_num=${board.board_num}');
				}
			};
		</script>
		</c:if>
		<input type="button" value="목록" onclick="location.href='list'">
	</div>
	<!-- 댓글 UI 시작 -->
	<div id="reply_div">
		<span class="re-title">댓글 달기</span>
		<form id="re_form">
			<input type="hidden" id="csrfHeaderName" value="${_csrf.headerName}">
			<input type="hidden" id="csrfTokenValue" value="${_csrf.token}">
			<input type="hidden" name="board_num" value="${board.board_num}" id="board_num">
			<textarea rows="3" cols="50" name="re_content" id="re_content" class="rep-content" class="rep-content" 
			<c:if test="${empty principal}">disabled="disabled"</c:if>
			><c:if test="${empty principal}">로그인해야 작성할 수 있습니다.</c:if></textarea>
			<c:if test="${!empty principal}">
			<div id="re_first">
				<span class="letter-count">300/300</span>
			</div>
			<div id="re_second" class="align-right">
				<input type="submit" value="전송">
			</div>	
			</c:if>	
		</form>
	</div>
	<!-- 댓글 목록 출력 -->
	<div id="output"></div>
	<div id="loading" style="display:none;">
		<img src="${pageContest.request.contextPath}/assets/images/loading.gif" width="30" height="30">
	</div>
	<div class="paging-button" style="display:none;">
		<input type="button" value="더보기">
	</div>
</div>
<!-- 글 상세 끝 -->





















