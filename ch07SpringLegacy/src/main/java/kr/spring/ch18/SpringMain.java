package kr.spring.ch18;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {

	public static void main(String[] args) {
		//applicationContextAnnotation.xml 설정파일을 읽어들여 스프링 컨테이너를 생성
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContextAnnotation.xml");
		
		HomeController home = (HomeController)context.getBean("homeController");
		System.out.println(home);
		
		context.close();
	}

}
