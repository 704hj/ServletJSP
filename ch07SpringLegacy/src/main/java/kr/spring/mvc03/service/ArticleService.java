package kr.spring.mvc03.service;


import kr.spring.mvc03.vo.NewArticleVO;


public class ArticleService {
	public void writeArticle(NewArticleVO vo) {
		System.out.println("신규 게시글 등록 : " + vo);
	}
}
