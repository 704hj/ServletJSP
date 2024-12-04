package kr.spring.ch04;

public class WriteArticleService {//WriteArticleDAO 꼭 필요함 의존관계
	private WriteArticleDAO writeArticleDAO;
	
	public WriteArticleService(WriteArticleDAO writeArticelDAO) {
		this.writeArticleDAO = writeArticelDAO;
	}
	
	public void write() {
		System.out.println("WriteArticleService의 write() 메서드 실행");
		writeArticleDAO.insert();
	}

}
