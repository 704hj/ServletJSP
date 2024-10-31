package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	//싱글턴 패턴
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	private MemberDAO() {}
	
	//회원가입
	public void insertMember(MemberVO member)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		ResultSet rs = null;
		String sql = null;
		long num = 0; //시퀀스 번호 저장
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//오토 커밋 해제
			conn.setAutoCommit(false);
			
			//회원번호(mem_num) 생성 , zmember_seq.nextval : 컬럼명 너무 길다 -> 컬럼인덱스(1)로 대체
			sql = "SELECT zmember_seq.nextval FROM dual";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {//컬럼인덱스
				num = rs.getLong(1);
			}
			
			sql = "INSERT INTO zmember (mem_num,id) VALUES (?,?)";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, num);//시퀀스
			pstmt2.setString(2, member.getId());//id
			pstmt2.executeUpdate();
			
			sql= "INSERT INTO zmember_detail (mem_num,name,passwd,phone,email,zipcode,address1,address2) VALUES (?,?,?,?,?,?,?,?)";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(1, num);//시퀀스
			pstmt3.setString(2, member.getName());
			pstmt3.setString(3, member.getPasswd());
			pstmt3.setString(4, member.getPhone());
			pstmt3.setString(5, member.getEmail());
			pstmt3.setString(6, member.getZipcode());
			pstmt3.setString(7, member.getAddress1());
			pstmt3.setString(8, member.getAddress2());
			pstmt3.executeUpdate();
			
			//SQL문 실행시 모두 성공하면 commit
			conn.commit();
			
		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
	}
	//ID 중복 체크 및 로그인 처리
	public MemberVO checkMember(String id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "SELECT * FROM zmember LEFT OUTER JOIN zmember_detail USING(mem_num) WHERE id=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, id);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setMem_num(rs.getLong("mem_num"));
				member.setId(rs.getString("id"));
				member.setAuth(rs.getInt("auth"));
				member.setPasswd(rs.getString("passwd"));
				member.setPhoto(rs.getString("photo"));
				//회원 탈퇴시 필요
				member.setEmail(rs.getString("email"));
			}
					
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			
		}DBUtil.executeClose(rs, pstmt, conn);
		
		return member;
	}
	//회원 상세 정보
	//회원 정보 수정
	//비밀번호 수정
	//프로필 사진 수정
	//회원 탈퇴(회원 정보 삭제)
	
	//관리자
	//전체 내용 개수, 검색 내용 개수
	//목록,검색 목록
	

}
