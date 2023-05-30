package com.trp.notice;

import java.util.ArrayList;
import java.util.List;

import com.trp.common.DAO;
import com.trp.member.MemberService;

public class NoticeBoardDAO extends DAO {
	
	private static NoticeBoardDAO ntBoardDao = null;
	
	private NoticeBoardDAO() {
		
	}
	
	public static NoticeBoardDAO getInstance() {
		if (ntBoardDao == null) {
			ntBoardDao = new NoticeBoardDAO();
		}
		return ntBoardDao;
	}
	

	// 공지사항 게시판 글 목록 조회
	public List<NoticeBoard> getBoardList(int page) {
		List<NoticeBoard> list = new ArrayList<>();
		int start = 1 + (page - 1) * 10;
		int end = 10 * page;
		
		NoticeBoard notice = null;
		try {
			conn();
			String sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit "
					+ "FROM (SELECT ROWNUM NUM, N.* FROM (SELECT * FROM trp_notice ORDER BY board_number DESC) N)"
					+ "WHERE NUM BETWEEN ? AND ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				notice = new NoticeBoard();
				notice.setBoardNumber(rs.getInt("board_number"));
				notice.setBoardTitle(rs.getString("board_title"));
				notice.setBoardWriter(rs.getString("board_writer"));
				notice.setBoardRegdate(rs.getDate("board_regdate"));
				notice.setBoardHit(rs.getInt("board_hit"));
				list.add(notice);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return list;
	}
	

	// 공지사항 게시물 상세 조회
	public NoticeBoard getBoard(int boardNum) {
		NoticeBoard notice = null;
		try {
			conn();
			String sql = "SELECT * FROM trp_notice WHERE board_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				notice = new NoticeBoard();
				notice.setBoardNumber(rs.getInt("board_number"));
				notice.setBoardTitle(rs.getString("board_title"));
				notice.setBoardWriter(rs.getString("board_writer"));
				notice.setBoardContent(rs.getString("board_content"));
				notice.setBoardRegdate(rs.getDate("board_regdate"));
				notice.setBoardHit(rs.getInt("board_hit"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return notice;
	}
	
	// 공지사항 작성
	public int insertBoard(NoticeBoard notice) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO trp_notice VALUES (trp_notice_seq.NEXTVAL,?,?,?,sysdate,0)";
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
	
	// 공지사항 수정
	public int updateBoard(NoticeBoard notice, int selectNo) {
		int result = 0;
		try {
			conn();
			String sql = "";
			if (selectNo == 1) {
				sql = "UPDATE trp_notice SET board_title = ? WHERE board_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, notice.getBoardTitle());
			} else if (selectNo == 2) {
				sql = "UPDATE trp_notice SET board_content = ? WHERE board_number = ?";
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
	
	// 공지사항 삭제
	public int deleteBoard(int boardNum) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM trp_notice WHERE board_number = ?";
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
	
	// 공지사항 게시물 검색
	public List<NoticeBoard> searchBoard(String searchWord, int selectNo, int page) {
		List<NoticeBoard> list = new ArrayList<>();
		NoticeBoard notice = null;
		int start = 1 + (page - 1) * 10;
		int end = 10 * page;
		
		try {
			conn();
			String sql = "";
			if (selectNo == 1) {
				sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit FROM (SELECT ROWNUM NUM, N.* FROM (SELECT board_number, board_title, board_writer, board_regdate, board_hit FROM trp_notice WHERE board_title LIKE '%'||?||'%' OR board_content LIKE '%'||?||'%' ORDER BY 1 DESC) N) WHERE NUM BETWEEN ? AND ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, start);
				pstmt.setInt(4, end);
			} else if (selectNo == 2) {
				sql = "SELECT board_number, board_title, board_writer, board_regdate, board_hit FROM (SELECT ROWNUM NUM, N.* FROM (SELECT board_number, board_title, board_writer, board_regdate, board_hit FROM trp_notice WHERE board_writer LIKE '%'||?||'%' ORDER BY 1 DESC) N) WHERE NUM BETWEEN ? AND ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, start);
				pstmt.setInt(3, end);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				notice = new NoticeBoard();
				notice.setBoardNumber(rs.getInt("board_number"));
				notice.setBoardTitle(rs.getString("board_title"));
				notice.setBoardWriter(rs.getString("board_writer"));
				notice.setBoardRegdate(rs.getDate("board_regdate"));
				notice.setBoardHit(rs.getInt("board_hit"));
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
			String sql = "UPDATE trp_notice SET board_hit = board_hit + 1 WHERE board_number = ?";
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
