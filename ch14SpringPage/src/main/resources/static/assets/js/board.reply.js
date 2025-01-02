$(function(){
	/*============================
	* 식별자 정의
	*============================*/
	/*부모글 아래에 보여질 댓글 개수*/
	let rowCount = 10;
	let currentPage;
	let count;
	
	/*============================
	* 댓글 목록
	*============================*/
	//댓글 목록
	function selectList(pageNum){
		currentPage = pageNum;
		//서버와 통신
		$.ajax({
			url:'listReply',
			type:'get',
			data:{board_num:$('#board_num').val(),
				pageNum:pageNum,rowCount:rowCount},
			dateType:'json',
			beforeSend:function(){
				$('#loading').show();//로딩 이미지 표시
			},
			complete:function(){
				//success와 error 콜백이 호출된 후에 호출
				$('#loading').hide();//로딩 이미지 숨기기
			},
			success:function(param){
				count = param.count;
				
				if(pageNum == 1){
					//처음 호출시는 해당 ID의 div의 내부 내용물을 제거
					$('#output').empty();
				}
				//댓글수 읽기
				displayReplyCount(param.count);
				
				//댓글 목록 작업
				$(param.list).each(function(index,item){
					//처음에는 보여지지 않고 다음 댓글부터 수평선이 보여지게 처리
					if(index>0) $('#output').append('<hr size="1" width="100%">');
					
					let output = '<div class="item">';
					output += '<ul class="detail-info">';
					output += '<li>';
					output += '<img src="../viewProfile?mem_num=>';
					output += '</li>';
					output += '<li>';
				});
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
	}
	//다음 댓글 보기 버튼 클릭시 데이터 추가
	$('.paging-button input').click(function(){
		selectList(currentPage + 1);
	});
	/*============================
	* 댓글 등록
	*============================*/
	//댓글 등록                     event가 submit되면 안된ㄷㅏ고?
	$('#re_form').submit(function(event){
		if($('#re_content').val().trim()==''){
			alert('내용을 입력하세요');
			$('#re_content').val('').focus();
			return false;
		}
		let form_data = $(this).serialize();
		//서버와 통신
		$.ajax({
			url:'writeReply',
			type:'post',
			data:form_data,
			dataType:'json',
			beforeSend:function(xhr){//보안적으로 같은 사이트에서 보낸다고 인식하게 됨
				xhr.setRequestHeader($('#csrfHeaderName').val(),
									$('#csrfTokenValue').val());
			},
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 작성할 수 있습니다.');
				}else if(param.result == 'success'){
					//폼 초기화
					initForm();
					//댓글 작성이 성공하면 새로 삽입한 글을 포함해서 첫번째 페이지의 게시글들을 다시 호출함
					selectList(1);
				}else{
					alert('댓글 등록 오류 발생');
				}
			},
			error:function(){
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
	/*============================
	* 초기 데이터(목록) 호출
	*============================*/
	selectList(1);
	
});

















