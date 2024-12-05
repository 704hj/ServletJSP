package kr.spring.ch17;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

//의존관계
public class SystemMonitor2 {
			   			//recorder 받아야함
	@Autowired 			//-> 매서드, 필드, 생성자에 명시 가능
						//@Qualifier를 이용한 자동설정 제한
	@Qualifier("main")
	private Recorder recorder;
							//private Recorder recorder에 @Autowired했는데 private이니까 아래에 주입받음
	public void setRecorder(Recorder recorder) {
		this.recorder = recorder;
	}

	@Override
	public String toString() {
		return "SystemMonitor2 [recorder=" + recorder + "]";
	}
	
	
}
