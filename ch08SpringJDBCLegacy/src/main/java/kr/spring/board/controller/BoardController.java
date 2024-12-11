package kr.spring.board.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardVO;
import kr.spring.util.PagingUtil;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	//로그 처리(로그 대상 지정) -> 콘솔에 에러메세지 띄우는 작업
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	/*
	 * log4j.xml에서 설정한다.
	 * 
	 * 로그 레벨
	 * FATAL : 가장 심각한 오류
	 * ERROR : 일반적인 오류
	 * WARN : 주의를 요하는 경우		 -> INFO,DEBUG,TRACE 를 제외한 나머지를 보여줌
	 * INFO : 런타임시 관심있는 내용 	 -> 	 DEBUG,TRACE 를 제외한 나머지를 보여줌
	 * DEBUG : 시스템 흐름과 관련된 상세 정보
	 * TRACE : 가장 상세한 정보 
	 */
	
	
	//annotation으로 유효성 체크 -> 자바빈(vo)초기화 필요
	@ModelAttribute
	public BoardVO initCommand() {
		return new BoardVO();
	}
	//글쓰기 폼
	@GetMapping("/insert.do")
	public String form() {
		return "insertForm";
	}
	
	//전송된 데이터 처리
	@PostMapping("/insert.do") //자바빈 객체 담고, BindingResult까지 연속적으로 담아줘야함
	public String submit(@Valid BoardVO vo, BindingResult result) {
		log.debug("<<전송된 데이터 : BoardVO>> : " + vo);
		
		//유효성 체크 결과 오류가 있으면 폼을 호출
		if(result.hasErrors()) {
			return form();
		}
		
		//디버그 체크, 유효성체크 결과 정상적이라면 글 등록
		boardService.insertBoard(vo);
		
		return "redirect:/list.do";
	}
	
	//목록
	@GetMapping("/list.do")
	public ModelAndView process(@RequestParam(defaultValue="1") int pageNum) {
		
		int count = boardService.getBoardCount();
		
		log.debug("<<count>> : " + count);
		
		PagingUtil page = new PagingUtil(pageNum,count,20,10,"list.do");
		List<BoardVO> list = null;
		if(count > 0) {
			list = boardService.getBoardList(page.getStartRow(), page.getEndRow());
		}
		
		ModelAndView mav = new ModelAndView();
		//뷰 이름 지정
		mav.setViewName("selectList");
		mav.addObject("count", count);
		mav.addObject("list",list);
		mav.addObject("page", page.getPage());
		
		return mav;
	}
	
	//상세
	@GetMapping("/detail.do")// null값이 넘어와도 되면 Integer|꼭 전달해야하는 상황 int
	public ModelAndView detail(int num) {
		log.debug("<<num>> : " + num);
		
		BoardVO board = boardService.getBoard(num);
								//뷰이름 			속성명   속성값 -> view에서 ${board.num} 일케 가져와야함
		return new ModelAndView("selectDetail","board",board);
	}
	
	//수정 폼
	@GetMapping("/update.do")		//모델 객체를 인자로 받는다
	public String formUpdate(int num, Model model){
		log.debug("<<num>> : " + num);
		//모델 객체에 데이터 추가	/ 속성명	, 속성값
		model.addAttribute("boardVO", boardService.getBoard(num));
		
		
		return "updateForm";
	}
	
	//전송된 데이터 수정
    @PostMapping("/update.do")
    public String submitUpdate(@Valid BoardVO vo, BindingResult result) {
        log.debug("<<글수정 : BoardVO>> : " + vo);
        //전송된 데이터 유효성 체크 결과 오류가 있으면 폼을 호출
        if(result.hasErrors()) {
            return "updateForm";
        }
        //DB에 저장된 비밀번호 구하기
        BoardVO db_board = boardService.getBoard(vo.getNum());
        //비밀번호 일치 여부 체크
        if(!db_board.getPasswd().equals(vo.getPasswd())) {
                                //필드,     오류코드
            result.rejectValue("passwd", "invalidPassword");
            return "updateForm";
        }
        //글수정
        boardService.updateBoard(vo);
        
        return "redirect:/detail.do?num="+vo.getNum();
    }

    //삭제
    @GetMapping("/delete.do")
    public String formDelete(BoardVO vo) {
    	 return "deleteForm";
    	
    }
    
    //글삭제 처리
    @PostMapping("/delete.do")
    public String submitDelete(@Valid BoardVO vo, BindingResult result) {
    	log.debug("<<글삭제 : BoardVO>> : " + vo);
    
    	//비밀번호만 유효성 체크 결과 확인
    	if(result.hasFieldErrors("passwd")) {
    		return "deleteForm";
    	}
    	//DB에 저장된 비밀번호 구하기
    	BoardVO db_board = boardService.getBoard(vo.getNum());
    	
    	//비밀번호 일치 여부 체크
    	if(!db_board.getPasswd().equals(vo.getPasswd())) {
    		result.rejectValue("passwd", "invalidPassword");
    		return "deleteForm";
    	}
    	
    	boardService.deleteBoard(vo.getNum());
    	
    	return "redirect:/list.do";
    }
    



}




























