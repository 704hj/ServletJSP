<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 쓰기</title>
</head>
<body>
게시글 쓰기 입력폼
<form action="newArticle.do" method="post">
    아이디: <input type="text" name="parentId"><br>
    제목 : <input type="text" name="title"><br>
    내용 : <textarea name="content" rows="5" cols="30"></textarea>
    <input type="submit" value="전송">
</form>
</body>
</html>
