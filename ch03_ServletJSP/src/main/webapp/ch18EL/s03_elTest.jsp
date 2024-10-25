<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EL - JSP 4개 영역에서 데이터 읽기</title>
</head>
<body>
<%
                            //속성 ,  값
	pageContext.setAttribute("msg", "봄");
	request.setAttribute("msg2", "여름");
	session.setAttribute("msg3", "가을");
	application.setAttribute("msg4", "겨울");
%>
<%-- 
내장 객체명을 생략하고 속성명을 호출하면 JSP4개 영역에서 page,request,session,application 영역 순으로 검색해서 
해당 속성명이 있으면 속성값을 반환
 --%>
page scope(영역) : <%=pageContext.getAttribute("msg") %>,
					${pageScope.msg}, ${msg}
<br>
request scope(영역) : <%=request.getAttribute("msg2") %>,
					${requestScope.msg2}, ${msg2}
<br>	
session scope(영역) : <%=session.getAttribute("msg3") %>,
					${sessionScope.msg3}, ${msg3}
<br>
application scope(영역) : <%=application.getAttribute("msg4") %>,
					${applicationScope.msg4}, ${msg4}
<br>
</body>
</html>