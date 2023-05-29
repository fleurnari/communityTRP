package com.trp.anonymous;

import java.util.ArrayList;
import java.util.List;

import com.trp.common.DAO;
import com.trp.report.Report;

public class AnonyBoardDAO extends DAO {
	
	private static AnonyBoardDAO anonyBoardDao = null;
	
	private AnonyBoardDAO() {
		
	}
	
	public static AnonyBoardDAO getInstance() {
		if (anonyBoardDao == null) {
			anonyBoardDao = new AnonyBoardDAO();
		}
		return anonyBoardDao;
	}
	

	// 익명 게시판 글 목록 조회
	public List<AnonyBoard> getBoardList(int page) {
		List<AnonyBoard> list = new ArrayList<>();
		int start = 1 + (page - 1) * 10;
		int end = 10 * page;
		
		AnonyBoard anony = null;
		try {
			conn();
			String sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit "
					+ "FROM (SELECT ROWNUM NUM, N.* FROM (SELECT * FROM trp_anony ORDER BY board_number DESC) N)"
					+ "WHERE NUM BETWEEN ? AND ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				anony = new AnonyBoard();
				anony.setBoardNumber(rs.getInt("board_number"));
				anony.setBoardTitle(rs.getString("board_title"));
				anony.setBoardWriter(rs.getString("board_writer"));
				anony.setBoardRegdate(rs.getDate("board_regdate"));
				anony.setBoardHit(rs.getInt("board_hit"));
				list.add(anony);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}
	

	// 익명 게시물 상세 조회
	public AnonyBoard getBoard(int boardNum) {
		AnonyBoard anony = null;
		try {
			conn();
			String sql = "SELECT * FROM trp_anony WHERE board_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				anony = new AnonyBoard();
				anony.setBoardNumber(rs.getInt("board_number"));
				anony.setBoardTitle(rs.getString("board_title"));
				anony.setBoardWriter(rs.getString("board_writer"));
				anony.setBoardContent(rs.getString("board_content"));
				anony.setBoardRegdate(rs.getDate("board_regdate"));
				anony.setBoardHit(rs.getInt("board_hit"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return anony;
	}
	
	// 익명 작성
	public int insertBoard(AnonyBoard anony) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO trp_anony VALUES (trp_anony_seq.NEXTVAL,?,?,?,sysdate,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anony.getBoardTitle());
			pstmt.setString(2, anony.getBoardWriter());
			pstmt.setString(3, anony.getBoardContent());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

		return result;
	}
	
	// 익명 수정
	public int updateBoard(AnonyBoard anony, int selectNo) {
		int result = 0;
		try {
			conn();
			String sql = "";
			if (selectNo == 1) {
				sql = "UPDATE trp_anony SET board_title = ? WHERE board_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, anony.getBoardTitle());
			} else if (selectNo == 2) {
				sql = "UPDATE trp_anony SET board_content = ? WHERE board_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, anony.getBoardContent());
			}
				pstmt.setInt(2, anony.getBoardNumber());
				result = pstmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return result;
		
	}
	
	// 익명 삭제
	public int deleteBoard(int boardNum) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM trp_anony WHERE board_number = ?";
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
	
	// 익명 게시물 검색
	public List<AnonyBoard> searchBoard(String searchWord) {
		List<AnonyBoard> list = new ArrayList<>();
		AnonyBoard anony = null;
		
		try {
			conn();
			String sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit FROM trp_anony WHERE board_title LIKE '%'||?||'%' OR board_content LIKE '%'||?||'%' ORDER BY 1 DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			pstmt.setString(2, searchWord);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				anony = new AnonyBoard();
				anony.setBoardNumber(rs.getInt("board_number"));
				anony.setBoardTitle(rs.getString("board_title"));
				anony.setBoardWriter(rs.getString("board_writer"));
				anony.setBoardRegdate(rs.getDate("board_regdate"));
				anony.setBoardHit(rs.getInt("board_hit"));
				list.add(anony);
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
			String sql = "UPDATE trp_anony SET board_hit = board_hit + 1 WHERE board_number = ?";
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
