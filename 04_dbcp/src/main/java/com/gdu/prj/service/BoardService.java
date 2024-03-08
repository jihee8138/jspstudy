package com.gdu.prj.service;

import com.gdu.prj.common.ActionForward;

import jakarta.servlet.http.HttpServletRequest;

public interface BoardService {
	ActionForward addBoard(HttpServletRequest request);
	ActionForward getBoardList(HttpServletRequest request);
	ActionForward getBoardByNo(HttpServletRequest request);
	ActionForward editBoard(HttpServletRequest request);
	ActionForward modifyBoard(HttpServletRequest request);
	ActionForward removeBoard(HttpServletRequest request);
	ActionForward removeBoards(HttpServletRequest request);
}
