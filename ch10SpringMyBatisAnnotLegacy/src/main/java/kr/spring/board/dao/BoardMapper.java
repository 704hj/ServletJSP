package kr.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.board.vo.BoardVO;

//맵퍼가 자동 스캔 되게 하려면? 이름이 같아야함. <mapper namespace="kr.spring.board.dao.BoardMapper">  BoardMapper, interface 이름, xml 파일 이름 세개가 같아야함.
//마이바티스가 자동으로 스캔해 BoardMapper를 클래스에 implements 해주고 클래스를 객체화 하지 않아도 됨
//맵퍼 인터페이스 이름, XML 파일 이름, XML의 namespace가 동일하면 마이바티스가 자동으로 XML을 스캔하여 인터페이스에 구현체를 연결하므로, 별도로 클래스를 객체화하거나 구현하지 않아도 사용
public interface BoardMapper {
	public void insertBoard(BoardVO board);
	@Select("SELECT COUNT(*) FROM aboard")
	public int selectBoardCount();
						//매서드 명이 id가 된다.
	public List<BoardVO> selectBoardList(Map<String,Object> map);
	@Select("SELECT * FROM aboard WHERE num=#{num}")
	public BoardVO selectBoard(int num);
	@Update("UPDATE aboard SET writer=#{writer},title=#{title},content=#{content} WHERE num=#{num}")
	public void updateBoard(BoardVO board);
	@Delete("DELETE FROM aboard WHERE num=#{num}")
	public void deleteBoard(int num); 

}
