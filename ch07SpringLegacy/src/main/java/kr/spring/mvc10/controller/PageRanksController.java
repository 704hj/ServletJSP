package kr.spring.mvc10.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.mvc10.vo.PageRank;

@Controller
public class PageRanksController {
	//요청 url 입력
	@GetMapping("/pageRanksExcel.do")
	public ModelAndView handleRequestInternal() {
		List<PageRank> pageRanks = new ArrayList<PageRank>();
		pageRanks.add(new PageRank(1,"/board/list.do"));
		pageRanks.add(new PageRank(2,"/member/login.do"));
		pageRanks.add(new PageRank(3,"/cart/list.do"));
								//뷰 이름,  		 속성 명,  	속성 값
		return new ModelAndView("pageRanks", "pageRanks", pageRanks);
	}
	
	@GetMapping("/pageJsonReport.do")
	
	@ResponseBody
	
	/* 자동으로 JSON형식으로 변환해줌
	 * 메서드의 반환값(List<PageRank>)을 HTTP 응답의 본문(body)으로 직접 전달.
	 * 일반적인 경우, 컨트롤러 메서드는 View(HTML 파일) 이름을 반환하거나 페이지를 렌더링하지만
	 * @ResponseBody를 사용하면 데이터를 직접 반환한다. 반환 데이터는 Spring에서 자동으로 JSON 형식으로 변환.
	 */
	public List<PageRank> jsonReport(){
		List<PageRank> pageRanks = new ArrayList<PageRank>();
		pageRanks.add(new PageRank(1,"/file.do"));
		pageRanks.add(new PageRank(2,"/pageRanksExcel.do"));
		pageRanks.add(new PageRank(3,"/pageJson.do"));
		
		
		return pageRanks;
	}

}
