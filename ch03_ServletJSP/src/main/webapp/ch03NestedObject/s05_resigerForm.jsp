<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[실습]회원가입</title>
</head>
<body>
<form action="s06_register.jsp" method="post">
<ul>
	<li>
		<label for="name">이름</label>
		<input type="text" name="name" size="10" id="name">
	</li>
	<li>
		<label for="id">아이디</label>
		<input type="text" name="id" size="10" id="id">
	</li>
	<li>
		<label for="password">비밀번호</label>
		<input type="password" name="password" size="10" id="password">
	</li>
	<li>
		<label for="phone">전화번호</label>
		<input type="text" name="phone" size="10" id="phone">
	</li>
	<li>
		<label>취미</label>
		<input type="checkbox" name="hobby" value="영화감상">영화감상
		<input type="checkbox" name="hobby" value="음악감상">음악감상
		<input type="checkbox" name="hobby" value="등산">등산
		<input type="checkbox" name="hobby" value="낚시">낚시
		<input type="checkbox" name="hobby" value="춤">춤
	</li>
	<li>
		<label for="content">자기소개</label>
		<textarea rows="5" cols="60" name="content" id="content"></textarea>
	</li>
</ul>
<input type="submit" value="전송">
</form>
</body>
</html>