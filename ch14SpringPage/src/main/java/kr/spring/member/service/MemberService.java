package kr.spring.member.service;

import java.util.List;
import java.util.Map;
import kr.spring.member.vo.MemberVO;

public interface MemberService {
	public void insertMember(MemberVO member);//하나의 트렌젝션으로 묶기 위해서 selectMem_num, insertMember_detail매서드 지우고 insertMember에 포함시킴
	public MemberVO selectIdAndNickName(Map<String,String> map);
	public MemberVO selectCheckMember(String id);
	public MemberVO selectMember(Long mem_num);
	public void updateProfile(MemberVO member);//->저장하면 오류 남 serviceImpl가서 add 해줘야함
	public void updateMember(MemberVO member);
	public void updatePassword(MemberVO member);
	public int selectRowCount(Map<String,Object> map);
	public List<MemberVO> selectList(Map<String,Object> map);
	public void updateByAdmin(MemberVO memberVO);
}
