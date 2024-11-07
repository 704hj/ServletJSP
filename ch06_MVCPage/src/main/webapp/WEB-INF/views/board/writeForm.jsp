<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글쓰기</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.7.1.min.js"></script>
<script>
    $(function(){
    	//회원탈퇴 유효성 체크
        $('#write_form').submit(function(){
            const items = document.querySelectorAll('.input-check');
            for(let i = 0; i < items.length; i++){
                if(items[i].value.trim() == ''){
                    const label = $('label[for="'+items[i].id+'"]');
                    alert(label.text() + ' 필수 입력');
                    items[i].value = '';
                    $(items[i]).focus();
                    return false;
                }
            }//end of for
        });//end of submit
    });
</script>

</head>
<body>
<div class="page-main">
	<jsp:include page="/WEB-INF/views/common/header.jsp"/>
	<div class="content-main">
		<h2>게시판 글쓰기</h2>
		<form id="write_form" action="write.do" method="post" enctype="multipart/form-data">
			<ul>
				<li>
					<label for="title">제목</label>
					<input type="text" name="title" id="title" maxlength="50" class="input-check">
				</li>
				<li>
					<label for="contnet">내용</label>
					<textarea rows="5" cols="40" name="content" id="content" class="input-check"></textarea>
				</li>
				<li>
				<label for="filename">이미지</label> <input type="file"
                        name="filename" id="filename" accept="image/gif,image/png,image/jpeg">
                </li>
			</ul>
			<div class="align-center">
				<input type="submit" value="등록">
				<input type="button" value="목록" onclick="location.href='list.do'">
			</div>
		</form>
	</div>
</div>
</body>
</html>




