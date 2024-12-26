/*======================================================================
 * customjs.js는 기본적으로 jquery 라이브러리를 요구함
 * =====================================================================*/

/*===================================================================
 * 업로드 이미지 미리보기
 * 파일 업로드 태그의 선택자,업로드되는 이미지가 보여지는 태그 선택자,업로드 허용 확장자,업로드 허용 파일 사이즈
 * =================================================================*/
function customViewImage(fileInput,imgSrc,fileTypes,fileSize){
	$(fileInput).attr('data-path',$(imgSrc).attr('src'));//처음 화면에 보여지는 이미지 읽기
	$(fileInput).change(function(){
		let my_photo = this.files[0];
		if(!my_photo){
			customCancelImage(this,imgSrc);
			return;
		}
		
		if(fileTypes.indexOf(my_photo.name.substring(my_photo.name.lastIndexOf(".")+1, my_photo.name.length).toLowerCase()) < 0){
			alert('파일의 확장자는 '+fileTypes+"만 허용합니다.");
			customCancelImage(this,imgSrc);
		    return;
		}
		
		if(my_photo.size > fileSize){
			alert(my_photo.size + 'bytes('+fileSize+'bytes까지만 업로드 가능)');
			customCancelImage(this,imgSrc);
			return;
		}
		
		const reader = new FileReader();
		reader.readAsDataURL(my_photo);
		
		reader.onload=function(){
			$(imgSrc).attr('src',reader.result);
		};
	});//end of change
}
//이미보기 취소시 사용
function customCancelImage(fileInput,imgSrc){
	$(imgSrc).attr('src',$(fileInput).attr('data-path'));
	$(fileInput).val('');
}
//파일 업로드 후 정보 초기화(파일을 ajax로 업로드할 때 화면 갱신을 위해 사용)
function customInitImage(fileInput,imgSrc){
	$(fileInput).attr('data-path',$(imgSrc).attr('src'));
	$(fileInput).val('');
}
