package com.trp.challenge;

import java.util.ArrayList;
import java.util.List;

import com.trp.common.DAO;

public class ChalBoardDAO extends DAO {
	
	
	private static ChalBoardDAO chalBoardDao = null;
	
	private ChalBoardDAO() {
		
	}
	
	public static ChalBoardDAO getInstance() {
		if (chalBoardDao == null) {
			chalBoardDao = new ChalBoardDAO();
		}
		return chalBoardDao;
	}
	

	// 챌린지 게시판 글 목록 조회
	public List<ChalBoard> getBoardList(int page) {
		List<ChalBoard> list = new ArrayList<>();
		int start = 1 + (page - 1) * 10;
		int end = 10 * page;
		
		ChalBoard chal = null;
		try {
			conn();
			String sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit, board_recomm "
					+ "FROM (SELECT ROWNUM NUM, N.* FROM (SELECT * FROM trp_chal ORDER BY board_number DESC) N)"
					+ "WHERE NUM BETWEEN ? AND ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				chal = new ChalBoard();
				chal.setBoardNumber(rs.getInt("board_number"));
				chal.setBoardTitle(rs.getString("board_title"));
				chal.setBoardWriter(rs.getString("board_writer"));
				chal.setBoardRegdate(rs.getDate("board_regdate"));
				chal.setBoardHit(rs.getInt("board_hit"));
				chal.setBoardRecomm(rs.getInt("board_recomm"));
				list.add(chal);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}
	

	// 챌린지 게시물 상세 조회
	public ChalBoard getBoard(int boardNum) {
		ChalBoard chal = null;
		try {
			conn();
			String sql = "SELECT * FROM trp_chal WHERE board_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				chal = new ChalBoard();
				chal.setBoardNumber(rs.getInt("board_number"));
				chal.setBoardTitle(rs.getString("board_title"));
				chal.setBoardWriter(rs.getString("board_writer"));
				chal.setBoardContent1(rs.getString("board_content1"));
				chal.setBoardContent2(rs.getString("board_content2"));
				chal.setBoardContent3(rs.getString("board_content3"));
				chal.setBoardRegdate(rs.getDate("board_regdate"));
				chal.setBoardHit(rs.getInt("board_hit"));
				chal.setBoardRecomm(rs.getInt("board_recomm"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return chal;
	}
	
	// 챌린지 작성
	public int insertBoard(ChalBoard chal) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO trp_chal VALUES (trp_chal_seq.NEXTVAL,?,?,?,?,?,sysdate,0,0)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, chal.getBoardTitle());
			pstmt.setString(2, chal.getBoardWriter());
			pstmt.setString(3, chal.getBoardContent1());
			pstmt.setString(4, chal.getBoardContent2());
			pstmt.setString(5, chal.getBoardContent3());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}

		return result;
	}
	
	// 챌린지 수정
	public int updateBoard(ChalBoard chal, int selectNo) {
		int result = 0;
		try {
			conn();
			String sql = "";
			if (selectNo == 1) {
				sql = "UPDATE trp_chal SET board_content1 = ? WHERE board_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, chal.getBoardContent1());
			} else if (selectNo == 2) {
				sql = "UPDATE trp_chal SET board_content2 = ? WHERE board_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, chal.getBoardContent2());
			} else if (selectNo == 3) {
				sql = "UPDATE trp_chal SET board_content3 = ? WHERE board_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, chal.getBoardContent3());
			} 
				pstmt.setInt(2, chal.getBoardNumber());
				result = pstmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return result;
		
	}
	
	// 챌린지 삭제
	public int deleteBoard(int boardNum) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM trp_chal WHERE board_number = ?";
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
	
	// 챌린지 게시물 검색
	public List<ChalBoard> searchBoard(String searchWord, int selectNo) {
		List<ChalBoard> list = new ArrayList<>();
		ChalBoard chal = null;
		
		try {
			conn();
			String sql = "";
			if (selectNo == 1) {
				sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit, board_recomm FROM trp_chal WHERE board_title LIKE '%'||?||'%' OR board_content1 LIKE '%'||?||'%' OR board_content2 LIKE '%'||?||'%' OR board_content3 LIKE '%'||?||'%' ORDER BY 1 DESC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setString(2, searchWord);
				pstmt.setString(3, searchWord);
				pstmt.setString(4, searchWord);
			} else if (selectNo == 2) {
				sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit, board_recomm FROM trp_chal WHERE board_writer LIKE '%'||?||'%' ORDER BY 1 DESC";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				chal = new ChalBoard();
				chal.setBoardNumber(rs.getInt("board_number"));
				chal.setBoardTitle(rs.getString("board_title"));
				chal.setBoardWriter(rs.getString("board_writer"));
				chal.setBoardRegdate(rs.getDate("board_regdate"));
				chal.setBoardHit(rs.getInt("board_hit"));
				chal.setBoardRecomm(rs.getInt("board_recomm"));
				list.add(chal);
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
			String sql = "UPDATE trp_chal SET board_hit = board_hit + 1 WHERE board_number = ?";
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
			String sql = "UPDATE trp_chal SET board_recomm = board_recomm + 1 WHERE board_number = ?";
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
	public List<ChalBoard> getBestChalList() {
		List<ChalBoard> list = new ArrayList<>();
		
		ChalBoard chal = null;
		try {
			conn();
			String sql = "SELECT RANKING, BOARD_NUMBER, BOARD_TITLE, BOARD_WRITER, BOARD_REGDATE, BOARD_HIT, BOARD_RECOMM\r\n"
					+ "FROM (SELECT ROW_NUMBER() OVER (ORDER BY BOARD_RECOMM DESC, BOARD_HIT DESC) AS RANKING, BOARD_NUMBER, BOARD_TITLE, BOARD_WRITER, BOARD_REGDATE, BOARD_HIT, BOARD_RECOMM\r\n"
					+ "FROM TRP_CHAL ORDER BY BOARD_RECOMM DESC) WHERE RANKING BETWEEN 1 AND 10";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				chal = new ChalBoard();
				chal.setRanking(rs.getInt("ranking"));
				chal.setBoardNumber(rs.getInt("board_number"));
				chal.setBoardTitle(rs.getString("board_title"));
				chal.setBoardWriter(rs.getString("board_writer"));
				chal.setBoardRegdate(rs.getDate("board_regdate"));
				chal.setBoardHit(rs.getInt("board_hit"));
				chal.setBoardRecomm(rs.getInt("board_recomm"));
				list.add(chal);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}

}
