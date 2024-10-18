<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>프로필 사진 업로드 폼</title>
<script type="text/javascript">
	window.onload=function(){
		//img 태그 호출
		const image = document.getElementById('image');
		//전송 버튼 호출
		const submit_btn = document.getElementById('submit_btn');
	}
	
</script>
</head>
<body>
<h2>프로필 사진 업로드하기</h2>
<img id="image" src="../images/face.png" width="200" height="200" alt="Image Prreview">
<form action="/ch03_ServletJSP/profile" method="post" enctype="multipart/form-data">
	
	<input type="file" name="file" id="file" accept="image/png, image/jpeg, image/gif">
	
	<input type="submit" value="전송" style="display:none;" id="submit_btn"> 
</form>
</body>
</html>


