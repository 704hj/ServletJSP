package kr.spring.board.vo;

import java.sql.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BoardVO {
	private long board_num; 		//게시판 번호
	private String title; 			//제목
	private String category;		//카테고리
	private String content;			//내용
	private int hit;				//조회수
	private Date reg_date;			//등록일
	private Date modify_date;		//수정일
	private MultipartFile upload;	//파일
	private String filename;		//파일명
	private String ip;				//ip주소
	private long mem_num;			//회원번호
	
	private String id;				//아이디
	private String nick_name;		//별명
	
	private int re_cnt;				//댓글 개수
	private int fav_cnt;			//좋아요 개수
	
	//문자열 체크해서 1번 자바 2번 자바스크립트  ?
	public String getCategoryName() {
		String name;
		switch (category) {
		case "1": name = "자바";break;
		case "2": name = "데이터베이스";break;
		case "3": name = "자바스크립트";break;
		case "4": name = "기타";break;
		default : name = "분류오류";
		}
		return name;
	}

}
