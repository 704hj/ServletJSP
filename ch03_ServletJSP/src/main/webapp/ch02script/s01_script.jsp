<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1부터 100까지의 합</title>
</head>
<body>
<%
	//스크립트릿 : 변수 선언,연산,제어문,출력
	int sum = 0;
	for(int i=0;i<=100;i++){
		sum += i;
	}
%>
<!-- HTML 주석은 컴파일시 출력된 화면 페이지 원본보기에서 사라지지 않는다.(노출되도 되는 주석) -->
<%--
JSP 주석 -> 컴파일시 출력된 화면 페이지 원본보기에서 사라진다. (노출되면 안되는 주석)
표현식 : 변수의 값, 메서드의 결과값, 연산의 결과값 출력 
--%>

<!-- 스크립트릿 빠져나와서 출력작성 -->
1부터 100까지의 합은 <%= sum /*여러줄 주석 사용 가능, 한 줄 주석은 오류*/ %> 입니다.
</body>
</html>