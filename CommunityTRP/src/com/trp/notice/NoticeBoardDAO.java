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
			String sql = "SELECT notice_number, notice_title, notice_writer, notice_regdate, notice_hit "
					+ "FROM (SELECT ROWNUM NUM, N.* FROM (SELECT * FROM trp_notice ORDER BY notice_number DESC) N)"
					+ "WHERE NUM BETWEEN ? AND ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				notice = new NoticeBoard();
				notice.setNoticeNumber(rs.getInt("notice_number"));
				notice.setNoticeTitle(rs.getString("notice_title"));
				notice.setNoticeWriter(rs.getString("notice_writer"));
				notice.setNoticeRegdate(rs.getDate("notice_regdate"));
				notice.setNoticeHit(rs.getInt("notice_hit"));
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
			String sql = "SELECT * FROM trp_notice WHERE notice_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				notice = new NoticeBoard();
				notice.setNoticeNumber(rs.getInt("notice_number"));
				notice.setNoticeTitle(rs.getString("notice_title"));
				notice.setNoticeWriter(rs.getString("notice_writer"));
				notice.setNoticeContent(rs.getString("notice_content"));
				notice.setNoticeRegdate(rs.getDate("notice_regdate"));
				notice.setNoticeHit(rs.getInt("notice_hit"));
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
			pstmt.setString(1, notice.getNoticeTitle());
			pstmt.setString(2, notice.getNoticeWriter());
			pstmt.setString(3, notice.getNoticeContent());
			
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
				sql = "UPDATE trp_notice SET notice_title = ? WHERE notice_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, notice.getNoticeTitle());
			} else if (selectNo == 2) {
				sql = "UPDATE trp_notice SET notice_content = ? WHERE notice_number = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, notice.getNoticeContent());
			}
				pstmt.setInt(2, notice.getNoticeNumber());
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
			String sql = "DELETE FROM trp_notice WHERE notice_number = ?";
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
	public List<NoticeBoard> searchBoard(String searchWord, int selectNo) {
		List<NoticeBoard> list = new ArrayList<>();
		NoticeBoard notice = null;
		
		try {
			conn();
			String sql = "";
			if (selectNo == 1) {
				sql = "SELECT notice_number, notice_title, notice_writer, notice_regdate, notice_hit FROM trp_notice WHERE notice_title LIKE '%'||?||'%' OR notice_content LIKE '%'||?||'%'";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setString(2, searchWord);
			} else if (selectNo == 2) {
				sql = "SELECT notice_number, notice_title, notice_writer, notice_regdate, notice_hit FROM trp_notice WHERE notice_writer LIKE '%'||?||'%'";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
			}
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				notice = new NoticeBoard();
				notice.setNoticeNumber(rs.getInt("notice_number"));
				notice.setNoticeTitle(rs.getString("notice_title"));
				notice.setNoticeWriter(rs.getString("notice_writer"));
				notice.setNoticeRegdate(rs.getDate("notice_regdate"));
				notice.setNoticeHit(rs.getInt("notice_hit"));
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
			String sql = "UPDATE trp_notice SET notice_hit = notice_hit + 1 WHERE notice_number = ?";
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
