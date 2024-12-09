package kr.spring.mvc07.vo;

import javax.validation.constraints.NotBlank;

public class LoginVO {
	/*
	 * 아래로 내려갈수록 허용 범위 증가
	 * @NotNull : null만 허용하지 않음
	 * @NotEmpty : null과 ""(빈문자열)을 허용하지 않음
	 * @NotBlank : null, ""(빈문자열), " "(공백)을 허용하지 않음
	 */
	
	//공백을 인정하지 않는다. 
	//@NotBlank(message = "아이디를 입력하세요") 자바스크립트에서의 유효성 체크 방법이 있고 지금 하는거는 서버 자체 내 에서의 유효성 체크
	@NotBlank
	private String userId;
	//@NotBlank(message = "비밀번호를 입력하세요")
	@NotBlank
	private String password;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Override
	public String toString() {
		return "LoginVO [userId=" + userId + ", password=" + password + "]";
	}
	
	
}
