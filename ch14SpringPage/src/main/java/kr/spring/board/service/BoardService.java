package kr.spring.board.service;

import java.util.List;
import java.util.Map;

import kr.spring.board.vo.BoardFavVO;
import kr.spring.board.vo.BoardReFavVO;
import kr.spring.board.vo.BoardReplyVO;
import kr.spring.board.vo.BoardVO;

public interface BoardService {
	//부모글
	public List<BoardVO> selectList(Map<String,Object> map);
	public int selectRowCount(Map<String,Object> map);
	public void insertBoard(BoardVO board);
	public BoardVO selectBoard(Long board_num);
	public void updateHit(Long board_num);
	public void updateBoard(BoardVO board);
	public void deleteBoard(Long board_num);
	public void deleteFile(Long board_num);
	
	//게시판 좋아요
	public BoardFavVO selectFav(BoardFavVO fav);
	public Integer selectFavCount(Long board_num);
	public void insertFav(BoardFavVO fav);
	public void deleteFav(BoardFavVO fav);
	
	//댓글
	public List<BoardReplyVO> selectListReply(Map<String,Object> map);
	public Integer selectRowContReply(Map<String,Object> map);
	public void insertReply(BoardReplyVO boardReply);
	public BoardReplyVO selectReply(Long re_num);
	public void updateReply(BoardReplyVO boardReply);
	public void deleteReply(Long re_num);
	//부모글 삭제시 댓글이 존재하면 부모글 삭제 전 댓글 삭제 -> Mapper에 public void deleteBoard(Long board_num) 명시했기 때문에 Mapper에서 호출만 하면 됨 
	//public void deleteReplyByBoardNum(Long board_num);
	
	//댓글 좋아요
	public BoardReFavVO selectReFav(BoardReFavVO fav);
	public Integer selectReFavCount(Long re_num);
	public void insertReFav(BoardReFavVO fav);
	public void deleteReFav(BoardReFavVO fav);
}
