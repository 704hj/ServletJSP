package kr.spring.core;

public class Product {
	//핵심 기능 수행 -> 기존방법은 공통+핵심 기능을 함께 적용했는데 AOP방법은 핵심기능 / 공통기능 따로 설정
	public String launch() {
		System.out.println("launch() 메서드 출력");
		
		//예외 발생시 호출되는 공통 기능을 테스트하기 위해 
		//System.out.println(20/0);
		return "[상품 출시]";
	}
}
