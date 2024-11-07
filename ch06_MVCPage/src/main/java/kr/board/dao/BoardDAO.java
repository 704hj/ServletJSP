package kr.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardFavVO;
import kr.board.vo.BoardVO;
import kr.util.DBUtil;
import kr.util.StringUtil;

public class BoardDAO {
	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	private BoardDAO() {}
	
	
	
	//글 등록
	public void insertBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO zboard (board_num,title,content,filename,ip,mem_num) VALUES (zboard_seq.nextval,?,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getFilename());
			pstmt.setString(4, board.getIp());
			pstmt.setLong(5, board.getMem_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//전체 글 개수/검색 글 개수
	public int getBoardCount(String keyfield,String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String sub_sql = "";
		int count = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "WHERE title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE id LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE content LIKE '%' || ? || '%'";
			}
			
			sql = "SELECT COUNT(*) FROM zboard JOIN zmember USING(mem_num) "+ sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			if(keyword !=null && !"".equals(keyword)) {
				pstmt.setString(1, keyword);
			}
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
				
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return count;
	}
	
	
	//전체 글 목록/검색 글 목록
	public List<BoardVO> getListBoard(int start, int end, String keyfield, String keyword)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색처리
				if(keyfield.equals("1")) sub_sql += "WHERE title LIKE '%' || ? || '%'";
				else if(keyfield.equals("2")) sub_sql += "WHERE id LIKE '%' || ? || '%'";
				else if(keyfield.equals("3")) sub_sql += "WHERE content LIKE '%' || ? || '%'";
			}
			
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM zboard JOIN zmember USING(mem_num) " 
			+ sub_sql + " ORDER BY board_num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO board = new BoardVO();
				board.setBoard_num(rs.getLong("board_num"));
				//HTML 태그를 허용하지 않음
				board.setTitle(StringUtil.useNoHtml(rs.getString("title")));
				board.setHit(rs.getInt("hit"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setId(rs.getString("id"));
				
				list.add(board);
			}
			
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//글 상세->한건의 레코드를 불러오려고 함
	public BoardVO getBoard(long board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO board = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM zboard JOIN zmember USING(mem_num) LEFT OUTER JOIN zmember_detail USING(mem_num) WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			//SQL문 실행
			rs = pstmt.executeQuery();
			if(rs.next()) {
                board = new BoardVO();
                board.setBoard_num(rs.getLong("board_num"));
                board.setTitle(rs.getString("title"));
                board.setContent(rs.getString("content"));
                board.setHit(rs.getInt("hit"));
                board.setReg_date(rs.getDate("reg_date"));
                board.setModify_date(rs.getDate("modify_date"));
                board.setFilename(rs.getString("filename"));
                board.setMem_num(rs.getInt("mem_num"));
                board.setId(rs.getString("id"));
                board.setPhoto(rs.getString("photo"));

            }
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		
		return board;
	}
	//조회수 증가
	public void updateReadcount(long board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE zboard SET hit=hit+1 WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
			
		}
	}
	
	//파일 삭제
	public void deletefile(long board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE zboard SET filename='' WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, board_num);
			//SQL문 실행
			pstmt.executeUpdate();
		
	}catch(Exception e) {
		throw new Exception(e);
	}finally {
		DBUtil.executeClose(null, pstmt, conn);
	}
	}


	//글 수정
	public void updateBoard(BoardVO board)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			
			if(board.getFilename()!=null && !"".equals(board.getFilename())) {
				sub_sql += ",filename=?";
			}
			sql = "UPDATE zboard SET title=?,content=?,modify_date=SYSDATE,ip=?" + sub_sql+ " WHERE board_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, board.getTitle());
			pstmt.setString(++cnt, board.getContent());
			pstmt.setString(++cnt, board.getIp());
			if(board.getFilename()!=null && !"".equals(board.getFilename())) {
				pstmt.setString(++cnt, board.getFilename());
			}
			pstmt.setLong(++cnt, board.getBoard_num());
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		}
	
	
	
	
	//글 삭제
	public void deleteBoard(long board_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			
			//좋아요 삭제
			//댓글 삭제
			//부모글 삭제
			sql = "DELETE FROM zboard WHERE board_num=?";
			pstmt3 = conn.prepareStatement(sql);
			pstmt3.setLong(1, board_num);
			pstmt3.executeUpdate();
			
			//예외 발생 없이 정상적으로 모든 SQL문이 실행
			conn.commit();
			
		}catch(Exception e) {
			//SQL문이 하나라도 예외가 발생하면 롤백 처리
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt3, conn);
			DBUtil.executeClose(null, pstmt2, conn);
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//좋아요 개수
	//회원번호와 게시물 번호를 이용한 좋아요 정보
	//(회원이 게시물을 호출했을 때 좋아요 선택 여부 표시)
	public BoardFavVO selectFav(BoardFavVO favVO)throws Exception{
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardFavVO fav = null;		
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM zboard_fav WHERE board_num=? AND mem_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, favVO.getBoard_num());
			pstmt.setLong(2, favVO.getMem_num());
			//SQL문 실행
			rs = pstmt.executeQuery();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		
		return fav;
	}
	//좋아요 등록
	public void insertFav(BoardFavVO favVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO zboard_fav (board_num,mem_num) VALUES (?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, favVO.getBoard_num());
			pstmt.setLong(2, favVO.getMem_num());
			//SQL문 실행
			pstmt.executeQuery();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}

	}
	
	//좋아요 삭제
	
	//좋아요 목록(=찜 목록)
	
	//내가 선택한 좋아요 목록
	
	
	
	

	}

























