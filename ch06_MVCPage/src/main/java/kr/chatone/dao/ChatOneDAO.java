package kr.chatone.dao;

public class ChatOneDAO {
	//싱글턴 패턴
	private static ChatOneDAO instance = new ChatOneDAO();
	
	public static ChatOneDAO getInstance() {
		return instance;
	}
	private ChatOneDAO() {}
	
	//채팅등록
	//채팅 읽기
	//전체 채팅 개수/검색 채팅 개수
	//전체 채팅 목록/검색 목록
	

}
