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
			dataType:'json',
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
					output += '<img src="../member/viewProfile?mem_num='+item.mem_num+'" width ="40" height ="40" class="my-photo">';
					output += '</li>';
					output += '<li>';
					
					if(item.nick_name){
						output += item.nick_name + '<br>';
					}else{
						output += item.id + '<br>';
					}
					if(item.re_mdate){
						output += '<span class="modify-date">최근 수정일 : ' + item.re_mdate + '</span>';
					}else{
						output += '<span class="modify-date">등록일 : ' + item.re_date + '</span>';
					}
					output += '</li>';
					output += '</ul>';
					output += '<div class="sub-item">';
					output += '<p>' + item.re_content.replace(/\r\n/g,'<br>') + '</p>';
					
					//좋아요 시작
					if(item.click_num==0 || param.user_num!=click_num){
						output += ' <img class="output-rfav" src="../assets/images/heart01.png" data_num="'+item.re_num+'"> <span class="output-rfcount">'+item.refav_cnt+'</span>';
					}else{
						output += ' <img class="output-rfav" src="../assets/images/heart02.png" data_num="'+item.re_num+'"> <span class="output-rfcount">'+item.refav_cnt+'</span>';
					}
					//좋아요 끝
					
					if(param.user_num==item.mem_num){
						//로그인 한 회원번호와 댓글 작성자 회원번호가 일치
						output += ' <input type="button" data-num="'+item.re_num+'" value="수정" class="modify-btn">';
						output += ' <input type="button" data-num="'+item.re_num+'" value="삭제" class="delete-btn">';
					}
					
					output += '</div>';
					output += '</div>';
					
					//문서 객체에 추가
					$('#output').append(output);
				});
				//paging button 처리
				if(currentPage >= Math.ceil(count/rowCount)){
					//다음 페이지가 없음
					$('.paging-button').hide();
				}else{
					//다음 페이지가 존재
					$('.paging-button').show();
				}
					
			},
			error:function(){
				alert('네트워크 오류 발생');
			}
		});
		//다음 댓글 보기 버튼 클릭시 데이터 추가
			$('.paging-button input').click(function(){
				selectList(currentPage + 1);
			});
		
		//댓글 수 표시
		function displayReplyCount(count){
			let output;
			if(count > 0){
				output = '댓글수('+count+')';
			}else{
				output = '댓글수(0)';
			}
			//문서 객체에 추가
			$('#output_rcount').text(output);
		}
	}
	
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
	 * 댓글 수정
	 *============================*/
	//댓글 수정 버튼 클릭시 수정폼 노출
	$(document).on('click','.modify-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-num');
		//댓글 내용
		let re_content = $(this).parent().find('p').html().replace(/<br>/gi,'\r\n');
		//댓글 수정폼 UI
		let modifyUI ='<form id="mre_form">';
		modifyUI += '<input type="hidden" name="re_num" value="'+re_num+'">';
		modifyUI += '<textarea rows="3" cols="50" name="re_content" id="mre_content" class="rep-content">'+re_content+'</textarea>';
		modifyUI += '<div id="mre_first"><span class="letter-count">300/300<span></span></div>';
		modifyUI += '<div id="mre_second" class="align-right">';
		modifyUI += ' <input type="submit" value="수정">';
		modifyUI += ' <input type="button" value="취소" class="re-reset">';
		modifyUI += '</div>';
		modifyUI += '<hr size="1" noshade width="96%">';
		modifyUI += '</form>';
		
		//이전에 이미 수정하는 댓글이 있을 경우 수정버튼을 클릭하면 숨김 sub-item를 환원시키고 수정폼을 초기화함
		initModifyForm();
		//지금 클릭해서 수정하고자 하는 데이터는 감추기
		//(수정버튼을 감싸고 있는 div)
		$(this).parent().hide();
		//수정폼을 수정하고자 하는 데이터가 있는 div에 노출
		$(this).parents('.item').append(modifyUI);
		//입력한 글자수 셋팅
		let inputLength = $('#mre_content').val().length;
		let remain = 300 - inputLength;
		remain += '/300';//템플릿 리터럴 사용 : let remain = `${300 - inputLength}/300`;
		//문서 객체에 반영
		$('#mre_first .letter-count').text(remain);
	});
	//수정폼에서 취소 버튼 클릭시 수정폼 초기화
	$(document).on('click','.re-reset',function(){
		initModifyForm();
	})
	//댓글 수정폼 초기화
	function initModifyForm(){
		$('.sub-item').show();
		$('#mre_form').remove();
	}
	//댓글 수정
	$(document).on('submit','#mre_form',function(event){
		if($('#mre_content').val().trim()==''){
			alert('내용을 입력하세요!');
			$('#mre_content').val('').focus();
			return false;
		}
		//폼에 입력한 데이터 반환
		let form_data = $(this).serialize();
		//서버와 통신
		$.ajax({
			url:'updateReply',
			type:'post',
			data:form_data,
			dataType:'json',
			beforeSend:function(xhr){
				xhr.setRequestHeader($('#csrfHeaderName').val(),
									$('#csrfTokenValue').val());
			},
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 수정할 수 있습니다.');
				}else if(param.result == 'success'){
					$('#mre_form').parents().find('p').html($('#mre_content').val().replace(/</g,'&lt;')
																					.replace(/>/g,'&gt;')
																					.replace(/\r\n/g,'<br>')
																					.replace(/\r/g,'<br>')
																					.replace(/\n/g,'<br>'));
					//최근 수정일 처리
					$('#mre_form').parent().find('.modify-date').text('최근 수정일 : 5초미만');
					//수정폼 초기화
					initModifyForm();
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글은 수정할 수 없습니다.')
				}else{
					alert('댓글 수정 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생');
				
			}
		});
		
		//기본 이벤트 제거
		event.preventDefault();
		
	});
	
		
	
	
	
	/*============================
	 * 댓글 삭제
     *============================*/
	$(document).on('click','.delete-btn',function(){
		//댓글 번호
		let re_num = $(this).attr('data-num');
		//서버와 통신
		$.ajax({
			url:'deleteReply',
			type:'get',
			data:{re_num:re_num},//let re_num으로 명시한 변수입력
			dataType:'json',
			success:function(param){
				if(param.result == 'logout'){
					alert('로그인해야 삭제할 수 있습니다.');		
				}else if(param.result == 'success'){
					alert('삭제 완료!');
					selectList(1); //1페이지 재호출
				}else if(param.result == 'wrongAccess'){
					alert('타인의 글을 삭제할 수 없습니다.');
				}else{
					alert('댓글 삭제 오류 발생');
				}
			},
			error:function(){
				alert('네트워크 오류 발생!');
			}
		});
	});
	
	/*============================
	* 초기 데이터(목록) 호출
	*============================*/
	selectList(1);
	
});

















