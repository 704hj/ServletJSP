package kr.spring.member.vo;

import java.io.IOException;
import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"photo"})//용량이 너무 커서 출력시 로딩이 길어짐
public class MemberVO {
	private long mem_num;
	@Pattern(regexp="^[A-Za-z0-9]{4,14}$")
	private String id;
	private String nick_name;
	private int auth;
	private String auto;
	private String au_id;
	private String social_name;
	private String social_id;
	@NotBlank
	private String name;
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String passwd;
	@NotBlank
	private String phone;
	@NotBlank
	@Email
	private String email;
	@Size(min=5,max=5)
	private String zipcode;
	@NotBlank
	private String address1;
	@NotBlank
	private String address2;
	private byte[] photo;
	private String photo_name;
	private Date reg_date;
	private Date modify_date;
	//비밀번호 변경시에만 조건 체크
	@Pattern(regexp="^[A-Za-z0-9]+$")
	private String captcha_chars;
	//비밀번호 변경시 현재 비밀번호를 저장하는 용도로 사용
	@Pattern(regexp="^[A-Za-z0-9]{4,12}$")
	private String now_passwd;
	
	//비밀번호 일치 여부 체크
	public boolean isCheckedPasswore(String userPasswd) {
		if(auth > 1 && passwd.equals(userPasswd)) {
			return true;
		}
		return false;
	}
	//이미지 BLOB 처리 -> ??
	public void setUpload(MultipartFile upload) throws IOException {
		//MultipartFile->byte[]
		setPhoto(upload.getBytes());
		//파일 이름
		setPhoto_name(upload.getOriginalFilename());
	}
	
	
	
}

































