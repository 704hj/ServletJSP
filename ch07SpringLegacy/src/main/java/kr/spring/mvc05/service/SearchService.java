package kr.spring.mvc05.service;

import kr.spring.mvc05.vo.SearchVO;

public class SearchService {
	public String search(SearchVO vo) {
		
		System.out.println(vo);
		
		return "검색 완료!!";
	}
}
