package kr.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class StringUtil {

	//JSON 문자열 처리
	//제네릭 표현의 ? : 제네릭 자료형을 특정 자료형으로 한정하지 않고 임의의 자료형으로 지정하고자 할 때 ?(와이드 카드) 사용
	public static String parseJSON(         //key,type
			HttpServletRequest request,Map<String,?> map)throws JsonGenerationException,JsonMappingException,IOException{
		//JSON 데이터 생성
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(map);
		
		request.setAttribute("ajaxData", ajaxData);
		//JSP 경로 반환		
		return "common/ajax_view.jsp";
	}
}
