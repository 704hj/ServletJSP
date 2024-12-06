package kr.spring.mvc03.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import kr.spring.mvc03.service.ArticleService;
import kr.spring.mvc03.vo.NewArticleVO;

@Controller
public class NewArticleController {
	@Autowired //private니까 setter있어야 하는데 프로퍼티에 @Autowired명시함으로써 setter안해도 됨
	private ArticleService articleService;
	
	//GET요청이 들어올 때 호출
	@GetMapping("/article/newArticle.do")
	public String form() {
				//뷰 이름
		return "article/newArticleForm";
	}
	
	
	/*
	 * @ModelAttribute 어노테이션을 이용해서 전송된 데이터 자바빈에 담기
	 * [기능]
	 * 1. 자바빈(VO) 생성
	 * 2. 전송된 데이터를 자바빈(VO)에 저장
	 * 3. view에서 사용할 수 있도록 request에 자바빈(vo)저장
	 *    기존에는 request.setAttribute("article",vo) -> article.content이런 식으로 article이란 이름으로 vo를 담아서 EL태그로 작성
	 * 
	 * [형식]
	 * 1. @ModelAttribute(속성명) NewArticleVO vo
	 * 		지정한 속성명으로 jsp에서 request에 접근해서 자바빈(VO)호출 가능
	 * 		예) ${속성명.title}
	 * 
	 * 2. @ModelAttribute를 명시할 때 속성명을 생략할 수 있음
	 * 		속성명을 생략하면 클래스명의 첫글자를 소문자로 속성명을 자동 생성
	 * 		예) ModelAttribute NewArticleVO vo
	 *			${newArticleVO.title}
	 *
	 * 3. @ModelAttribute 생략 가능
	 * 		호출 메서드에 인자명만 명시
	 * 		예)NewArticleVO vo와 같이 인자명만 명시
	 * 		request에 저장되는 속성명  NewArticleVO로 저장됨
	 */
	
	//POST요청이 들어올 때 호출 
	
	@PostMapping("/article/newArticle.do")//@ModelAttribute NewArticleVO vo -> 자바빈 생성해서 vo에 넣어줌 파라미터 네임과 자바빈의 프로퍼티 명이 일치하면 데이터를 다 넣어줌
	public String submit(NewArticleVO vo) {
		
		articleService.writeArticle(vo);
				//뷰 이름
		return "article/newArticleSubmitted";
		
		
	}
	
	
}
