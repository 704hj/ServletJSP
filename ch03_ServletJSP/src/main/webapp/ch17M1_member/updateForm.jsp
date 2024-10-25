<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="kr.board.dao.BoardDAO" %>    
<%@ page import="kr.board.vo.BoardVO" %>
<%
	Long user_num = (Long)session.getAttribute("user_num");
	if(user_num==null){//로그인이 안된 경우
		response.sendRedirect("longinForm.jsp");
	}else{//로그인이 된 경우
		long board_num = Long.parseLong(request.getParameter("board_num"));
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO board = dao.getBoard(board_num);
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(user_num == board.getNum()){
			
		
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 수정</title>
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
	<h1>글 수정</h1>
	<form action="update.jsp" method="post" id="update_form">
	
	<input type="hidden" name="board_num" value="<%=board_num %>">
		<ul>
			<li>
				<label for="title">제목</label>
				<input type="text" name="title" id="title" size="30" value="<%=board.getTitle() %>" class="input-check" maxlength="50">
			</li>
			<li>
				<label for="content">내용</label>
				<textarea rows="5" cols="40" id="content" name="content" class="input-check"><%=board.getContent() %></textarea>
			</li>
		</ul>
		<div class="align-center">
		 	<input type="submit" value="글 수정">
		 	<input type="button" value="홈으로" onclick="location.href='main.jsp'">
		 	
		</div>
	</form>
</div>
</body>
</html>
<%
		}else{
%>
	<script>
		alert('잘못된 접속입니다.');
		location.replace('list.jsp');
	</script>
<%			
		}	
	}		
%>



















