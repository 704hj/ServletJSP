package kr.item.dao;

public class ItemDAO {
	//싱글턴 패턴
	private static ItemDAO instance = new ItemDAO();
	
	public static ItemDAO getInstance() {
		return instance;
	}
	private ItemDAO() {}
	
	//관리자 - 상품 등록
	//관리자 - 상품 수정
	//관리자 - 상품 삭제
	//관리자/사용자 - 전체 상품 개수/검색 상품 개수 
	//관리자/사용자 - 전체 상품 목록/검색 상품 목록
	//관리자/사용자 - 상품 상세
	
	
	
}
