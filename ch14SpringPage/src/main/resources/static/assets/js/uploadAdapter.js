class UploadAdapter {
    constructor(loader) {
        this.loader = loader;
    }

    upload() {
        return this.loader.file.then( file => new Promise(((resolve, reject) => {
            this._initRequest();
            this._initListeners( resolve, reject, file );
            this._sendRequest( file );
        })))
    }

    _initRequest() {
        const xhr = this.xhr = new XMLHttpRequest();
        //컨텍스 경로가 /springPage 형식일 때
        //xhr.open('POST', location.protocol + '//' + location.host + location.href.substring(location.href.indexOf(location.host)+location.host.length,location.href.indexOf('/',location.href.indexOf(location.host)+location.host.length+1)) + '/common/imageUploader', true);
        //컨텍스 경로가 / 형식일 때
        xhr.open('POST', location.protocol + '//' + location.host + '/common/imageUploader', true);
        xhr.responseType = 'json';
    }

    _initListeners(resolve, reject, file) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = '파일을 업로드 할 수 없습니다.'

        xhr.addEventListener('error', () => {reject(genericErrorText)})
        xhr.addEventListener('abort', () => reject())
        xhr.addEventListener('load', () => {
            const response = xhr.response
            //---------로그아웃 메시지 처리---------//
            if(!response.uploaded){
				alert('로그아웃 되어서 표시된 이미지는 서버에 저장되지 않았습니다.로그인 후 다시 작성하세요');
			}
            //---------로그아웃 메시지 처리---------//
            if(!response || response.error) {
                return reject( response && response.error ? response.error.message : genericErrorText );
            }

            resolve({
                default: response.url //업로드된 파일 주소
            })
        })
    }

    _sendRequest(file) {
        const data = new FormData()
        data.append('upload',file)
        this.xhr.send(data)
    }
}