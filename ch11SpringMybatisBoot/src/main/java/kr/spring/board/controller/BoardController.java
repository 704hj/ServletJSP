package kr.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	//주입받아야하니까
	@Autowired
	private BoardService boardService;
	//로그 처리(로그 대상 지정)
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	//자바빈(vo) 초기화
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
	@PostMapping("/insert.do")
	public String submit(@Valid BoardVO vo,BindingResult result) {
		log.debug("<<전송된 데이터 - BoardVO>> : "+ vo);
		//전송된 데이터 유효성 체크 결과 오류가 있으면 폼 호출
		if(result.hasErrors()) {
			return form();
		}
		//글 등록
		boardService.insertBoard(vo);
		
		return "redirect:/list.do";
		
	}
	
	//부트에서 이게 컨트롤러에 꼭 들어가야한다는데 왜?--------------------------------------->이거 이해하기
	@GetMapping("/")
	public String init() {
		return "redirect:/list.do";
	}
	
	
	
	//목록
	@GetMapping("/list.do")
	public ModelAndView getList( 
			@RequestParam(defaultValue="1") int pageNum){

		//총 레코드 수 
		int count = boardService.selectBoardCount();
		log.debug("<<count>> : " + count);

		//페이지 처리
		PagingUtil page = new PagingUtil(pageNum,count,20,10,"list.do");

		//목록 호출
		List<BoardVO> list = null;
		if(count > 0) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("start", page.getStartRow());
			map.put("end", page.getEndRow());

			list = boardService.selectBoardList(map);
		}


		ModelAndView mav = new ModelAndView();
		//뷰 이름 설정
		mav.setViewName("selectList"); 

		//뷰에서 보여질 데이터 설정
		mav.addObject("count",count);
		mav.addObject("list",list);
		mav.addObject("page",page.getPage());
		return mav;
	}
	
		//글 상세
		@GetMapping("/detail.do")
		public ModelAndView detail(int num) {
			
			//한 건의 데이터 읽어옴
			BoardVO board = boardService.selectBoard(num);
									//뷰이름, 속성명, 속성값
			return new ModelAndView("selectDetail","board",board);
		}
		
		//글수정 폼
		@GetMapping("/update.do")
		public String formUpdate(int num, Model model) {
			
			model.addAttribute("boardVO",boardService.selectBoard(num));
			
			return "updateForm";
		}
		
		//글 수정 처리
		@PostMapping("/update.do")
		public String submitUpdate(@Valid BoardVO vo,BindingResult result) {
			log.debug("<<글수정 - BoardVO>> :" + vo);
			//전송된 데이터 유효성 체크 결과 오류가 있으면 폼을 호출
			if(result.hasErrors()) {
				return "updateForm";
			}
			//비밀번호 일치 여부 체크
			//DB에 저장된 비밀번호 구하기
			BoardVO db_board = boardService.selectBoard(vo.getNum());
			
			//비밀번호 체크
			if(!db_board.getPasswd().equals(vo.getPasswd())) {
				result.rejectValue("passwd", "invalidPassword");
				return "updateForm";
			}
			boardService.updateBoard(vo);
			
			return "redirect:/detail.do?num="+vo.getNum();
		}
		
		//글 삭제 폼
		@GetMapping("/delete.do")//int num이 아닌 이유 : 이중작업을 해야한다?
		public String formDelete(BoardVO vo) {
			return "deleteForm";
		}
		
		//글 삭제 처리
		@PostMapping("/delete.do")
		public String submitDelete(@Valid BoardVO vo,BindingResult result) {
			log.debug("<<글삭제 - BoardVO>> :" + vo);
			//전송된 데이터 유효성 체크 결과 오류가 있으면 폼을 호출 ->  비밀번호 전송 여부만 체크
			if(result.hasFieldErrors("passwd")) {
				return "deleteForm";
			}
			//비밀번호 일치 여부 체크
			//DB에 저장된 비밀번호 구하기
			BoardVO db_board = boardService.selectBoard(vo.getNum());
			
			//비밀번호 체크
			if(!db_board.getPasswd().equals(vo.getPasswd())) {
				result.rejectValue("passwd", "invalidPassword");
				return "deleteForm";
			}
			
			//글 삭제
			boardService.deleteBoard(vo.getNum());
			
			return "redirect:/list.do";
		}
		
}









































