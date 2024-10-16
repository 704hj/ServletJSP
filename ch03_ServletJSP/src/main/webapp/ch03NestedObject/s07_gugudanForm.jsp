<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>구구단 입력</title>
<script type="text/javascript">
    window.onload=function(){
        const myForm = document.getElementById('myForm');
        //이벤트 연결
        myForm.onsubmit=function(){
            const dan = document.getElementById('dan');
            if(dan.value.trim()==''){
                alert('단을 입력하세요!');
                dan.value = ''; //공백을 입력했을 경우 공백 제거
                dan.focus();
                return false;
            }
            if(isNaN(dan.value)){
                alert('숫자만 입력하세요!');
                dan.value='';//숫자가 아닌 값을 입력한 경우 초기화 
                dan.focus();
                return false;
            }
            if(dan.value<2 || dan.value>9){
                alert('2~9단을 입력하세요!');
                dan.value='';//숫자가 아닌 값을 입력한 경우 초기화 
                dan.focus();
                return false;
            }
            
        }
    };
</script>
</head>
<body>
<form action="s08_gugudan.jsp" id="myForm" method="get">
단 <input type="text" name="dan" id="dan"><br>
<input type="submit" value="전송">
</form>
</body>
</html>