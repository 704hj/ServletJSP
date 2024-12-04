package kr.spring.ch07;

public class RegisterService {//자바빈 설정과 getter, setter만 없는 동일한 구조
	private RegisterDAO registerDAO;
	 
	//의존 관계 설정 방식 : 프로퍼티 (registerDAO 이건데 private니까 외부에서 직접적으로 access 못 함-> public한 setter를 만들어줘야함)
			//public void setRegisteraDAO(RegisterDAO registerDAO) {
			//this.registerDAO = registerDAO; -> 주석처리 이유 : 매번 직접 쓸 필요 없음
			//source-> getter, setter로 생성
	public void setRegisterDAO(RegisterDAO registerDAO) {//applicationContext2.xml 파일내에서 <property name="registerDAO">의 name : 의존 객체를 주입할 때 사용할 설정 메서드 (setter)의 프로퍼티 이름
		this.registerDAO = registerDAO;
	}
	
	public void write() {
		System.out.println("RegisterService의 write() 메서드 실행");
		registerDAO.insert();
	}
	
}
