$(function(){
	/*======================================
	 * 댓글 등록
	*====================================== */	
	//댓글 등록 이벤트 연결            event넣어야함
	$('#re_form').submit(function(event){
		if($('#re_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#re_content').val('').focus();
			return false;
		}
		//form 이하의 태그에 입력한 데이터를 모두 읽어서 쿼리 스트링으로 반환
		let form_data = $(this).serialize();
		//서버와 통신
		$.ajax({
			url:'writeReply.do',
			type:'post',
			data:form_data,
			dataType:'json',
			success:function(param){
			   if(param.result=='logout'){
					alert('로그인해야 작성할 수 있습니다.');
			   }else if(param.result== 'success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글 목록을 다시 호출함
					//selectList(1);
			   }else{
			        alert('댓글 등록 오류 발생');
			   }
			},error:function(){
			    alert('네트워크 오류 발생');
		    }
		});
		//기본 이벤트 제거
		event.preventDefault();
		
	});
	//댓글 작성 폼 초기화
	function initForm(){
		$('textarea').val('');
		$('#re_first .letter-count').text('300/300');
	}
});
/**
 * 
 */