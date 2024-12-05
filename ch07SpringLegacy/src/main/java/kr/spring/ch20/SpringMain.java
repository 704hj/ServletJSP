package kr.spring.ch20;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class SpringMain {

	public static void main(String[] args) {
		//SpringConfig 클래스(xml설정이 아니라 자바코드 기반 설정)를 읽어들여 스프링 컨테이너 생성
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
		
		//자바코드 기반 설정으로 생성된 객체 호출
		Executor executor = (Executor)context.getBean("executor");
		executor.addUnit();
		context.close();
		
	}

}
