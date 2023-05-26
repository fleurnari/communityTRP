package com.trp.mainboard;

import java.util.ArrayList;
import java.util.List;

import com.trp.common.DAO;

public class MainBoardDAO extends DAO {
	
	private static MainBoardDAO mainBoardDao = null;
	
	private MainBoardDAO() {
		
	}
	
	public static MainBoardDAO getInstance() {
		if (mainBoardDao == null) {
			mainBoardDao = new MainBoardDAO();
		}
		return mainBoardDao;
	}
	

	// 삼행시 게시판 글 목록 조회
	public List<MainBoard> getBoardList(int page) {
		List<MainBoard> list = new ArrayList<>();
		int start = 1 + (page - 1) * 10;
		int end = 10 * page;
		
		MainBoard notice = null;
		try {
			conn();
			String sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit, board_recomm "
					+ "FROM (SELECT ROWNUM NUM, N.* FROM (SELECT * FROM trp_main ORDER BY board_number DESC) N)"
					+ "WHERE NUM BETWEEN ? AND ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				notice = new MainBoard();
				notice.setBoardNumber(rs.getInt("board_number"));
				notice.setBoardTitle(rs.getString("board_title"));
				notice.setBoardWriter(rs.getString("board_writer"));
				notice.setBoardRegdate(rs.getDate("board_regdate"));
				notice.setBoardHit(rs.getInt("board_hit"));
				notice.setBoardRecomm(rs.getInt("board_recomm"));
				list.add(notice);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}
	

	// 삼행시 게시물 상세 조회
	public MainBoard getBoard(int boardNum) {
		MainBoard notice = null;
		try {
			conn();
			String sql = "SELECT * FROM trp_main WHERE board_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				notice = new MainBoard();
				notice.setBoardNumber(rs.getInt("board_number"));
				notice.setBoardTitle(rs.getString("board_title"));
				notice.setBoardWriter(rs.getString("board_writer"));
				notice.setBoardContent(rs.getString("board_content"));
				notice.setBoardRegdate(rs.getDate("board_regdate"));
				notice.setBoardHit(rs.getInt("board_hit"));
				notice.setBoardRecomm(rs.getInt("board_recomm"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return notice;
	}
	
	// 삼행시 작성
	public int insertBoard(MainBoard notice) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO trp_main VALUES (trp_main_seq.NEXTVAL,?,?,?,sysdate,0,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, notice.getBoardTitle());
			pstmt.setString(2, notice.getBoardWriter());
			pstmt.setString(3, notice.getBoardContent());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

		return result;
	}
	
	// 삼행시 수정
	public int updateBoard(MainBoard notice, int selectNo) {
		int result = 0;
		try {
			conn();
			String sql = "";
			if (selectNo == 1) {
				sql = "UPDATE trp_main SET board_title = ? WHERE board_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, notice.getBoardTitle());
			} else if (selectNo == 2) {
				sql = "UPDATE trp_main SET board_content = ? WHERE board_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, notice.getBoardContent());
			}
				pstmt.setInt(2, notice.getBoardNumber());
				result = pstmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return result;
		
	}
	
	// 삼행시 삭제
	public int deleteBoard(int boardNum) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM trp_main WHERE board_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return result;		
	}
	
	// 삼행시 게시물 검색
	public List<MainBoard> searchBoard(String searchWord, int selectNo) {
		List<MainBoard> list = new ArrayList<>();
		MainBoard notice = null;
		
		try {
			conn();
			String sql = "";
			if (selectNo == 1) {
				sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit, board_recomm FROM trp_main WHERE board_title LIKE '%'||?||'%' OR board_content LIKE '%'||?||'%' ORDER BY 1 DESC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setString(2, searchWord);
			} else if (selectNo == 2) {
				sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit, board_recomm FROM trp_main WHERE board_writer LIKE '%'||?||'%' ORDER BY 1 DESC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				notice = new MainBoard();
				notice.setBoardNumber(rs.getInt("board_number"));
				notice.setBoardTitle(rs.getString("board_title"));
				notice.setBoardWriter(rs.getString("board_writer"));
				notice.setBoardRegdate(rs.getDate("board_regdate"));
				notice.setBoardHit(rs.getInt("board_hit"));
				notice.setBoardRecomm(rs.getInt("board_recomm"));
				list.add(notice);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		
		return list;
		
	}
	
	// 게시판 조회수
	public void boardHit(int boardNum) {
		try {
			conn();
			String sql = "UPDATE trp_main SET board_hit = board_hit + 1 WHERE board_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}
	
	// 게시판 추천 기능
	public void boardRecomm(int boardNum) {
		try {
			conn();
			String sql = "UPDATE trp_main SET board_recomm = board_recomm + 1 WHERE board_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	}
	
	

}
