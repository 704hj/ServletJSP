$(function(){
	/*-------------------
	 *		회원가입
	 *-------------------*/
	
	 //아이디 중복 여부 저장 변수 : 0 -> 아이디 중복 또는 중복체크 미실행
	 //						  1 -> 아이디 미중복
	 let checkId = 0;
	 //별명 중복 여부 저장 변수 : 0 -> 별명 중복 또는 중복체크 미실행
	 //						1 -> 별명 미중복
	 let checkNick = 0;
	 
	 //아이디 중복 체크
	 $('#confirm_id').click(function(){
		if($('#id').val().trim()==''){
			$('#message_id').css('color','red').text('아이디를 입력하세요');
			$('#id').val('').focus();
			return;
		}
		
		$('#message_id').text('');//메시지 초기화
		
		//서버와 통신
		$.ajax({
			url:'confirmIdnNickName',
			type:'get',
			data:{id:$('#id').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'idNotFound'){
					checkId = 1;
					$('#message_id').css('color','#000').text('등록 가능 ID');
				}else if(param.result == 'idDuplicated'){
					checkId = 0;
					$('#message_id').css('color','red').text('중복된 ID');
					$('#id').val('').focus();
				}else if(param.result == 'notMatchPattern'){
					checkId = 0;
					$('#message_id').css('color','red').text('영문,숫자 4~12 입력');
					$('#id').val('').focus();
				}
			},
			error:function(){
				checkId = 0;
				alert('ID 중복 체크 오류');
			}
			});
		
	 });//end of click
	 
	 //별명 중복 체크
	 $('#confirm_nick').click(function(){
		if($('#nick_name').val().trim()==''){
			$('#message_nick').css('color','red').text('별명을 입력하세요');
			$('#nick_name').val('').focus();
			return;
		}
		$('#message_nick').text('');//메시지 초기화
		
		//서버와 통신
		$.ajax({
			url:'confirmIdnNickName',
			type:'get',
			data:{nick_name:$('#nick_name').val()},
			dataType:'json',
			success:function(param){
				if(param.result == 'nickNotFound'){
					checkNick = 1;
					$('#message_nick').css('color','#000').text('등록 가능 별명');
				}else if(param.result == 'nickDuplicated'){
					checkNick = 0;
					$('#message_nick').css('color','red').text('중복된 별명');
					$('#nick_name').val('').focus();
				}else if(param.result == 'NotMatchPattern'){
					checkNick = 0;
					$('#message_nick').css('color','red').text('한글,영문,숫자2~10자만 가능');
					$('#nick_name').val('').focus
				}
			}
		});
		
	 })
	 //아이디,별명 중복 안내 메시지 초기화 및 아이디,별명 중복 값 초기화
	 $('#id,#nick_name').keydown(function(){
		if($(this).attr('id') == 'id'){
			checkId=0;
			$('#message_id').text('');
		}else if($(this).attr('id') == 'nick_name'){
			checkNick=0;
			$('#message_nick').text('');
		}
		
	 });//end of keydown
	 
	 //submit 이벤트 발생시 아이디, 별명 중복 체크 여부 확인
	 $('#member_register').submit(function(){
		if(checkId==0){
			$('#message_id').css('color','red').text('아이디 중복 체크 필수!');
			if($('#id').val().trim()==''){
				$('#id').val('').focus();
			}
			return false;
		}
		//별명 중복 체크 선택
		if($('#nick_name').val()!='' && checkNick == 0){
			$('#message_nick').css('color','red').html('<div>별명 중복 체크 필수, 별명을 사용하지 않을 경우 별명을 지우고 전송하세요.')
			if($('#nick_name').val().trim()==''){
				$('#nick_name').val('').focus();
			}
			return false;
		}
	 });
	
});
































