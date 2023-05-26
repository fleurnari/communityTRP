package com.trp.challenge;

import java.util.ArrayList;
import java.util.List;

import com.trp.common.DAO;

public class ChalReplyDAO extends DAO {
	
private static ChalReplyDAO chalReplyDao = null;
	
	private ChalReplyDAO() {
		
	}
	
	public static ChalReplyDAO getInstance() {
		if (chalReplyDao == null) {
			chalReplyDao = new ChalReplyDAO();
		}
		return chalReplyDao;
	}
	
	// 댓글 조회
	public List<ChalReply> getReplyList(int boardNum) {
		List<ChalReply> list = new ArrayList<>();
		ChalReply reply = null;
		
		try {
			conn();
			String sql = "SELECT reply_number, reply_writer, reply_content, reply_regdate FROM chal_reply WHERE board_number = ? ORDER BY 1 DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				reply = new ChalReply();
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
	public int writeReply(ChalReply reply) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO chal_reply VALUES (?,chal_reply_seq.nextval,?,?,sysdate)";
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
	public int updateReply(ChalReply reply) {
		int result = 0;
		
		try {
			conn();
			String sql = "UPDATE chal_reply SET reply_content = ? WHERE reply_number = ?";
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
			String sql = "DELETE FROM chal_reply WHERE reply_number = ?";
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
	public ChalReply getReply (int replyNum) {
		ChalReply reply = null;
		try {
			conn();
			String sql = "SELECT reply_number, reply_writer, reply_content FROM chal_reply WHERE reply_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				reply = new ChalReply();
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
