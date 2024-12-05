package kr.spring.ch17;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {

	public static void main(String[] args) {
		//applicationContextAnnotation.xml 설정파일을 읽어들여 스프링 컨테이너를 생성
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContextAnnotation.xml");
		
		SystemMonitor2 monitor = (SystemMonitor2)context.getBean("systemMonitor");
		System.out.println(monitor);
		
		context.close();
		
	}

}
