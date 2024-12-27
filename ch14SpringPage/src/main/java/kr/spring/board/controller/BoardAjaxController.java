package kr.spring.board.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.spring.board.service.BoardService;
import kr.spring.board.vo.BoardVO;
import kr.spring.member.vo.MemberVO;
import kr.spring.member.vo.PrincipalDetails;
import kr.spring.util.FileUtil;
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
	public Map<String,String> processFile(long board_num, @AuthenticationPrincipal PrincipalDetails principal, HttpServletRequest request){
		Map<String,String> mapJson = new HashMap<String,String>();
		try {
			MemberVO user = principal.getMemberVO();
			
			BoardVO db_board = boardService.selectBoard(board_num);
			//로그인한 회원번호와 작성자 회원번호 일치 여부
			if(user.getMem_num() != db_board.getMem_num()) {
				//불일치
				mapJson.put("result", "wrongAccess");
			}else {
				//일치
				boardService.deleteFile(board_num);
				FileUtil.removeFile(request, db_board.getFilename());
				mapJson.put("result", "success");
			}
			
		}catch(Exception e) {
			mapJson.put("result", "logout");
		}
		return mapJson;
	}
}

























