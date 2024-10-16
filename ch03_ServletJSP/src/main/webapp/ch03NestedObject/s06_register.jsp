<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>요청 파라미터 출력</title>
</head>
<body>
<%
	//전송된 데이터 인코딩 처리(POST 방식으로 전송)
	request.setCharacterEncoding("utf-8");
	//String name = request.getParameter("name");->변수선언
%>
<h3>request.getParameter() 메서드 사용</h3>
name 파라미터 = <%= request.getParameter("name") %> <br>
id 파라미터 = <%= request.getParameter("id") %> <br>
password 파라미터 = <%= request.getParameter("password") %> <br>
phone 파라미터 = <%= request.getParameter("phone") %> <br>
content 파라미터 = <%= request.getParameter("content") %> <br>

<h3>request.getParameterValues() 메서드 사용</h3>
			
			<%-- <%= name %><br> --%>
			이름 : <%=request.getParameter("name")%><br>
			아이디 : <%=request.getParameter("id") %><br>
			비밀번호 : <%=request.getParameter("password") %><br>
			전화번호 : <%=request.getParameter("phone") %><br>
<%
	String[] values = request.getParameterValues("hobby");
	if(values != null){
		for(int i=0;i<values.length;i++){		
%>			
			취미 : <%= values[i] %>	
<%	
		}
	}else{
	
	}
%>
			자기소개 : <%= request.getParameter("content") %>
</body>
</html>