<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num==null){//로그인이 되지 않은 경우
		response.sendRedirect("loginForm.jsp");
	}else{
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원탈퇴 폼</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" type="text/css">
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
	window.onload=function(){
		const form = document.getElementById('delete_form');
		//이벤트 연결
		form.onsubmit=function(){
			const id = document.getElementById('id');
			if(id.value.trim()==''){
				alert('아이디를 입력하세요!');
				id.value = '';
				id.focus();
				return false;
			}
			const passwd = document.getElementById('passwd');
			if(passwd.value.trim()==''){
				alert('비밀번호를 입력하세요!');
				passwd.value = '';
				passwd.focus();
				return false;
			}
			const cpasswd = document.getElementById('cpasswd');
			if(cpasswd.value.trim()==''){
				alert('비밀번호 확인을 입력하세요!');
				//비밀번호 확인 필드의 값을 지우고 다시 입력하도록 하는 경우
				cpasswd.value = '';
				//해당 입력 필드로 포커스가 이동하게 되어, 사용자가 마우스나 키보드를 따로 조작하지 않아도 바로 입력
				cpasswd.focus();
				return false;
			}
			//비밀번호와 비밀번호 확인 일치 여부 체크
			if(passwd.value != cpasswd.value){
				alert('아이디와 비밀번호 확인이 불일치합니다.')
				cpasswd.value = '';
				cpasswd.focus();
				return false;
			}
				
		};

	};
</script>
</head>
<body>
<div class="page-main">
	<h1>회원탈퇴</h1>
	<form action="deleteUser.jsp" method="post" id="delete_form">
		<ul>
			<li>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id" maxlength="12">
				
			</li>
			<li>
				<label for="passwd">비밀번호</label>
				<input type="password" name="passwd" id="passwd" maxlength="12">
			</li>
			<li>
				<label for="cpasswd">비밀번호 확인</label>
				<input type="password" id="cpasswd" maxlength="12">
			</li>
		</ul>
		<div class="align-center">
		 	<input type="submit" value="회원탈퇴">
		 	<input type="button" value="홈으로" onclick="location.href='main.jsp'">
		 	
		</div>
	</form>
</div>
</body>
</html>
<%
	}
%>