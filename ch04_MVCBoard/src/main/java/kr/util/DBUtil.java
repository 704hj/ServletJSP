package kr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBUtil {
	
	
	//Connection 객체를 생성해서 반환
	public static Connection getConnection()throws Exception{
		//context.xml에서 설정 정보를 읽어들여 커넥션풀로부터 커넥션을 할당 받음
		Context initCtx = new InitialContext();
		//lookup이 설정정보를 찾아서 DataSource담고 return으로 반환
													     //큰 범위 / 작은 범위
		DataSource ds = (DataSource)initCtx.lookup("java:comp/env/jdbc/xe"); 
		return ds.getConnection();
				
	}
	//자원정리
	public static void executeClose(ResultSet rs,
			                    PreparedStatement pstmt,
			                    Connection conn) {
		if(rs!=null)try {rs.close();}catch(SQLException e) {}
		if(pstmt!=null)try {pstmt.close();}catch(SQLException e) {}
		if(conn!=null)try {conn.close();}catch(SQLException e) {}
	}
}




