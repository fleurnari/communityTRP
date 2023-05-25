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
			String sql = "SELECT reply_number, reply_writer, reply_content, reply_regdate FROM notice_reply WHERE notice_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
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
	
}
