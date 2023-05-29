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
		
		MainBoard main = null;
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
				main = new MainBoard();
				main.setBoardNumber(rs.getInt("board_number"));
				main.setBoardTitle(rs.getString("board_title"));
				main.setBoardWriter(rs.getString("board_writer"));
				main.setBoardRegdate(rs.getDate("board_regdate"));
				main.setBoardHit(rs.getInt("board_hit"));
				main.setBoardRecomm(rs.getInt("board_recomm"));
				list.add(main);
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
		MainBoard main = null;
		try {
			conn();
			String sql = "SELECT * FROM trp_main WHERE board_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				main = new MainBoard();
				main.setBoardNumber(rs.getInt("board_number"));
				main.setBoardTitle(rs.getString("board_title"));
				main.setBoardWriter(rs.getString("board_writer"));
				main.setBoardContent(rs.getString("board_content"));
				main.setBoardRegdate(rs.getDate("board_regdate"));
				main.setBoardHit(rs.getInt("board_hit"));
				main.setBoardRecomm(rs.getInt("board_recomm"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return main;
	}
	
	// 삼행시 작성
	public int insertBoard(MainBoard main) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO trp_main VALUES (trp_main_seq.NEXTVAL,?,?,?,sysdate,0,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, main.getBoardTitle());
			pstmt.setString(2, main.getBoardWriter());
			pstmt.setString(3, main.getBoardContent());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

		return result;
	}
	
	// 삼행시 수정
	public int updateBoard(MainBoard main, int selectNo) {
		int result = 0;
		try {
			conn();
			String sql = "";
			if (selectNo == 1) {
				sql = "UPDATE trp_main SET board_title = ? WHERE board_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, main.getBoardTitle());
			} else if (selectNo == 2) {
				sql = "UPDATE trp_main SET board_content = ? WHERE board_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, main.getBoardContent());
			}
				pstmt.setInt(2, main.getBoardNumber());
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
		MainBoard main = null;
		
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
				main = new MainBoard();
				main.setBoardNumber(rs.getInt("board_number"));
				main.setBoardTitle(rs.getString("board_title"));
				main.setBoardWriter(rs.getString("board_writer"));
				main.setBoardRegdate(rs.getDate("board_regdate"));
				main.setBoardHit(rs.getInt("board_hit"));
				main.setBoardRecomm(rs.getInt("board_recomm"));
				list.add(main);
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
	
	
	// 베스트 삼행시 목록 조회
	public List<MainBoard> getBestMainList() {
		List<MainBoard> list = new ArrayList<>();
		
		MainBoard main = null;
		try {
			conn();
			String sql = "SELECT RANKING, BOARD_NUMBER, BOARD_TITLE, BOARD_WRITER, BOARD_REGDATE, BOARD_HIT, BOARD_RECOMM\r\n"
					+ "FROM (SELECT ROW_NUMBER() OVER (ORDER BY BOARD_RECOMM DESC, BOARD_HIT DESC) AS RANKING, BOARD_NUMBER, BOARD_TITLE, BOARD_WRITER, BOARD_REGDATE, BOARD_HIT, BOARD_RECOMM\r\n"
					+ "FROM TRP_MAIN ORDER BY BOARD_RECOMM DESC) WHERE RANKING BETWEEN 1 AND 10";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				main = new MainBoard();
				main.setRanking(rs.getInt("ranking"));
				main.setBoardNumber(rs.getInt("board_number"));
				main.setBoardTitle(rs.getString("board_title"));
				main.setBoardWriter(rs.getString("board_writer"));
				main.setBoardRegdate(rs.getDate("board_regdate"));
				main.setBoardHit(rs.getInt("board_hit"));
				main.setBoardRecomm(rs.getInt("board_recomm"));
				list.add(main);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}
	
	
	

}
