package kr.spring.mvc05.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.spring.mvc05.service.SearchService;
import kr.spring.mvc05.vo.SearchVO;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

  
@Controller
public class GameSearchController {
    @Autowired
    private SearchService searchService;
    
    
    @GetMapping("/search/main.do")
    public String main() {
        return "search/main";
    }
    @GetMapping("/search/game.do")
    public ModelAndView search(SearchVO vo) {
        System.out.println("검색어 = " + vo.getQuery());
        String result = searchService.search(vo);
        
        ModelAndView mav = new ModelAndView();
        mav.setViewName("search/game");
        mav.addObject("searchResult", result);
        return mav;
    }
    
    
}