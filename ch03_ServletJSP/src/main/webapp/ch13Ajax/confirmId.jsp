<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import="kr.util.DBUtil"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.PreparedStatement"%>
<%@ page import="java.sql.ResultSet"%>

<%

    //전송된 데이터 인코딩 처리
    request.setCharacterEncoding("utf-8");
    //전성된 데이터 반환
    String id = request.getParameter("id");

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    String sql = null;

    try{

        //Connection 객체 생성
        conn = DBUtil.getConnection();
        //SQL문 작성
        sql = "SELECT id FROM people WHERE id=?";
        //PreparedStatement 객체 생성
        pstmt = conn.prepareStatement(sql);
        //?에 데이터 바인딩
        pstmt.setString(1,id);

        rs=pstmt.executeQuery();
        if(rs.next()){//아이디 중복
%>
            {"result":"idDuplicated"}
<%
        }else{//아이디 미중복

%>
        {"result":"idNotFound"}
<%
        }
    }catch(Exception e){
        e.printStackTrace();
%>
    {"result":"failure"}

<%
    }finally{
        DBUtil.executeClose(rs,pstmt,conn);
    }
%>