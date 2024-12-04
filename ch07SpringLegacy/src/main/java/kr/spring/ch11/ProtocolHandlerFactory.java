package kr.spring.ch11;

import java.util.Map;

public class ProtocolHandlerFactory {
	private Map<String,Object> map;

	//Object 타입으로 직접적인 값을 주입하지 못하기 때문에, setMap 메서드를 호출해서 Map을 설정해야 한다
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "ProtocolHandlerFactory [map=" + map + "]";
	}
	
	
	
}
