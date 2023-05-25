package com.trp.notice;

import java.util.List;

public class NoticeReplyService {
	
	// 댓글 조회
	public void getReplyList(int boardNum) {
		List<NoticeReply> list = NoticeReplyDAO.getInstance().getReplyList(boardNum);
		System.out.println("번호 | \t작성자\t | \t내용\t | 등록일");
		if (list.size() == 0) {
			System.out.println("등록된 댓글이 없습니다.");
		} else {
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getReplyNumber() + "\t" + list.get(i).getReplyWriter() + "\t" + list.get(i).getReplyContent() + "\t" + list.get(i).getReplyRegdate());
			}
		}
	}
}
