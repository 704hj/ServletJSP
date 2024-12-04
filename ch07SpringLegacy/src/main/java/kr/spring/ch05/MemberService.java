package kr.spring.ch05;

public class MemberService {
	/*
	 * MemberService가 MemberDAO를 사용하기 위해 의존하는 것

		MemberService가 MemberDAO의 register() 메서드를 호출할 필요가 있기 때문에 의존한다고 표현
		이를 해결하기 위해, MemberDAO 객체를 생성자가 받아서 주입(Injection)받는 구조로 설계
	 * 
	 */
	
	private MemberDAO memberDAO;
	//의존 관계 설정 방식: 생성자        설정파일에서 의존관계 주입해줘야지 가장 아래 줄 memberDAO.register(); 호출 가능
	public MemberService(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	public void send() {
		System.out.println("MemberService의 send() 메서드 실행");
		memberDAO.register();
	}
}
