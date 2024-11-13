package kr.chatone.vo;

public class ChatOneVO {
	private long char_num; //채팅번호
	private long send_num; //보낸 사람 회원번호
	private long recv_num; //받는 사람 회원번호
	private String message; //메시지
	private int read_check; //읽기 여부 1:읽지 않음, 0:읽음
	private String chat_date;//메시지 작성일
	
	private String id;
	private int cnt;
	
	
	public long getChar_num() {
		return char_num;
	}
	public void setChar_num(long char_num) {
		this.char_num = char_num;
	}
	public long getSend_num() {
		return send_num;
	}
	public void setSend_num(long send_num) {
		this.send_num = send_num;
	}
	public long getRecv_num() {
		return recv_num;
	}
	public void setRecv_num(long recv_num) {
		this.recv_num = recv_num;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String string) {
		this.message = string;
	}
	public int getRead_check() {
		return read_check;
	}
	public void setRead_check(int read_check) {
		this.read_check = read_check;
	}
	public String getChat_date() {
		return chat_date;
	}
	public void setChat_date(String chat_date) {
		this.chat_date = chat_date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
	
}
