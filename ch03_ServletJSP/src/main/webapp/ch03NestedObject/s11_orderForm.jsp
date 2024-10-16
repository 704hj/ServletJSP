<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="s12_order.jsp" method="post" id="myForm">
	이름 <input type="text" name="name" id="name"><br>
	배송희망일 <input type="date" name="order_date" id="order_date"><br>
	상품 (30만원 미만 배송비 5천원 추가) : 
	<input type="check box" name="item" value="가방">가방(20만원)
	<input type="check box" name="item" value="신발">신발(10만원)
	<input type="check box" name="item" value="옷">옷(5만원)
	<input type="check box" name="item" value="식사권">식사권(15만원)
	<br>
	<input type="submit value="전송">
</body>
</html>