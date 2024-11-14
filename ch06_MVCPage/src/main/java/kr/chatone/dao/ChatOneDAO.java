package kr.chatone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.chatone.vo.ChatOneVO;
import kr.util.DBUtil;

public class ChatOneDAO {
	//싱글턴 패턴
	private static ChatOneDAO instance = new ChatOneDAO();
	
	public static ChatOneDAO getInstance() {
		return instance;
	}
	private ChatOneDAO() {}
	
	//채팅 등록
	public void insertChat(ChatOneVO chat)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO zchatone (chat_num,send_num,"
				+ "recv_num,message) VALUES (zchatone_seq.nextval,"
				+ "?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, chat.getSend_num());
			pstmt.setLong(2, chat.getRecv_num());
			pstmt.setString(3, chat.getMessage());
			//SQL문 실행
			pstmt.executeUpdate();
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//채팅 읽기
	public List<ChatOneVO> getChatDetail(long send_num,
			                             long recv_num)
	                               throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		List<ChatOneVO> list = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//오토커밋 해제
			conn.setAutoCommit(false);
			
			//내가 보낸 메시지가 아닌 상대방이 보낸 메시지를 읽었을
			//때 read_check를 1에서 0으로 변경해야 하기때문에
			//send_num=recv_num AND recv_num=send_num 으로 셋팅
			sql = "UPDATE zchatone SET read_check=0 WHERE "
				+ "send_num=? AND recv_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, recv_num);
			pstmt.setLong(2, send_num);
			//SQL문 실행
			pstmt.executeUpdate();
			
			//내가 보낸 메시지뿐만 아니라 상대방이 보낸 메시지도 
			//읽어와야 하기때문에
			//(send_num=send_num AND recv_num=recv_num) OR
			//(send_num=recv_num AND recv_num=send_num)
			sql = "SELECT * FROM zchatone c JOIN zmember m "
				+ "ON c.send_num=m.mem_num WHERE "
				+ "(send_num=? AND recv_num=?) OR "
				+ "(send_num=? AND recv_num=?) "
				+ "ORDER BY chat_date ASC";
			//PreparedStatement 객체 생성
			pstmt2 = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt2.setLong(1, send_num);
			pstmt2.setLong(2, recv_num);
			pstmt2.setLong(3, recv_num);
			pstmt2.setLong(4, send_num);
			//SQL문 실행
			rs = pstmt2.executeQuery();
			list = new ArrayList<ChatOneVO>();
			while(rs.next()) {
				ChatOneVO chat = new ChatOneVO();
				chat.setChat_num(rs.getLong("chat_num"));
				chat.setSend_num(rs.getLong("send_num"));
				chat.setRecv_num(rs.getLong("recv_num"));
				chat.setMessage(rs.getString("message"));
				chat.setRead_check(rs.getInt("read_check"));
				chat.setChat_date(rs.getString("chat_date"));
				chat.setId(rs.getString("id"));
				
				list.add(chat);
			}
			//SQL문이 모두 성공하면 commit
			conn.commit();
		}catch(Exception e) {
			//SQL문이 하나라도 실패하면 rollback
			conn.rollback();
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, null);
			DBUtil.executeClose(rs, pstmt2, conn);
		}
		return list;
	}
	//전체 채팅 개수/검색 채팅 개수
	public int getChatCount(String keyfield,String keyword,
			                   long mem_num)throws Exception{
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
				//검색 처리
				if(keyfield.equals("1")) 
					sub_sql += "WHERE id LIKE '%' || ? || '%'";
				else if(keyfield.equals("2"))
					sub_sql += "WHERE message LIKE '%' || ? || '%'";
			}
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM zchatone JOIN (SELECT "
				+ " SUM(read_check) cnt, MAX(chat_num) chat_num, (SELECT "
				+ "id FROM zmember WHERE mem_num=send_num) id "
				+ "FROM zchatone WHERE send_num!=? "
				+ "GROUP BY send_num) USING(chat_num) " + sub_sql;
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, mem_num);
			if(keyword != null && !"".equals(keyword)) {
				pstmt.setString(2, keyword);
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
	//전체 채팅 목록/검색 목록
	public List<ChatOneVO> getChatList(int start, int end,
			               String keyfield, String keyword,long mem_num)
	                                  throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ChatOneVO> list = null;
		String sql = null;
		String sub_sql = "";
		int cnt = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			
			if(keyword != null && !"".equals(keyword)) {
				//검색 처리
				if(keyfield.equals("1")) 
					sub_sql += "WHERE id LIKE '%' || ? || '%'";
				else if(keyfield.equals("2"))
					sub_sql += "WHERE message LIKE '%' || ? || '%'";
			}
			
			//SQL문 작성
			sql = "select * from(select a.*,rownum rnum from"
					+ "(SELECT * FROM zchatone JOIN (SELECT "
				+ " SUM(read_check) cnt, MAX(chat_num) chat_num, (SELECT "
				+ "id FROM zmember WHERE mem_num=send_num) id "
				+ "FROM zchatone WHERE send_num!=? "
				+ "GROUP BY send_num) USING(chat_num) " + sub_sql
				+"order by chat_date desc,cnt desc)a) "
				+ "where rnum>=? and rnum<=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setLong(++cnt, mem_num);
			if(keyword!=null&&!"".equals(keyword)) {
				pstmt.setString(++cnt, keyword);
			}
			pstmt.setInt(++cnt, start);
			pstmt.setInt(++cnt, end);
			//sql문장 실행
			rs=pstmt.executeQuery();
			list=new ArrayList<ChatOneVO>();
			while(rs.next()) {
				ChatOneVO chat= new ChatOneVO();
				chat.setChat_num(rs.getLong("chat_num"));
				chat.setSend_num(rs.getLong("send_num"));
				chat.setMessage(rs.getString("message"));
				chat.setChat_date(rs.getString("chat_date"));
				chat.setId(rs.getString("id"));
				chat.setCnt(rs.getInt("cnt"));
				list.add(chat);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//읽지 않은 채팅 글 개수(MY페이지 표시)
	public int getUnreadCount(long recv_num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs =null;
		String sql = null;
		int count = 0;
		try {
			//커넥션풀로부터 커넥션 할당
			conn = DBUtil.getConnection();
			//sql문 작성
			sql = "SELECT SUM(read_check) FROM zchatone WHERE recv_num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1,recv_num);
			//SQL문 작성
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
	
	
}
































