package kr.spring.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.member.vo.MemberVO;

@Mapper
public interface MemberMapper {
	//회원관리 - 일반회원
	@Select("SELECT spmember_seq.nextval FROM dual")
	public Long selectMem_num();
	@Insert("INSERT INTO spmember (mem_num,id,nick_name) VALUES (#{mem_num},#{id},#{nick_name})")
	public void insertMember(MemberVO member);//짧아서 annotation 사용
	public void insertMember_detail(MemberVO member);//길어서 xml에 명시함
	public MemberVO selectIdAndNickName(Map<String,String> map);
	public MemberVO selectCheckMember(String id);
	@Select("SELECT * FROM spmember JOIN spmember_detail USING (mem_num) WHERE mem_num=#{mem_num}")
	public MemberVO selectMember(Long mem_num);//명시후 MemberService명시 후 MemberServiceImpl까지 명시해줘야함
	
	@Update("UPDATE spmember SET nick_name=#{nick_name} WHERE mem_num=#{mem_num}")
	public void updateMember(MemberVO member);
	public void updateMember_detail(MemberVO member);
	@Update("UPDATE spmember_detail SET passwd=#{passwd} WHERE mem_num=#{mem_num}")
	public void updatePassword(MemberVO member);
	//자동 로그인 해제
	@Delete("DELETE FROM persistent_logins WHERE username=#{id}")
	public void deleteRememberMe(String id);
	
	
	//프로필 이미지 업데이트
	@Update("UPDATE spmember_detail SET photo=#{photo},photo_name=#{photo_name} WHERE mem_num=#{mem_num}")
	public void updateProfile(MemberVO member); //memberservice에 명시해야함
	
	//회원관리 - 관리자(작성 후 membermapper.xml로 이동 -> xml작성후 ㅡmemberservice로 가서 아래 두 줄 복붙-> servicelpml가서 작성 후 maincontroller로 이동
	public int selectRowCount(Map<String,Object> map);
	public List<MemberVO> selectList(Map<String,Object> map);
	
	//회원권한 수정
	@Update("UPDATE spmember SET auth=#{auth} WHERE mem_num=#{mem_num}")
	public void updateByAdmin(MemberVO memberVO);
	

}
