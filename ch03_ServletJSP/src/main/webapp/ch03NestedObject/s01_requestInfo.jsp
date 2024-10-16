<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>클라이언트 및 서버 정보</title>
</head>
<body>
클라이언트IP = <%= request.getRemoteAddr() %> <br>
요청정보 프로토콜 = <%=request.getProtocol() %> <br>
요청정보 전송방식 = <%=request.getMethod() %> <br>
요청 URL(Uniform Resource Locator) = <%=request.getRequestURL() %> <br>    <%-- 다른 서버, 네이버 서버를 가져올 때 --%>
요청 URI(Uniform Resource Identifier) = <%=request.getRequestURI() %> <br> <%-- 같은 서버, 내가 작성한 코드를 가져오려고 할 때 --%>
컨텍스트 경로 = <%= request.getContextPath() %> <br>
서버패스 = <%= request.getServletPath() %> <br>
쿼리 문자열 = <%= request.getQueryString() %> <br>
서버 이름 = <%= request.getServerName() %> <br>
서버 포트 = <%= request.getServerPort() %> <br>
</body>
</html>