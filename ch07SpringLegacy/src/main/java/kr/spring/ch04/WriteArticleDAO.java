package kr.spring.ch04;

public class WriteArticleDAO {//단독으로 가능하지만 의존관계(DI) 맺을 클래스 WriteArticleService를 생성한다
	public void insert() {
		System.out.println("WriteArticleDAO의 insert() 메서드 실행");
	}
}
