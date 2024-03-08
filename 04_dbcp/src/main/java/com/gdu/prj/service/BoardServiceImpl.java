package com.gdu.prj.service;

import java.util.List;
import java.util.Optional;

import com.gdu.prj.common.ActionForward;
import com.gdu.prj.dao.BoardDao;
import com.gdu.prj.dao.BoardDaoImpl;
import com.gdu.prj.dto.BoardDto;

import jakarta.servlet.http.HttpServletRequest;

/*
 * view - filter - controller - service - dao - db
 */
public class BoardServiceImpl implements BoardService {
	
	// service는 dao 를 호출한다.
	private BoardDao boardDao =  BoardDaoImpl.getInstance();

	@Override
	public ActionForward addBoard(HttpServletRequest request) {
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		BoardDto board = BoardDto.builder()
														 .title(title)
														 .contents(contents)
														 .build();
		int insertCount = boardDao.insertBoard(board);
		// insert 성공하면 목록보기, 실패하면 index.jsp로 넘어가기
		// redirect 경로는 URLMApping 으로 작성한다.
		String view = null;
		if(insertCount == 1) {
			view = request.getContextPath() + "/board/list.brd"; 
		} else if(insertCount == 0) {
			view = request.getContextPath() + "/main.brd";
		}
		// INSERT 이후 이동은 redirect 이다.
		return new ActionForward(view, true);
	}

	@Override
	public ActionForward getBoardList(HttpServletRequest request) {
		int boardCount = boardDao.getBoardCount();
		List<BoardDto> boardList = boardDao.selectBoardList(null);
		request.setAttribute("boardCount", boardCount);
		request.setAttribute("boardList", boardList);
		// 위에 있는 결과를 가지고 /board/list.jsp로 가라
		return new ActionForward("/board/list.jsp", false);
	}

	@Override
	public ActionForward getBoardByNo(HttpServletRequest request) {
		Optional<String> opt = Optional.ofNullable(request.getParameter("board_no"));
		int board_no = Integer.parseInt(opt.orElse("0"));
		BoardDto board = boardDao.selectBoardByNo(board_no);
		String view = null;
		if(board != null) {
			view = "/board/detail.jsp";
			request.setAttribute("board", board);
		} else {
			view = "/index.jsp";
		}
		return new ActionForward(view, false);
	}

	@Override
	// edit 가 하는 일 : 편집 화면으로 가는 것 (거기서 편집하고 수정하는 건 modify가 함)
	// 그렇기 때문에 select 해야 함
	public ActionForward editBoard(HttpServletRequest request) {
		// board_no 가 null 값일 가능성은 없다. 빈 문자열일 가능성은 있다.
		String param = request.getParameter("board_no");
		int board_no = 0;
		if(!param.isEmpty()) {
			board_no = Integer.parseInt(param);
		}
		BoardDto board = boardDao.selectBoardByNo(board_no);
		String view = null;
		if(board != null) {
			view = "/board/edit.jsp";
			request.setAttribute("board", board);
		} else {
			view = "/index/jsp";
		}
		return new ActionForward(view, false);
	}

	@Override
	public ActionForward modifyBoard(HttpServletRequest request) {
		int board_no = Integer.parseInt(request.getParameter("board_no"));
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");
		BoardDto board = BoardDto.builder()
				  									 . title(title)
				  									 .contents(contents)
				  									 .board_no(board_no)
				  									 .build();
		int updateCount = boardDao.updateBoard(board);
		String view = null;
		if(updateCount == 0) {
			view = request.getContextPath() + "/main.brd";
		} else {
			view = request.getContextPath() + "/board/detail.brd?board_no=" + board_no;
		}
		// redirect 를 위해서 true 반환
		return new ActionForward(view, true);
	}

	@Override
	public ActionForward removeBoard(HttpServletRequest request) {
		String param = request.getParameter("board_no");
		int board_no = 0;
		if(!param.isEmpty()) {
			board_no = Integer.parseInt(param);
		}
		// 삭제할 게시글의 번호를 deleteBoard 라는 Dao 에 전달
		int deleteCount = boardDao.deleteBoard(board_no);
		String view = null;
		if(deleteCount == 0) {
			view = request.getContextPath() + "/main.brd";
		} else {
			view = request.getContextPath() + "/board/list.brd";
		}
		// insert update delete 는 redirect 해줘야함
		return new ActionForward(view, true);
	}
	
	@Override
	public ActionForward removeBoards(HttpServletRequest request) {
		String param = request.getParameter("param");
		int deleteCount = boardDao.deleteBoards(param);
		String view = null;
		if(deleteCount == 0) {
			view = request.getContextPath() + "/main.brd";
		} else {
			view = request.getContextPath() + "/board/list.brd";
		}
		return new ActionForward(view, true);
	}

}
