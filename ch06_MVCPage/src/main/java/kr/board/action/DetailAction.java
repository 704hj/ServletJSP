package kr.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class DetailAction implements Action{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long board_num = Long.parseLong(request.getParameter("board_num"));
        BoardDAO dao = BoardDAO.getInstance();
        //조회수 증가
        dao.updateReadcount(board_num);

        BoardVO board = dao.getBoard(board_num);
        //HTML 태그를 허용하지 않음
        board.setTitle(StringUtil.useNoHtml(board.getTitle()));
        //HTML 태그를 허용하지 않으면서 줄바꿈 처리
        board.setContent(StringUtil.useBrNoHtml(board.getContent()));

        request.setAttribute("board", board);

        return "board/detail.jsp";
    }

}