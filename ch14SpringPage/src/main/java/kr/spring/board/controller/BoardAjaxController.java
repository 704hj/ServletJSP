package kr.spring.board.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardFavVO;
import kr.spring.board.vo.BoardReplyVO;
import kr.spring.board.vo.BoardVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardAjaxController {
	@Autowired
	private BoardService boardService;
	
	/*===================
	 * 부모글 업로드 파일 삭제
	 *===================*/
	@GetMapping("/deleteFile")
	@ResponseBody
	public Map<String,String> processFile(long board_num,
			              @AuthenticationPrincipal
			              PrincipalDetails principal,
			              HttpServletRequest request){
		Map<String,String> mapJson = 
				new HashMap<String,String>();
		try {
			//로그인 정보
			MemberVO user = principal.getMemberVO();
			BoardVO db_board = boardService.selectBoard(board_num);
			//로그인한 회원번호와 작성자 회원번호 일치 여부 체ㅡ
			if(user.getMem_num() != db_board.getMem_num()) {
				//불일치
				mapJson.put("result", "wrongAccess");
			}else {
				//일치
				boardService.deleteFile(board_num);
				FileUtil.removeFile(request, db_board.getFilename());
				mapJson.put("result", "success");
			}
			
		}catch(NullPointerException e) {
			mapJson.put("result", "logout");
		}		
		return mapJson;
	}
	/*===================
	 * 부모글 좋아요
	 *===================*/	
	//부모글 좋아요 읽기
	@GetMapping("/getFav")
	@ResponseBody
	public Map<String,Object> getFav(
			              BoardFavVO fav,
			              @AuthenticationPrincipal
			              PrincipalDetails principal){
		
		log.debug("<<게시판 좋아요 - BoardFavVO>> : " + fav);
		
		Map<String,Object> mapJson = 
				new HashMap<String,Object>();
		try {
			//로그인된 회원번호 셋팅
			fav.setMem_num(principal.getMemberVO().getMem_num());
			BoardFavVO boardFav = boardService.selectFav(fav);
			
			if(boardFav!=null) {
				mapJson.put("status", "yesFav");
			}else {
				mapJson.put("status", "noFav");
			}			
		}catch(NullPointerException e) {
			mapJson.put("status", "noFav");
		}
		mapJson.put("count", 
				boardService.selectFavCount(
						    fav.getBoard_num()));		
		return mapJson;
	}
	//부모글 좋아요 등록/삭제
	@PostMapping("/writeFav")
	@ResponseBody
	public Map<String,Object> writeFav(
			        BoardFavVO fav,
			        @AuthenticationPrincipal
			        PrincipalDetails principal){
		log.debug("<<게시판 좋아요 - 등록/삭제>> : " + fav);
		
		Map<String,Object> mapJson = 
				new HashMap<String,Object>();
		try {
			//로그인된 회원번호 셋팅
			fav.setMem_num(principal.getMemberVO().getMem_num());
			log.debug("<<부모글 좋아요 등록/삭제>> : " + fav);
			
			BoardFavVO boardFav = boardService.selectFav(fav);
			if(boardFav!=null) {
				boardService.deleteFav(fav);
				mapJson.put("status", "noFav");
			}else {
				boardService.insertFav(fav);
				mapJson.put("status", "yesFav");
			}
			mapJson.put("result", "success");
			mapJson.put("count", 
					boardService.selectFavCount(
							         fav.getBoard_num()));
		}catch(NullPointerException e) {
			mapJson.put("result", "logout");
		}		
		return mapJson;
	}
	/*===================
	 * 댓글 등록
	 *===================*/	
	@PostMapping("/writeReply")
	@ResponseBody
	public Map<String,String> writeReply(
							BoardReplyVO boardReplyVO,
							@AuthenticationPrincipal
							PrincipalDetails principal,
							HttpServletRequest request){
		log.debug("<<댓글 등록>> : " + boardReplyVO);
		
		Map<String,String> mapJson = new HashMap<String,String>();
		try {
			//회원번호 저장
			boardReplyVO.setMem_num(
					principal.getMemberVO().getMem_num());
			//IP저장
			boardReplyVO.setRe_ip(request.getRemoteAddr());
			//댓글 등록
			boardService.insertReply(boardReplyVO);
			mapJson.put("result", "success");
			//다른 종류 에러 콘솔 에 보여짐
		}catch(NullPointerException e) {
			mapJson.put("result", "logout");
		}
			
		return mapJson;
	}
	/*===================
	 * 댓글 목록
	 *===================*/	
	@GetMapping("/listReply")
	@ResponseBody
	public Map<String,Object> getList(
			int board_num,int pageNum,
			int rowCount,
			@AuthenticationPrincipal
			PrincipalDetails principal){
		
		log.debug("<<board_num>> : " + board_num);
		log.debug("<<currentPage>> : " + pageNum);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("board_num",board_num);
		//총 글의 개수
		int count = boardService.selectRowContReply(map);
		
		PagingUtil page = new PagingUtil(pageNum,count,rowCount);
		map.put("start", page.getStartRow());
		map.put("end", page.getEndRow());
		
		List<BoardReplyVO> list = null;
		if(count > 0) {
			list = boardService.selectListReply(map);
			
		}else {
			list = Collections.emptyList();
		}
		Map<String,Object> mapJson = new HashMap<String,Object>();
		mapJson.put("list", list);
		mapJson.put("count", count);
		//로그인한 회원번호와 작성자 회원번호 일치 여부를 체크하기 위해 로그인한 회원번호 전송
		if(principal!=null) {
			mapJson.put("user_num", principal.getMemberVO().getMem_num());
		}
				
		return mapJson;
	}
	/*===================
	 * 댓글 수정
	 *===================*/	
	@PostMapping("/updateReply")
	@ResponseBody
	public Map<String,String> modifyReply(
						BoardReplyVO boardReplyVO,
						@AuthenticationPrincipal PrincipalDetails principal,
						HttpServletRequest request){
		log.debug("<<댓글 수정>> : " + boardReplyVO);
		
		Map<String,String> mapJson = new HashMap<String,String>();
		
		BoardReplyVO db_reply = boardService.selectReply(boardReplyVO.getRe_num());
		if(principal==null) {
			//로그인이 되지 않은 경우
			mapJson.put("result", "logout");
		}else if(principal.getMemberVO().getMem_num() == db_reply.getMem_num()) {
			//로그인 회원번호와 작성자 회원번호 일치
			//IP 저장
			boardReplyVO.setRe_ip(request.getRemoteAddr());
			//댓글 수정
			boardService.updateReply(boardReplyVO);
			mapJson.put("result", "success");
		}else {
			//로그인 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wrongAccess");
		}
		
		return mapJson;
	}
	
	
	/*===================
	 * 댓글 삭제
	 *===================*/	
	@GetMapping("/deleteReply")
	@ResponseBody
	public Map<String,String> deleteReply(
					long re_num,
					@AuthenticationPrincipal PrincipalDetails principal){
		
		log.debug("<<댓글 삭제 - re_num>> : " + re_num);
		Map<String,String> mapJson = new HashMap<String,String>();
		BoardReplyVO db_reply = boardService.selectReply(re_num);
		
		if(principal==null) {
			//로그인이 되어있지 않음
			mapJson.put("result", "logout");
		}else if(principal.getMemberVO().getMem_num() == db_reply.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호 일치
			boardService.deleteReply(re_num);
			mapJson.put("result", "success");
		}else {
			//로그인한 회원번호와 작성자 회원번호 불일치
			mapJson.put("result", "wrongAccess");
		}
		
		return mapJson;
	}
	
}







