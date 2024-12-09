<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리포트 등록 폼</title>
</head>
<body>
<form:form action="submitReport.do" enctype="multipart/form-data" modelAttribute="submitReportVO">
	<ul>
		<li>
			<form:label path="subject">제목</form:label>
			<form:input path="subject"/>
			<form:errors path="subject"/>
		</li>
		<li>
			<form:label path="reportFile">리포트 파일</form:label>
			<!-- 파일 전송관련은 form으로 쓸 수 없음 -->
			<input type="file" id="reportFile" name="reportFile">
			<form:errors path="reportFile"/>
		</li>
	</ul>
	<div align="center">
		<form:button>리포트 전송</form:button>
	</div>

</form:form>
</body>
</html>