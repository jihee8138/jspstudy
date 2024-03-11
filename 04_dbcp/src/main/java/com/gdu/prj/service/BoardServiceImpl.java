package com.gdu.prj.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.gdu.prj.common.ActionForward;
import com.gdu.prj.dao.BoardDao;
import com.gdu.prj.dao.BoardDaoImpl;
import com.gdu.prj.dto.BoardDto;
import com.gdu.prj.utils.MyPageUtils;

import jakarta.servlet.http.HttpServletRequest;

/*
 * view - filter - controller - service - dao - db
 */
public class BoardServiceImpl implements BoardService {
	
	// service는 dao 를 호출한다.
	private BoardDao boardDao =  BoardDaoImpl.getInstance();
	
	// 목록 보기는 MyPageUtils 객체가 필요하다. (객체 생성)
	private MyPageUtils myPageUtils = new MyPageUtils();

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
		
		// 전체 게시글 개수
		int total = boardDao.getBoardCount();
		
		// 한 페이지에 표시할 게시글 개수
		Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
		// 요청 파라미터로 받는데 혹시 전달이 안 되면 20개로 하겠다
		int display = Integer.parseInt(optDisplay.orElse("20"));
		
		// 현재 페이지 번호
		Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(optPage.orElse("1"));
		
		// 정렬 방식
		Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
		String sort = optSort.orElse("DESC");
		
		// 페이징 처리에 필요한 변수 값 계산하기
		myPageUtils.setPaging(total, display, page);
		
		
		// 목록을 가져올 때 필요한 변수를 Map 에 저장함
		Map<String, Object> params = Map.of("begin", myPageUtils.getBegin(),
																				"end", myPageUtils.getEnd(),
																				"sort", sort);
		
		// 목록 가져오기
		List<BoardDto> boardList = boardDao.selectBoardList(params);
		
		// 페이지 링크 가져오기
		// request 만 넘기면 안됨
		String paging = myPageUtils.getPaging(request.getRequestURI(), sort, display);
		// /dbcp/board/list.brd
		
		// JSP 에 전달할 데이터들
		request.setAttribute("total", total);
		request.setAttribute("boardList", boardList);
		request.setAttribute("paging", paging);
		request.setAttribute("display", display);
		request.setAttribute("sort", sort);

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
