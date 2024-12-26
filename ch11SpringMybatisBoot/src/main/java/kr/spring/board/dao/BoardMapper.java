package kr.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.spring.board.vo.BoardVO;

//레거시 할 때와 다르게 부트는 @Mapper 넣어줘야함
@Mapper
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
