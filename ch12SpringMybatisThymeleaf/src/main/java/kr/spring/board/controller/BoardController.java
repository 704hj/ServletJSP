package kr.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardVO;
import kr.spring.util.PagingUtil;

@Controller
public class BoardController {
	@Resource
	private BoardService boardService;
	
	//로그 처리(로그 대상 지정)
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	/*
	 * 로그 레벨
	 * FATAL : 가장 심각한 오류
	 * ERROR : 일반적인 오류
	 * WARN : 주의를 요하는 경우
	 * INFO : 런타임시 관심있는 내용
	 * DEBUG : 시스템 흐름과 관련된 상세 정보
	 * TRACE : 가장 상세한 정보
	 */
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public BoardVO initCommand() {
		return new BoardVO();
	}
	
	@GetMapping("/")
	public String main() {
		return "redirect:/list.do";
	}
	
	@GetMapping("/list.do")
	public ModelAndView getList(
			@RequestParam(value="pageNum",defaultValue="1") int currentPage) {
		
		//총 레코드 수
		int count = boardService.getBoardCount();
		
		//페이징 처리
		PagingUtil page = 
				new PagingUtil(currentPage,count,10,10,"list.do");
		
		//목록 호출
		List<BoardVO> list = null;
		if(count > 0) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = boardService.getBoardList(map);
		}
		
		ModelAndView mav = new ModelAndView();
		//뷰 이름 설정
		mav.setViewName("selectList");
		//데이터 저장
		mav.addObject("count", count);
		mav.addObject("list",list);
		mav.addObject("page", page.getPage());
		
		return mav;
	}
	
	//글쓰기 폼
	@GetMapping("/insert.do")
	public String form() {
		return "insertForm";
	}
	
	//글쓰기 처리
	@PostMapping("/insert.do")
	public String submit(@Valid BoardVO boardCommand,
			         BindingResult result) {
		
		//if(log.isDebugEnabled()) {
			log.debug("<<BoardCommand>> : " + boardCommand);
			log.info("<<~~~~~~!!!>> : " + boardCommand);
		//}
		
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		
		//글쓰기
		boardService.insertBoard(boardCommand);
		
		return "redirect:/list.do";
	}
	
	//글 상세
	@GetMapping("/detail.do")
	public ModelAndView detail(@RequestParam int num) {
		BoardVO board = 
				boardService.getBoard(num);
		
		return new ModelAndView("selectDetail","board",board);		
	}
	
	//수정 폼
	@GetMapping("/update.do")
	public String formUpdate(@RequestParam int num,
			                 Model model) {
		
		model.addAttribute(
				"boardVO", boardService.getBoard(num));
		
		return "updateForm";
	}	
	//글 수정 처리
	@PostMapping("/update.do")
	public String submitUpdate(@Valid BoardVO boardCommand,
			                   BindingResult result) {
		
		//유효성 체크 결과 오류가 있으면 폼을 호출
		if(result.hasErrors()) {
			return "updateForm";
		}
		
		//비밀번호 일치 여부 체크
		//DB에 저장된 비밀번호 구하기
		BoardVO dbBoard = boardService.getBoard(
				                     boardCommand.getNum());
		
		//비밀번호 체크
		if(!dbBoard.getPasswd().equals(boardCommand.getPasswd())) {
			result.rejectValue("passwd", "invalidPassword");
			return "updateForm";
		}
		
		boardService.updateBoard(boardCommand);
		
		return "redirect:/list.do";
	}
	
	//글 삭제 폼
	@GetMapping("/delete.do")
	public String formDelete(BoardVO vo) {
		return "deleteForm";
	}
	
	//글 삭제 처리
	@PostMapping("/delete.do")
	public String submitDelete(@Valid BoardVO boardCommand,
			                 BindingResult result) {
		//유효성 체크 결과 오류가 있으면 폼을 호출
		//비밀번호가 전송 여부만 체크
		if(result.hasFieldErrors("passwd")) {
			return "deleteForm";
		}
		
		//비밀번호 일치 여부 체크
		//DB에 저장된 비밀번호 구하기
		BoardVO dbBoard = 
				boardService.getBoard(boardCommand.getNum());
		
		//비밀번호 체크
		if(!dbBoard.getPasswd().equals(boardCommand.getPasswd())) {
			result.rejectValue("passwd", "invalidPassword");
			return "deleteForm";
		}
		
		//글 삭제
		boardService.deleteBoard(boardCommand.getNum());
		
		return "redirect:/list.do";
	}
	
}








