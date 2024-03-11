package com.gdu.prj.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.gdu.prj.dto.BoardDto;

/*
 * view - filter - controller - service - dao - db
 */


public class BoardDaoImpl implements BoardDao {
	
	// dao 는 db 를 처리한다.
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	// Connection Pool 관리를 위한 DataSource 객체 선언
	private DataSource dataSource;
	
	// SingletonPattern
	private static BoardDao boardDao = new BoardDaoImpl();
	private BoardDaoImpl() {
		// META-INF\context.xml 파일에 명시된 Resource 를 이용해 DataSource 객체
		try {
			Context context = new InitialContext();
			Context env = (Context)context.lookup("java:comp/env");
			dataSource = (DataSource)env.lookup("jdbc/myoracle");
		} catch (NamingException e) {
			System.out.println("관련 자원을 찾을 수 없습니다.");
		}
	}
	
  public static BoardDao getInstance() {
    return boardDao;
  }

	@Override
	public int insertBoard(BoardDto board) {
		int insertCount = 0;
		try {
			con = dataSource.getConnection();
			String sql = "INSERT INTO BOARD_T(BOARD_NO, TITLE, CONTENTS, MODIFIED_AT, CREATED_AT) VALUES(BOARD_SEQ.NEXTVAL, ?, ?, CURRENT_DATE, CURRENT_DATE)";
			// ps : 쿼리문 실행을 담당하는 객체
			ps = con.prepareStatement(sql);
			// 변수 처리
			ps.setString(1, board.getTitle());  // 첫번째 ?에 title 집어넣기
			ps.setString(2, board.getContents());
			// 실행결과 받는 애 : insertCount
			insertCount = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// this.close(); 라고 호출해도 됨
			close();
		}
		return insertCount;
	}

	@Override
	public int updateBoard(BoardDto board) {
		int updateCount = 0;
		try {
			con = dataSource.getConnection();
			String sql = "UPDATE BOARD_T SET TITLE = ?, CONTENTS = ?, MODIFIED_AT = CURRENT_DATE WHERE BOARD_NO = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContents());
			ps.setInt(3, board.getBoard_no());
			updateCount = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return updateCount;
	}

	@Override
	public int deleteBoard(int board_no) {
		int deleteCount = 0;
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM BOARD_T WHERE BOARD_NO = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, board_no);
			deleteCount = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return deleteCount;
	}
	
	@Override
	public int deleteBoards(String param) {
		int deleteCount = 0;
		try {
			con = dataSource.getConnection();
			String sql = "DELETE FROM BOARD_T WHERE BOARD_NO IN(" + param + ")";
			ps = con.prepareStatement(sql);
			deleteCount = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return deleteCount;
		
	}
	

	@Override
	public List<BoardDto> selectBoardList(Map<String, Object> params) {
		List<BoardDto> boardList = new ArrayList<>();
		try {
			con = dataSource.getConnection();
			// 순서 : BOARD_NO, TITLE, CONTENTS, MODIFIED_AT, CREATED_AT FROM BOARD_T
			String sql = "SELECT BOARD_NO, TITLE, CONTENTS, MODIFIED_AT, CREATED_AT"
          + "  FROM (SELECT ROW_NUMBER() OVER(ORDER BY BOARD_NO " + params.get("sort") + ") AS RN, BOARD_NO, TITLE, CONTENTS, MODIFIED_AT, CREATED_AT"
          + "          FROM BOARD_T)"
          + " WHERE RN BETWEEN ? AND ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, (int)params.get("begin"));  // params가 object로 저장되어 있기 때문에 setInt 시 캐스팅 필요
			ps.setInt(2, (int)params.get("end"));
			
			// 실행 결과 받는 애 : rs (select 결과는 무조건 rs가 받는다.)
			// rs 객체가 select 결과를 하나씩 하나씩 찾는다.
			rs = ps.executeQuery();
			// rs.next 는 갯수만큼 호출해야한다. (select 가 3개면 rs.next 3개 호출)
			// 데이터가 존재하면 존재하는 만큼 boardDto가 만들어져서 boatdList에 추가 됨
			// while 문으로 계속 호출해서 읽어내라
			while(rs.next()) {
				BoardDto board = BoardDto.builder()
																 .board_no(rs.getInt(1))  // 순서는 쿼리문 보고 결정
																 .title(rs.getString(2))
																 .contents(rs.getString(3))
																 .modified_at(rs.getDate(4))
																 .created_at(rs.getDate(5))
																 .build();
			// 해당 데이터를 boardList에 추가
			boardList.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return boardList;
	}

	@Override
	// 전체 게시물 갯수 구하는 애
	public int getBoardCount() {
		int boardCount = 0;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT COUNT(*) FROM BOARD_T";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			// rs.next 는 무조건 호출한다. (데이터가 무조건 1개인 경우에는 while 말고 if 사용)
			if (rs.next()) {
				boardCount = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return boardCount;
	}

	@Override
	public BoardDto selectBoardByNo(int board_no) {
		BoardDto board = null;
		try {
			con = dataSource.getConnection();
			String sql = "SELECT BOARD_NO, TITLE, CONTENTS, MODIFIED_AT, CREATED_AT FROM BOARD_T WHERE BOARD_NO = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, board_no);
			rs = ps.executeQuery();
			if (rs.next()) {
				board = BoardDto.builder()
												.board_no(rs.getInt(1))
												.title(rs.getString(2))
												.contents(rs.getString(3))
												.modified_at(rs.getDate(4))
												.created_at(rs.getDate(5))
												.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return board;
	}

	@Override
	public void close() {
		try {
			if(rs != null)  rs.close();
			if(ps != null)  ps.close();
			if(con != null) con.close();  // Connection 반납으로 동작
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}

	}

}
