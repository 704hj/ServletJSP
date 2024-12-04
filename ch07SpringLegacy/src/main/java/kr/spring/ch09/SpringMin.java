package kr.spring.ch09;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMin {

	public static void main(String[] args) {
		//applicationContext2.xml 설정파일을 읽어들여 스프링 컨테이너를 생성
				AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContext2.xml");
		
		UploadController upload = (UploadController)context.getBean("upload");
		System.out.println(upload);
		
		//어플리케이션 종료시 컨테이너에 존재하는 모든 빈(객체)를 종료
		context.close();
		
	}

}
