package kr.spring.ch08;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {

	public static void main(String[] args) {
		//applicationContext2.xml 설정파일을 읽어들여 스프링 컨테이너를 생성
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext2.xml");
		
		//DI 프로퍼티 설정방식 - 여러 개의 프로퍼티 
		//           컨테이너 id     (부모)                   (자동완성은 부모를 참조함. 자식으로 변경해 호출해줘야하고, 앞에 (부모)를 호출해 다운그레이드 해야함
		WorkController work = (WorkController)context.getBean("work");
		System.out.println(work);
		//어플리케이션 종료시 컨테이너에 존재하는 모든 빈(객체)를 종료
		context.close();
		
	}

}
