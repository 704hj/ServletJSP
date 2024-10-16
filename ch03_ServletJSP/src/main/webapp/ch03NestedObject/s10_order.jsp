<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문서 확인</title>
<style>
	.items{
		font-weight:bold;
		background:ivory;
		border:1px solid black;
		width:20%;
		height:30px;
		font-size:16px;
	}
	
	.total{
		font-weight:bold;
		color:#0080ff;
		width:20%;
		font-size:20px;
	}
</style>
</head>
<body>
<%--
짜장면 4,000원, 짬뽕 5,000원 볶음밥 6,000원
[출력 예시]
주문 음식
짜장면 2개
짬뽕 1개
총 지불금액 : 13,000원
 --%>
 <%
 	int[] orderArray = {4000,5000,6000};
 	String[] menus = {"짜장면","짬뽕","볶음밥"};
 	request.setCharacterEncoding("utf-8");
 	int[] amount = new int[orderArray.length];
 	int total = 0;
 	for(int i = 0; i < orderArray.length; i++)
 	{
 		amount[i] = Integer.parseInt(request.getParameter("food_c" + i));
 	}
 	
 	for(int i = 0; i < menus.length; i++){
 		if(amount[i] != 0){
%> 			
			<div class="items">
				<%=menus[i]%> <%=amount[i]%>개
			</div>
<%
			total += orderArray[i] * amount[i];			
 		}
 	}
%>
		<p class="total">총 지불금액: <%=String.format("%,d",total)%>원<p>
<% 	

 
 %>
</body>
</html>