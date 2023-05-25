package com.trp.notice;

import java.util.ArrayList;
import java.util.List;

import com.trp.common.DAO;

public class NoticeReplyDAO extends DAO {

	private static NoticeReplyDAO ntReplyDao = null;
	
	private NoticeReplyDAO() {
		
	}
	
	public static NoticeReplyDAO getInstance() {
		if (ntReplyDao == null) {
			ntReplyDao = new NoticeReplyDAO();
		}
		return ntReplyDao;
	}
	
	// 댓글 조회
	public List<NoticeReply> getReplyList(int boardNum) {
		List<NoticeReply> list = new ArrayList<>();
		NoticeReply reply = null;
		
		try {
			conn();
			String sql = "SELECT reply_number, reply_writer, reply_content, reply_regdate FROM notice_reply WHERE notice_number = ? ORDER BY 1 DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				reply = new NoticeReply();
				reply.setReplyNumber(rs.getInt("reply_number"));
				reply.setReplyWriter(rs.getString("reply_writer"));
				reply.setReplyContent(rs.getString("reply_content"));
				reply.setReplyRegdate(rs.getDate("reply_regdate"));
				list.add(reply);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return list;
	}
	
	
	// 댓글 작성
	public int writeReply(NoticeReply reply) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO notice_reply VALUES (?,notice_reply_seq.nextval,?,?,sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getNoticeNumber());
			pstmt.setString(2, reply.getReplyWriter());
			pstmt.setString(3, reply.getReplyContent());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		
		return result;
	}
	
	// 댓글 수정
	public int updateReply(NoticeReply reply) {
		int result = 0;
		
		try {
			conn();
			String sql = "UPDATE notice_reply SET reply_content = ? WHERE reply_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reply.getReplyContent());
			pstmt.setInt(2, reply.getReplyNumber());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return result;
	}
	
	// 댓글 삭제
	public int deleteReply(int replyNum) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM notice_reply WHERE reply_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNum);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return result;
	}
	
	
	
	// 선택된 댓글 조회
	public NoticeReply getReply (int replyNum) {
		NoticeReply reply = null;
		try {
			conn();
			String sql = "SELECT reply_number, reply_writer, reply_content FROM notice_reply WHERE reply_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				reply = new NoticeReply();
				reply.setReplyNumber(rs.getInt("reply_number"));
				reply.setReplyWriter(rs.getString("reply_writer"));
				reply.setReplyContent(rs.getString("reply_content"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return reply;
	}
	

	
	
}
