package kr.spring.ch21;

import org.aspectj.lang.ProceedingJoinPoint;

import com.itextpdf.text.log.SysoCounter;

public class MyFirstAdvice {
	/*
	 * 구현 가능한 Advice(언제 공통 기능을 핵심 기능에 적용할 지) 종류
	 * 종류						설명
	 * Before Advice			대상 객체의 메서드 호출 전에 공통 기능을 실행
	 * 
	 * AfterReturning Advice	대상 객체의 메서드가 예외 없이 실행한 이후에 공통기능을 실행 
	 * 
	 * AfterThrowing Advice 	대상 객체의 메서드를 실행하는 도중 예외가 발생한 경우에 공통 기능을 실행
	 * 
	 * After Advice				대상 객체의 메서드를 실행하는 도중 예외가 발생했는지의 여부와 상관 없이 
	 * 							메서드 실행 후 공통 기능을 실행
	 *							(try~catch~finally의 finally 블럭과 동작 방식 비슷)
	 *
	 * Around Advice			대상 객체의 메서드 실행 전, 후 또는 예외 발생 시점에 공통 기능을 실행하는데 사용
	 *
	 *  
	 */
	
	public void before() {
		//메서드 시작 직전에 동작하는 어드바이스
		System.out.println("Hello Beford! **메서드가 호출되기 전에 나온다!");
	}
								//설정파일에서 파라미터 값 입력해줘야함
	public void afterReturning(String msg) {
		//메서드 호출이 예외를 내보내지 않고 종료했을 때 동작하는 어드바이스
		System.out.println("Hello AfterReturning! **메서드가 호출한 후에 나온다! 전달된 객체 : " + msg);
	}
	
	public void afterThrowing(Exception e) {
		//메서드 호출이 예외를 던졌을 때 동작하는 어드바이스
		System.out.println("Hello AfterThrowing! **예외가 생기면 나온다! 예외 : " + e);
	}
	
	public void after() {
		//메서드 종료 후에 동작하는 어드바이스(예외가 발생해도 실행됨)
		System.out.println("Hello After! **메서드가 호출된 후에 나온다!");
	}
	
	public String around(ProceedingJoinPoint joinPoint)throws Throwable{
		//매서드 호출 전후에 동작하는 어드바이스
		System.out.println("Hello Around before! **메서드가 호출되기 전에 나온다");
		String s = null;
		//try~catch~finally 구조로 명시해야 예외가 발생해도 메서드 실행 후 공통 기능을 수행
		try {
			//핵심기능이 수행된 후 데이터 반환
			s = (String)joinPoint.proceed();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			System.out.println("Hello Around after! **메서드가 호출된 후에 나온다! 반환된 객체 : " + s);
		}
		
		return s;
		
	}
}








