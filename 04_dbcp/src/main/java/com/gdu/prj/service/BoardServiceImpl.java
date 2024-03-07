package com.gdu.prj.service;

import com.gdu.prj.common.ActionForward;
import com.gdu.prj.dao.BoardDao;
import com.gdu.prj.dao.BoardDaoimpl;

import jakarta.servlet.http.HttpServletRequest;

/*
 * view - controller - service - dao - db
 */

public class BoardServiceImpl implements BoardService {
	
	// service는 dao 를 호출한다.
	private BoardDao boardDao = new BoardDaoimpl()

	@Override
	public ActionForward addBoard(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward getBoardList(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward getBoardByNo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward editBoard(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward modifyBoard(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward removeBoard(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
