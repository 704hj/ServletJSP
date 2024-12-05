package kr.spring.ch16;

import org.springframework.beans.factory.annotation.Autowired;

/*
 * @Autowired
 * 생성자, 필드, 메서드에 지정 가능
 * 메서드에 지정할 때는 setter뿐만 아니라 일반 메서드에도 적용가능
 * 
 *  @Autowired 어노테이션은 타입을 이용해서 자동적으로 프로퍼티 값을 설정하기 때문에, 해당 타입의 빈 긱체가 존재하지 않거나 또는 빈 객체가 
 *  두개 이상 존재할 경우 스프링은 @Autowired 어노테이션이 적용된 빈 긱체를 생성할 때 예외를 발생
 *  
 *  @Autowired(required=false)로 지정하면 해당 타입의 빈 객체가 존재하지 않더라도 스프링은 예외를 발생하지 않음
 *  
 *  기본값은 @Autowired(required=true)
 */
public class SystemMonitor {
	private long periodTime;
	private SmsSender sender;
	
								//주입받을 곳(숫자 타입)
	public void setPeriodTime(long periodTime) {
		this.periodTime = periodTime;
	}							
	@Autowired					//주입받을 곳(SmsSender)
	public void setSender(SmsSender sender) {
		this.sender = sender;
	}
	@Override
	public String toString() {
		return "SystemMonitor [periodTime=" + periodTime + ", sender=" + sender + "]";
	}
	
	//의존관계 맺어줘야함
	
	

}
