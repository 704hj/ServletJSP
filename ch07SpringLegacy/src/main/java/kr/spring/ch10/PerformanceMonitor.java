package kr.spring.ch10;

import java.util.List;

//콜렉션 타입 프로퍼티 설정(문서 2-6))
public class PerformanceMonitor {
	private List<Double> deviations;

	public void setDeviations(List<Double> deviations) {
		this.deviations = deviations;
	}

	@Override
	public String toString() {
		return "PerformanceMonitor [deviations=" + deviations + "]";
	}
	
	
	
	
}
