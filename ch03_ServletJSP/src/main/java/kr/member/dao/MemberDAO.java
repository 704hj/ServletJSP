package kr.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.member.vo.MemberVO;
import kr.util.DBUtil;

public class MemberDAO {
	/*
	 * 싱글턴 패턴은 생성자를 private으로 지정해서 외부에서 호출할 수 없도록 처리하고
	 * static 메서드를 호출해서 객체가 한 번만 생성되고 
	 * 생성된 객체를 공유할 수 있도록 처리하는 방식을 의미함 
	 */
	private static MemberDAO instance = new MemberDAO();
	
	public static MemberDAO getInstance() {
		return instance;
	}
	
	private MemberDAO() {}
	//회원가입
	public void insertMember(MemberVO member)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO smember (num,id,name,passwd,email,phone)"
					+ "VALUES (smember_seq.nextval,?,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1,member.getId());
			pstmt.setString(2,member.getName());
			pstmt.setString(3,member.getPasswd());
			pstmt.setString(4,member.getEmail());
			pstmt.setString(5,member.getPhone());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//회원상세정보
	public MemberVO getMember(long num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM smember WHERE num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, num);
			//SQL문 실행
			rs=pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setNum(rs.getLong("num"));
				member.setId(rs.getString("id"));
				member.setPasswd(rs.getString("passwd"));
				member.setName(rs.getString("name"));
				member.setEmail(rs.getString("email"));
				member.setPhone(rs.getString("phone"));
				member.setReg_date(rs.getDate("reg_date"));
				
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return member;
	}
	//아이디 중복 체크, 로그인 체크
	public MemberVO checkMember(String id)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberVO member = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM smember WHERE id=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				member = new MemberVO();
				member.setId(rs.getString("id"));
				member.setNum(rs.getInt("num"));
				member.setPasswd(rs.getString("Passwd"));
			}

        }catch(Exception e) {
            throw new Exception(e);
        }finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return member;
			
		}
	
	//회원정보수정->member는 usebean 에서 가져옴(노션)
	public void updateMember(MemberVO member)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE smember SET name=?,passwd=?,email=?,phone=? WHERE num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, member.getName());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getPhone());
			pstmt.setLong(5, member.getNum());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//회원탈퇴(회원정보 삭제)->인증후에 탈퇴가능 하게끔 설정하려함
	public void deleteMember(long num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토 커밋 해제 -> 오류가 발생하면 모든 작업을 취소
			conn.setAutoCommit(false);
			
			sql = "DELETE FROM sboard WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, num);
			pstmt.executeUpdate();
			
			sql = "DELETE FROM smember WHERE num=?";
			pstmt2 = conn.prepareStatement(sql);
			pstmt2.setLong(1, num);
			pstmt2.executeUpdate();
			
			//SQL문이 오류 없이 정상적으로 수행되면 커밋
			conn.commit();
			
		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 롤백
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}



























