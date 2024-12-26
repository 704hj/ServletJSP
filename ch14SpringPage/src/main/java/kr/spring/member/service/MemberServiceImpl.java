package kr.spring.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.spring.member.dao.MemberMapper;
import kr.spring.member.vo.MemberVO;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Override //하나의 트렌젝션으로 묶음->이유 몬디
	public void insertMember(MemberVO member) {
		member.setMem_num(memberMapper.selectMem_num());
		memberMapper.insertMember(member);
		memberMapper.insertMember_detail(member);
	}

	@Override
	public MemberVO selectIdAndNickName(Map<String, String> map) {
		return memberMapper.selectIdAndNickName(map);
	}

	@Override
	public MemberVO selectCheckMember(String id) {
		return memberMapper.selectCheckMember(id);
	}
	
	@Override
	public MemberVO selectMember(Long mem_num) {
		return memberMapper.selectMember(mem_num);
	}

	@Override
	public void updateProfile(MemberVO member) {
		memberMapper.updateProfile(member);
		
	}

	@Override
	public void updateMember(MemberVO member) { 
		//트랜잭션으로 묶임
		memberMapper.updateMember(member);
		memberMapper.updateMember_detail(member);
	}

	@Override
	public void updatePassword(MemberVO member) {
		memberMapper.updatePassword(member);
		//설정되어 있는 자동로그인 기능 해제
		//모든 브라우저에 설정된 자동로그인 해제
		memberMapper.deleteRememberMe(member.getId());
		
	}

	@Override
	public int selectRowCount(Map<String, Object> map) {
		return memberMapper.selectRowCount(map);
	}

	@Override
	public List<MemberVO> selectList(Map<String, Object> map) {
		return memberMapper.selectList(map);
	}

	@Override
	public void updateByAdmin(MemberVO memberVO) {
		memberMapper.updateByAdmin(memberVO);
		
	}


}
