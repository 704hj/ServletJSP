<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num==null){//로그인이 되지 않은 경우
		response.sendRedirect("loginForm.jsp");
	}else{//로그인 된 경우
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css" type="text/css">
<script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
		$('#write_form').submit(function() {
			const items = document.querySelectorAll('.input-check');
			for(let i=0; i<items.length;i++){
				if(items[i].value.trim()==''){
					const label = document.querySelector('label[for="'+items[i].id+'"]');
					const label_text = label.textContent;
					alert(label_text + ' 필수 입력');
					items[i].value ='';
					items[i].focus();
					return false;
				}
				}
				
			}
		});
	});

</script>
</head>
<body>
<div class="page-main">
	<h1>글쓰기</h1>
	<form action="write.jsp" method="post" id="write_form">
		<ul>
			<li>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" size="30" class="input-check" maxlength="50">
			</li>
			<li>
				<label for="content">내용</label>
				<textarea rows="5" cols="40" id="content" name="content" classs="input-check"></textarea>
			</li>
		</ul>
		<div class="align-center">
		 	<input type="submit" value="글쓰기">
		 	<input type="button" value="홈으로" onclick="location.href='main.jsp'">
		 	
		</div>
	</form>
</div>
</body>
</html>
<%
	}
%>
