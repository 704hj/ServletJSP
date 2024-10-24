package kr.board.dao;
//write.jsp로 전송하려고 함
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.board.vo.BoardVO;
import kr.util.DBUtil;

public class BoardDAO {
	//싱글턴 패턴
	private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	private BoardDAO() {}
	
	//글 저장
	public void insert(BoardVO BoardVO)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO sboard (board_num,title,content,ip,num) VALUES (sboard_seq.nextval,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, BoardVO.getTitle());
			pstmt.setString(2, BoardVO.getContent());
			pstmt.setString(3, BoardVO.getIp());
			pstmt.setLong(4, BoardVO.getNum());
			//SQL문 실행
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//글의 총개수
	//글 목록 
	//[]배열에 저장해서 반환/ 자바빈에 여러개의 레코드 보관 -> return하려고 함-> 여러개 중 1개만 반환 가능-> 여러개를 하나로 묶어줘야해->arrayList로 묶어서 하나로 반환
	public List<BoardVO> getList(int startRow, int endRow)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성 -> 회원번호로 조인/ USING(num) "<- 공백 필요
			sql = "SELECT * FROM sboard JOIN smember USING(num) "
					+ "ORDER BY board_num DESC";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//SQL문 실행
			rs = pstmt.executeQuery();
			list = new ArrayList<BoardVO>();
			while(rs.next()) {
				BoardVO boardVO = new BoardVO();
				boardVO.setBoard_num(rs.getLong("board_num"));
				boardVO.setTitle(rs.getString("title"));
				boardVO.setId(rs.getString("id"));
				boardVO.setReg_date(rs.getDate("reg_date"));
				//자바빈을 ArrayList에 저장
				list.add(boardVO);
				
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			 DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	//글 상세
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
			sql = "SELECT * FROM sboard JOIN smember USING(num) "
					+ "WHERE board_num=?";
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
				board.setIp(rs.getString("ip"));
				board.setReg_date(rs.getDate("reg_date"));
				board.setNum(rs.getLong("num"));
				board.setId(rs.getString("id"));
			}
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return board;
	}
	//글 수정
	//글 삭제
	
}
