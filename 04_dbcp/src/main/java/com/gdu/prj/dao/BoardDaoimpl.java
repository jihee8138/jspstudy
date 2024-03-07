package com.gdu.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import com.gdu.prj.dto.BoardDto;

/*
 * view - controller - service - dao - db
 */


public class BoardDaoimpl implements BoardDao {
	
	// dao 는 db 를 처리한다.
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;

	@Override
	public int insertBoard(BoardDto board) {
		return 0;
	}

	@Override
	public int upadateBoard(BoardDto board) {
		return 0;
	}

	@Override
	public int deleteBoard(int board_no) {
		return 0;
	}

	@Override
	public List<BoardDto> selectBoardList(Map<String, Object> map) {
		return null;
	}

	@Override
	public int getBoardCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BoardDto selectBoardByNo(int board_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
