<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 폼</title>
<style type="text/css">
form{
	width:500px;
	margin:0 auto;
	border:1px solid #000;
	padding:10px 10px 10px 30px;
}
label{
	float:left;
	width:100px;
}
</style>
</head>

<body>
<form action="processJoining.jsp" method="post">
<label>아이디 </label><input type="text" name="id" size="10"><br>
<label>비밀번호 </label><input type="password" name="password" size="10"><br>
<label>이름 </label> <input type="text" name="name" size="10"><br>
<label>주소 </label> <input type="email" name="email" size="10"><br>
<label>전화번호 </label> <input type="text" name="address" size="30"><br>
<input type="submit" value="회원가입">
</form>
</body>

</html>












