package kr.spring.ch15;

public class SystemMonitor {
	//PhoneCall과 의존관계를 맺을 클래스(PhoneCall을 받을 클래스)
	private PhoneCall call;

	public void setCall(PhoneCall call) {//PhoneCall타입이 맞으면 자동으로 매칭 되게끔 하려고 함 
		this.call = call;
	}

	@Override
	public String toString() {
		return "SystemMonitor [call=" + call + "]";
	}
	
	
	
}
