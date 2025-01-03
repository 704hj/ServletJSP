package kr.spring.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.util.FileUtil;
import kr.spring.util.PagingUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	//자바빈(VO) 초기화
	@ModelAttribute
	public BoardVO initCommand() {
		return new BoardVO();
	}
	
	/*==============================
	 * 	게시판 글 등록	
	 * =============================*/
	//등록 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/write")
	public String form() {
		return "boardWrite";
	}
	//전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/write")
	public String submit(@Valid BoardVO boardVO,BindingResult result,HttpServletRequest request,RedirectAttributes redirect,@AuthenticationPrincipal PrincipalDetails principal) throws IllegalStateException, IOException {
		log.debug("<<게시판 글 저장>> :" + boardVO);
		//유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		//회원 번호 읽기
		MemberVO vo = principal.getMemberVO();
		boardVO.setMem_num(vo.getMem_num());
		//ip셋팅
		boardVO.setIp(request.getRemoteAddr());
		//파일 업로드
		boardVO.setFilename(FileUtil.createFile(request, boardVO.getUpload()));
		
		//글쓰기
		boardService.insertBoard(boardVO);
		
		//RedirectAttributes 객체는 리다이렉트 시점에 한 번만 사용되는 데이트를 전송.
		//브라우저에 데이터를 전송하지만 URI상에는 보이지 않는 숨겨진 데이터의 형태로 전달
		redirect.addFlashAttribute("result","success");
		
		//리다이렉트할  url에 쿼리 스트링으로 추가
		//redirect.addAttribute("board_num",10);
		
		
		return "redirect:/board/list";
	}
	
	
	/*==============================
	 * 	게시판 목록
	 * =============================*/
	@GetMapping("/list")
	public String getList(
			@RequestParam(defaultValue="1") int pageNum,
			@RequestParam(defaultValue="1") int order,
			@RequestParam(defaultValue="") String category,
			String keyfield,String keyword,Model model) {
		
		log.debug("<<게시판 목록 - category>> : " + category);
		log.debug("<<게시판 목록 - order>> : " + order);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("category", category);
		map.put("keyfield", keyfield);
		map.put("keyword", keyword);
		
		//전체/검색 레코드수 
		int count = boardService.selectRowCount(map);
		
		//페이지 처리
		PagingUtil page = new PagingUtil(keyfield,keyword,pageNum,count,20,10,"list","&category="+category+"&order="+order);

		List<BoardVO> list = null;
		if(count > 0) {
			map.put("order", order);
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());
			
			list = boardService.selectList(map);
		}
		
		model.addAttribute("count", count);
		model.addAttribute("list", list);
		model.addAttribute("page", page.getPage());
		
		return "boardList";//boardList로 간다
	}

	
	/*==============================
	 * 	게시판 글 상세
	 * =============================*/
	@GetMapping("/detail")
	public String process(long board_num,Model model) {
		log.debug("<<게시판 글상세 - board_num>> :" +board_num);
		//해당 글의 조회수 증가 
		boardService.updateHit(board_num);
		
		BoardVO board = boardService.selectBoard(board_num);
		
		model.addAttribute("board",board);
		return "boardView";
	}
	//파일 다운로드
	@GetMapping("/file")
	public String download(long board_num,HttpServletRequest request,Model model) {
		BoardVO board = boardService.selectBoard(board_num);
		byte[] downloadFile = FileUtil.getBytes(request.getServletContext().getRealPath("/assets/upload")+"/"+board.getFilename());
		
		//다운로드 넘기는건 model역할
		model.addAttribute("downloadFile", downloadFile);
		model.addAttribute("filename", board.getFilename());
		
		return "downloadView";
	}
	
	/*========================
	 * 게시판 글 수정
	 *========================*/
	//수정 폼
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/update")
	public String formUpdate(long board_num,
			                 Model model,
			                 @AuthenticationPrincipal
			                 PrincipalDetails principal) {
		BoardVO boardVO = boardService.selectBoard(board_num);
		
		//DB에 저장된 파일 정보 구하기
		if(principal.getMemberVO().getMem_num() != 
				                 boardVO.getMem_num()) {
			//로그인한 회원번호와 작성자 회원번호 불일치
			return "redirect:/common/accessDenied";
		}
		
		model.addAttribute("boardVO", boardVO);
		
		return "boardModify";
	}
	//수정 폼에서 전송된 데이터 처리
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/update")
	public String submitUpdate(@Valid BoardVO boardVO,
			                  BindingResult result,
			                  HttpServletRequest request,
			                  Model model,
			                  @AuthenticationPrincipal
			                  PrincipalDetails principal) 
			                		  throws IllegalStateException, IOException {
		log.debug("<<글 수정>> : " + boardVO);
		
		//DB에 저장된 파일 정보 구하기
		BoardVO db_board = 
			boardService.selectBoard(boardVO.getBoard_num());
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(principal.getMemberVO().getMem_num() != 
				db_board.getMem_num()) {
			return "redirect:/common/accessDenied";
		}
		//전송된 데이터 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			//title 또는 content가 입력되지 않아 유효성 체크에 걸리면
			//파일 정보를 잃어버리기 때문에 폼을 호출할 때 다시 셋팅해주어야
			//함
			boardVO.setFilename(db_board.getFilename());
			
			return "boardModify";
		}
		//파이명 셋팅(FileUtil.createFile에서 파일이 없으면 null 처리했음)
		boardVO.setFilename(
				FileUtil.createFile(request, 
						             boardVO.getUpload()));
		//ip 셋팅
		boardVO.setIp(request.getRemoteAddr());
		//글 수정
		boardService.updateBoard(boardVO);
		
		//파일 교체 여부를 체크해서 교체했을 경우 기존 파일을 삭제
		if(boardVO.getUpload() != null && 
				!boardVO.getUpload().isEmpty()) {
			//기존 파일(수정 작업전 파일) 삭제 처리
			FileUtil.removeFile(request, db_board.getFilename());
		}
		
		//View에 표시할 메시지
		model.addAttribute("message", "글 수정 완료!!");
		model.addAttribute("url", 
				request.getContextPath() + 
				         "/board/detail?board_num="
						             +boardVO.getBoard_num());		
		return "common/resultAlert";
	}
	/*========================
	 * 게시판 글 삭제
	 *========================*/
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/delete")
	public String submitDelete(long board_num,
			                   HttpServletRequest request,
			                   @AuthenticationPrincipal
			                   PrincipalDetails principal) {
		log.debug("<<게시판 글 삭제>> : " + board_num);
		
		//DB에 저장된 게시판 정보 구하기
		BoardVO db_board = 
				       boardService.selectBoard(board_num);
		//로그인한 회원번호와 작성자 회원번호 일치 여부 체크
		if(principal.getMemberVO().getMem_num() != 
				db_board.getMem_num()) {
			return "redirect:/common/accessDenied";
		}
		
		//글 삭제
		boardService.deleteBoard(board_num);
		
		if(db_board.getFilename() != null) {
			//파일 삭제
			FileUtil.removeFile(request, db_board.getFilename());
		}
		
		return "redirect:/board/list";
	}
	
}


























