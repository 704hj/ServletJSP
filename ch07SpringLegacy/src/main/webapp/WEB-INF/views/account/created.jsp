<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계정 생성</title>
</head>
<body>
계정이 생성되었습니다.
<ul>
	<li>아이디 : ${memberVO.id}</li>
	<li>이름 : ${memberVO.name}</li>
	<li>우편번호 : ${memberVO.zipcode}</li>
	<li>주소 : ${memberVO.address1} ${memberVO.address2}</li>
</ul>
</body>
</html>