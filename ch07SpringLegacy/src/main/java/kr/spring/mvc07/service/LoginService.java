package kr.spring.mvc07.service;

import kr.spring.mvc07.vo.LoginVO;

//로그인에 실패하는 경우를 error라고 인식하는 방법
public class LoginService {
	public void checkLogin(LoginVO loginVO) throws LoginCheckException{
		
		//테스트용으로 아이디와 비밀번호가 일치하면 로그인 성공
		if(!loginVO.getUserId().equals(loginVO.getPassword())) {
			//로그인에 실패했을 경우
			System.out.println("인증 에러 -" + loginVO.getUserId());
			throw new LoginCheckException();
		}
	}
}
