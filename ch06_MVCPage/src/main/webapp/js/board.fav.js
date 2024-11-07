$(function(){
    /*
    좋아요 선택 여부와 선택한 총 개수를 읽기
     */


function selectFav(){
    //서버와 통신
    $.ajax({
        url:'getFav.do',
        type :'post',
        data:{board_num:$('#output_fav').attr('data-num')},
        dataType :'json',
        success:function(param){
             dispalyFav(param);
        },
        error:function(){
            alert('네트워크 오류발생');
        }
        
    });
}

function dispalyFav(param){
    let output;
    if(param.status == 'yesFav'){//좋아요 선택
        output = '../images/fav02.gif';
    }else{//좋아요 미선택
        output ='../images/fav01.gif';
    }
    //문서 객체에 설정
    $('#output_fav').attr('src',output);
    //$('#output_fcount').text(param.count);
    }
/*
좋아요 표시 함수
*/
    selectFav();

});