package kr.spring.ch19;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.itextpdf.text.log.SysoCounter;

public class SpringMain {

	public static void main(String[] args) {
		//applicationContextAnnotation.xml 설정파일을 읽어들여 스프링 컨테이너를 생성
				AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContextScan.xml");
																//앞글자를 소문자로
		HomeController home = (HomeController)context.getBean("homeController");
		System.out.println(home);
		
		context.close();
		
	}

}
