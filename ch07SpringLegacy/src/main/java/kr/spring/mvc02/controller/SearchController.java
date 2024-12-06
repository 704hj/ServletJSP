package kr.spring.mvc02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

//요청하면 동작하는 클래스임을 명시해야함
@Controller
public class SearchController {
	/*
	 * @RequestParam 어노테이션은 HTTP 요청 파라미터를 메서드의 파라미터로 전달 
	 * [형식]
	 * 1. @RequestParam (요청파라미터네임) 메서드의 인자(파라미터) 요청파라미터를 필수적으로 사용하지 않으면 오류 발생
	 *    @RequestParam(value="query", required=false)
	 *    required는 false로 지정하면 요청파라미터가 없어도 오류가 발생하지 않음
	 *    
	 * 	  숫자를 처리할 때 int pageNumber 대신 Integer pageNumber도 가능
	 * 	  Integer로 명시하면 요청파라미터가 없을 때 null처리 가능함
	 * 	  int로 명시하면 null을 숫자로 변환할 수 없기 때문에 @RequestParam(value="p",defaultValue="1") int pageNumber
	 * 	  
	 * 2. 요청 파라미터명과 호출메서드의 인자명이 같으면 요청파라미터명 생략 가능
	 * 	  @RequestParam String query (메서드의 인자명)
	 * 
	 * 3. @RequestParam 생략 가능
	 * 	  요청파라미터명과 호출메서드의 인자명을 동일하게 표기
	 * 	  요청파라미터를 필수적으로 사용하지 않아도 오류가 발생하지 않음
	 * 
	 * 
	 */
	
	
	
	//요청 URL과 실행 메서드 연결
	@RequestMapping("/search/internal.do")//@RequestParam(value="query") 반드시 index에서 값을 전달 해야함. required=false하면 가능
	public ModelAndView searchInternal(String query) {
		System.out.println("query = "+ query);
		
		ModelAndView mav = new ModelAndView();
		//뷰 이름 지정			폴더/파일
		mav.setViewName("search/internal");
		
		return mav;
		
	}
	
	//요청 URL과 실행 메서드 연결
	@RequestMapping("/search/external.do")//@RequestParam(value="p") 반드시 index에서 값을 전달 해야함.defaultValue="1"하면 가능->null일 경우에 1로 전달하겠다.
	public ModelAndView searchExternal(@RequestParam(value="p",defaultValue="1") int pageNumber) {
		System.out.println("p = " + pageNumber);
								//뷰 이름
		return new ModelAndView("search/external");
	}
	
	
}
