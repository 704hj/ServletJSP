package kr.spring.ch20;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//설정 파일
//(xml파일 만들지 않고)자바 코드 기반 설정
@Configuration
public class SpringConfig {
	
	//@Bean 어노테이션을 명시함으로써 bean 객체 설정
	@Bean
	public Worker worker() {
		return new Worker();
	}
	@Bean
	public Executor executor() {
		return new Executor();
	}
}
