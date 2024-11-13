<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅 상세</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	$(function(){
		//웹소켓 생성
		
		//엔터키 처리 이벤트 연결
		$('#message').keydown(function(event){
			if(event.keyCode == 13 && !event.shiftKey){
				$('#chatting_form').trigger('submit');
				
			}
			
		})
		//채팅 목록 
		function selectData(){
			//서버와 통신
			$.ajax({
				url:'chatMessageList.do',
				type:'post',
				data:{recv_num:$('#recv_num').val()},
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						alert('로그인 후 사용하세요!')
					}else if(param.result == 'success'){
						$('#chatting_message').empty();
						
						let chat_date='';
						$(param.list).each(function(index,item){
							let output = '';
							//날짜 추출
							if(chat_date != item.chat_date.split(' ')[0]) {
								chat_date = item.chat_date.split(' ')[0];
								output += '<div class="date-position"><span>'+chat_date+'</span></div>';
							}
							
							//받은 데이터 왼쪽, 보낼 데이터 오른쪽
											//session 에서 가져온 el
							if(item.send_num == ${user_num}){
								output += '<div class="from-position">'+item.id;
							}else{
								output += '<div class="to-position">'+item.id;
								$('#recv_id').text(item.id);
							}
							
							output += '<div class="item">';
							output += (item.read_check !=0 ? '<b>①</b>' : '')+ ' <span>' + item.message + '</span>';
							//시간표시
							output += '<div class="align-right">' + item.chat_date.split(' ')[1] + '</div>';
							output += '</div>';
							output += '</div>';
							
							//문서 객체에 추가
							$('#chatting_message').append(output);
							//스크롤 하단으로 위치시킴
							$('#chatting_message').scrollTop($('#chatting_message')[0].scrollHeight);
							
						});
						
					}else{
						alert('채팅 읽기 오류 발생');
					}
				},
				error:function(){
					alert('네트워크 오류 발생');					
				}
			
			
			})
			
		}
		//채팅 등록
		$('#chatting_form').submit(function(event){
			if($('#message').val().trim()==''){
				alert('내용을 입력하세요!');
				$('#message').val('').focus();
				return false;
			}
			//form 이하의 태그에 입력한 데이터를 모두 읽어옴
			let form_data = $(this).serialize();
			//서버와 통신
			$.ajax({
				url:'writeChatOne.do',
				type:'post',
				data:form_data,
				dataType:'json',
				success:function(param){
					if(param.result == 'logout'){
						
					}else if(param.result == 'success'){
						//폼 초기화
						$('#message').val('').focus();
						selectData();
					}else{
						alert('채팅 등록 오류 발생');
					}
					
				},
				error:function(){
					alert('네트워크 오류 발생');
				}
				
			});
			
			//기본 이벤트 제거
			event.preventDefault();
			
		});
	
		//초기 데이터 호출
		selectData();
		
	});
	

</script>
</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<c:if test="${user_auth == 9}">
			<h2>${user_id} 관리자님이 <span id="recv_id"></span>님과 채팅 중</h2>
		</c:if>
		<c:if test="${user_auth != 9}">
			<h2>${user_id}님이 관리자와 채팅</h2>
		</c:if>
		<div id="chatting_message"></div>
		<form id="chatting_form">
			<input type="hidden" name="recv_num" value="${recv_num}" id="recv_num">
			<ul>
				<li>
					<label for="message">내용</label>
					<textarea rows="5" cols="40" name="message" id="message"></textarea>
					<input type="submit" value="전송">
				</li>
			</ul>
		</form>
	</div>
</div>
</body>
</html>