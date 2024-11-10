package kr.board.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.board.dao.BoardDAO;
import kr.board.vo.BoardFavVO;
import kr.controller.Action;
import kr.util.StringUtil;

public class GetFavAction implements Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");

        long board_num=Long.parseLong(request.getParameter("board_num"));

        Map<String, Object> mapAjax= new HashMap<String,Object>();
        HttpSession session=request.getSession();
        Long user_num=(Long)session.getAttribute("user_num");
        BoardDAO dao= BoardDAO.getInstance();
        if(user_num==null) {
            mapAjax.put("status", "noFav");
        }else {
            BoardFavVO boardFav= dao.selectFav(new BoardFavVO(board_num,user_num));

            if(boardFav!=null) {
                mapAjax.put("status", "yesFav");
            }else {
                mapAjax.put("status", "noFav");
            }
        }
        //좋아요 개수
        mapAjax.put("count",dao.selectFavCount(board_num));

        //JSON 데이터로 변환
        return StringUtil.parseJSON(request, mapAjax);
    }

}