package kr.spring.board.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.spring.board.vo.BoardVO;

@Repository
public class BoardDAOImpl implements BoardDAO{
	@Autowired				  //sqlSession : MyBatis에서 SQL을 실행하고 결과를 처리하는 핵심 객체이며,  SQL 실행, 트랜잭션 관리, 커밋/롤백 등을 담당
	private SqlSessionTemplate sqlSession;

	/*
	 *	1. "insertBoard"는 MyBatis 매퍼(XML)에서 SQL을 식별하기 위한 id
       		매퍼 파일에서 id="insertBoard"를 가진 SQL 문을 찾는다
   		2. board 객체는 SQL 문에서 #{writer}, #{title}, #{content}와 매핑된다
      	   MyBatis는 board 객체의 프로퍼티 값을 가져와 SQL의 ? 자리에 바인딩함
    	3. 최종적으로 MyBatis는 SQL 문을 데이터베이스에 전달하여 실행한다.
	 */
	@Override
	public void insertBoard(BoardVO board) {
						//xml 파일에 명시한 id, 자바빈 객체를 넘겨줌으로써 맵핑되게끔	
		sqlSession.insert("insertBoard",board);
	}

	@Override
	public int selectBoardCount() {
		return sqlSession.selectOne("selectBoardCount");
	}

	@Override
	public List<BoardVO> selectBoardList(Map<String, Object> map) {
		return sqlSession.selectList("selectBoardList",map);
	}

	@Override
	public BoardVO selectBoard(int num) {
		return sqlSession.selectOne("selectBoard",num);
	}

	@Override
	public void updateBoard(BoardVO board) {
		sqlSession.update("updateBoard",board);
	}

	@Override
	public void deleteBoard(int num) {
		sqlSession.delete("deleteBoard",num);
		
	}
	

}
