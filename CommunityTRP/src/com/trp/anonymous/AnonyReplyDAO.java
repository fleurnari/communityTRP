package com.trp.anonymous;

import java.util.ArrayList;
import java.util.List;

import com.trp.common.DAO;


public class AnonyReplyDAO extends DAO {
	
	private static AnonyReplyDAO anonyReplyDao = null;
	
	private AnonyReplyDAO() {
		
	}
	
	public static AnonyReplyDAO getInstance() {
		if (anonyReplyDao == null) {
			anonyReplyDao = new AnonyReplyDAO();
		}
		return anonyReplyDao;
	}
	
	// 댓글 조회
	public List<AnonyReply> getReplyList(int boardNum) {
		List<AnonyReply> list = new ArrayList<>();
		AnonyReply reply = null;
		
		try {
			conn();
			String sql = "SELECT reply_number, reply_writer, reply_content, reply_regdate FROM anony_reply WHERE board_number = ? ORDER BY 1 DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				reply = new AnonyReply();
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
	public int writeReply(AnonyReply reply) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO anony_reply VALUES (?,anony_reply_seq.nextval,?,?,sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reply.getBoardNumber());
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
	public int updateReply(AnonyReply reply) {
		int result = 0;
		
		try {
			conn();
			String sql = "UPDATE anony_reply SET reply_content = ? WHERE reply_number = ?";
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
			String sql = "DELETE FROM anony_reply WHERE reply_number = ?";
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
	public AnonyReply getReply (int replyNum) {
		AnonyReply reply = null;
		try {
			conn();
			String sql = "SELECT reply_number, reply_writer, reply_content FROM anony_reply WHERE reply_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				reply = new AnonyReply();
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
