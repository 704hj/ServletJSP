package kr.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import kr.news.vo.NewsVO;
import kr.util.DBUtil;

public class NewsDAO {
	//싱글턴 패턴
	private static NewsDAO instance = new NewsDAO();
	
	public static NewsDAO getInstance() {
		return instance;
	}
	private NewsDAO() {}
	
	//뉴스 등록
	public void registerNews(NewsVO vo)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO dailynews (num,title,writer,passwd,email,article) VALUES (dailynews_seq.nextval,"
					+ "?,?,?,?,?)";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getWriter());
			pstmt.setString(3, vo.getPasswd());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getArticle());
			//SQL문 실행
			pstmt.executeUpdate();	
			
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}
	
	//뉴스 총개수
	public int getCount()throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		String sql = null;
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT COUNT(*) FROM dailynews";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//SQL문 실행
			rs=pstmt.executeQuery();
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
	
	//뉴스 목록
	public List<NewsVO> getList(int startRow,int endRow)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<NewsVO> list = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM (SELECT a.*, rownum rnum FROM (SELECT * FROM dailynews ORDER BY num DESC)a) WHERE rnum >= ? AND rnum <= ?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			//SQL문 실행
			rs=pstmt.executeQuery();
			list = new ArrayList<NewsVO>();
			
			while(rs.next()) {
				NewsVO newsVO = new NewsVO();
				
				newsVO.setNum(rs.getLong("num"));
				newsVO.setTitle(rs.getString("title"));
				newsVO.setWriter(rs.getString("writer"));
				newsVO.setReg_date(rs.getDate("reg_date"));
				//자바빈(VO)를 ArrayList에 저장
			    list.add(newsVO);
			}
		}catch(Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return list;
	}
	
	//뉴스 상세정보
	public NewsVO getNews(long num)throws Exception{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		NewsVO vo = null;
		String sql = null;
		
		try {
			//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM dailynews WHERE num=?";
			//PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setLong(1, num);
			//SQL문 실행
			rs=pstmt.executeQuery();
			if(rs.next()) {
				vo = new NewsVO();
				vo.setNum(rs.getLong("num"));
				vo.setTitle(rs.getString("title"));
				vo.setWriter(rs.getString("writer"));
				vo.setPasswd(rs.getString("passwd"));
				vo.setEmail(rs.getString("email"));
				vo.setArticle(rs.getString("article"));
				vo.setReg_date(rs.getDate("reg_date"));
				
			}
		}catch (Exception e) {
			throw new Exception(e);
		}finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}		
		return vo;
	}
	
	//뉴스 수정
	public void updateNews(NewsVO vo)throws Exception{
		 Connection conn = null;
	        PreparedStatement pstmt = null;
	        String sql = null;
	        try {
	            conn = DBUtil.getConnection();
	            sql = "UPDATE dailynews SET title=?,writer=?,email=?,article=? WHERE num=?";
	            //PreparedStatement 객체 생성
	            pstmt = conn.prepareStatement(sql);
	            //?에 데이터 바인딩
	            pstmt.setString(1, vo.getTitle());
	            pstmt.setString(2, vo.getWriter());
	            pstmt.setString(3, vo.getEmail());
	            pstmt.setString(4, vo.getArticle());
	            pstmt.setLong(5, vo.getNum());
	            
	           //SQL문 실행
				pstmt.executeUpdate();		

	        }catch(Exception e) {
	            throw new Exception(e);
	        }finally {
	            DBUtil.executeClose(null, pstmt, conn);
	        }
	    }
	
	//뉴스 삭제
	public void deleteNews(long num)throws Exception{
		Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        try {
        	//커넥션풀로부터 커넥션을 할당
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM dailynews WHERE num=?";
			//PreparedStatement 객체 생성
            pstmt = conn.prepareStatement(sql);
            //?에 데이터 바인딩
            pstmt.setLong(1,num);
            //SQL문 실행
			pstmt.executeUpdate();		
        }catch(Exception e) {
        	throw new Exception(e);
        }finally {
        	DBUtil.executeClose(null, pstmt, conn);
        	
        }
	}

}
