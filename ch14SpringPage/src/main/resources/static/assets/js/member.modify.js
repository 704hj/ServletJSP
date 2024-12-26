$(function(){
/*--------------------
 *      회원정보 수정
 *--------------------*/

//아이디 중복 여부 저장 변수 : 0 -> 아이디 중복 또는 중복체크 미실행
//                        1 -> 아이디 미중복
let checkId = 0;
//별명 중복 여부 저장 변수 : 0 -> 별명 중복 또는 중복체크 미실행
//                       1 -> 별명 미중복
let checkNick = 0;

//별명 중복 체크
$('#confirm_nick').click(function(){
if($('#nick_name').val().trim()==''){
$('#message_nick').css('color','red')
                  .text('별명을 입력하세요');
$('#nick_name').val('').focus();
return;  
}

$('#message_nick').text(''); //메시지 초기화

//서버와 통신
$.ajax({
url:'confirmIdnNickName',
type:'get',
data:{nick_name:$('#nick_name').val()},
dataType:'json',
success:function(param){
if(param.result == 'nickNotFound'){
checkNick = 1;
$('#message_nick').css('color','#000')
                  .text('등록 가능 별명'); 
}else if(param.result == 'nickDuplicated'){
checkNick = 0;
$('#message_nick').css('color','red')
                  .text('중복된 별명');
$('#nick_name').val('').focus();
}else if(param.result == 'notMatchPattern'){
checkNick = 0;
$('message_nick').css('color','red')
                 .text('한글,영문,숫자 2~10자만 가능');
$('#nick_name').val('').focus(); 
}else{
checkNick=0;
alert('별명 중복 체크 오류');
}
},
error:function(){
checkNick=0;
alert('네트워크 오류 발생');
}
});

});//end of click

//별명 중복 안내 메시지 초기화 및 아이디,별명 중복 값 초기화
$('#nick_name').keydown(function(){
checkNick=0;
$('#message_nick').text('');
});//end of keydown

//submit 이벤트 발생시 아이디, 별명 중복 체크 여부 확인
$('#member_register').submit(function(){

//별명 중복 체크 선택
if($('#nick_name').val()!=$('#nick_name').attr('value')  && checkNick == 0){
$('#message_nick').css('color','red').html('<div>별명 중복 체크 필수, 별명을 사용하지 않을 경우 별명을 지우고 전송하세요</div>');
if($('#nick_name').val().trim()==''){
$('#nick_name').val('').focus();
}
return false;
}
});
});
