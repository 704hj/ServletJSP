<%@ page language="java" contentType="text/plain; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ page import ="kr.util.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%
    //전송된 데이터 인코딩 처리
    request.setCharacterEncoding("utf-8");
    //전송된 데이터 반환
    String todo = request.getParameter("todo");
    Connection conn = null;
    PreparedStatement pstmt = null;
    String sql = null;

    try{
        //Connection 객체 생성
        conn = DBUtil.getConnection();
        //SQL문 작성
        sql = "INSERT INTO todo(id,todo) VALUES (todo_seq.nextval,?)";
        //PreparedStatement 객체 생성
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1,todo);
        //SQL문 실행
        pstmt.executeUpdate();

        %>
        {"result":"success"}
        <%

    }catch(Exception e){
        %>
         {"result":"failure"}
        <%
        e.printStackTrace();
    }finally{
        DBUtil.executeClose(null, pstmt, conn);
    }
%>
