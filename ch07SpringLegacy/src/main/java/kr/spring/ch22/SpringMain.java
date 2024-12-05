package kr.spring.ch22;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import kr.spring.core.Product;

public class SpringMain {
	public static void main(String[] args) {
		//스프링 컨테이너 생성
			AbstractApplicationContext context = new ClassPathXmlApplicationContext("applicationContextAOPAnnot.xml");
			
			Product p = (Product)context.getBean("product");
			p.launch();
			context.close();
	}
	
}
