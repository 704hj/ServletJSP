package kr.board.vo;

public class BoardReplyVO {
	private long re_num;
	private String re_content;
	//요새 트렌드 댓글 쓴 지 몇 분 전 이런식의 문자열로 사용 -> String
	private String re_date;
	private String re_modify;
	private String re_ip;
	private long board_num;
	private long mem_num;
	
	private String id;

	public long getRe_num() {
		return re_num;
	}

	public void setRe_num(long re_num) {
		this.re_num = re_num;
	}

	public String getRe_content() {
		return re_content;
	}

	public void setRe_content(String re_content) {
		this.re_content = re_content;
	}

	public String getRe_date() {
		return re_date;
	}

	public void setRe_date(String re_date) {
		this.re_date = re_date;
	}

	public String getRe_modify() {
		return re_modify;
	}

	public void setRe_modify(String re_modify) {
		this.re_modify = re_modify;
	}

	public String getRe_ip() {
		return re_ip;
	}

	public void setRe_ip(String re_ip) {
		this.re_ip = re_ip;
	}

	public long getBoard_num() {
		return board_num;
	}

	public void setBoard_num(long board_num) {
		this.board_num = board_num;
	}

	public long getMem_num() {
		return mem_num;
	}

	public void setMem_num(long mem_num) {
		this.mem_num = mem_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
