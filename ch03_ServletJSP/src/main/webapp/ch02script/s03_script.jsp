<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>[실습]성적처리</title>
</head>
<body>
<%--
배열 생성(score): 국어,영어,수학 성적을 배열에 저장
[출력예시]
국어 : 88
영어 : 99
수학 : 86
총점 : 273
평균 : 91
 --%>
 <%
 	//선언부 : 변수 선언, 메서드 선언
 	String[] cours = {"국어","수학","영어"};
 	int[] test = {88,99,86};

 	int sum = 0;
 	int avg = 0;
 	for(int i=0;i<test.length;i++){
 %>
 <%=cours[i] %> :  <%=test[i] %><br>

  
 <%//총점 구하기
 	 sum += test[i];
 	}
 	
 	//평균 구하기
	 avg = sum/ test.length;
%>
<%--HTML영역 :   JAVA영역 --%>
		총점 : <%= sum %><br>
		평균 : <%= avg %>

</body>
</html>