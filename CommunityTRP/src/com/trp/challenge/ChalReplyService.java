package com.trp.challenge;

import java.util.List;
import java.util.Scanner;

import com.trp.member.MemberService;

public class ChalReplyService {
	
	Scanner sc = new Scanner(System.in);
	
	// 댓글 조회
	public void getReplyList(int boardNum) {
		List<ChalReply> list = ChalReplyDAO.getInstance().getReplyList(boardNum);
		System.out.println("===============================");
		System.out.println("번호 | \t작성자\t | \t내용\t | 등록일");
		if (list.size() == 0) {
			System.out.println("등록된 댓글이 없습니다.");
		} else {
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getReplyNumber() + "\t" + list.get(i).getReplyWriter() + "\t" + list.get(i).getReplyContent() + "\t" + list.get(i).getReplyRegdate());
			}
		}
	}
	
	// 댓글 작성
	public void writeReply(int boardNum) {
		ChalReply reply = new ChalReply();
		
		if (MemberService.memberInfo == null) {
			System.out.println("먼저 로그인을 해 주세요.");
		} else {
			System.out.println("===== 댓글 작성 =====");
			reply.setBoardNumber(boardNum);
			reply.setReplyWriter(MemberService.memberInfo.getMemberId());
			System.out.println("작성할 댓글 내용을 입력해 주세요>");
			reply.setReplyContent(sc.nextLine());
			
			int result = ChalReplyDAO.getInstance().writeReply(reply);
			
			if (result > 0) {
				System.out.println("댓글 작성이 완료 되었습니다.");
			} else {
				System.out.println("댓글 작성에 실패 했습니다.");
			}
		}
				
	}
	
	// 댓글 수정
	public void updateReply(int boardNum) {
		ChalReply reply = new ChalReply();
		reply.setBoardNumber(boardNum);
		System.out.println("===== 댓글 수정 =====");
		System.out.println("수정할 댓글 번호를 입력해 주세요.");
		int replyNum = Integer.parseInt(sc.nextLine());
		reply = ChalReplyDAO.getInstance().getReply(replyNum);
		if (MemberService.memberInfo == null) {
			System.out.println("먼저 로그인을 해 주세요.");
		} else if (!(MemberService.memberInfo.getMemberId().equals(reply.getReplyWriter()))) {
			System.out.println("해당 댓글 수정 권한이 없습니다.");
		} else {
			System.out.println("수정할 댓글 내용 입력>");
			reply.setReplyContent(sc.nextLine());
			
			int result = ChalReplyDAO.getInstance().updateReply(reply);
			if (result > 0) {
				System.out.println("댓글 수정이 완료 되었습니다.");
			} else {
				System.out.println("댓글 수정에 실패 했습니다.");
			}
		}
		
	}
	
	
	public void deleteReply(int boardNum) {
		ChalReply reply = new ChalReply();
		reply.setBoardNumber(boardNum);
		System.out.println("===== 댓글 삭제 =====");
		System.out.println("삭제할 댓글 번호를 입력해 주세요.");
		int replyNum = Integer.parseInt(sc.nextLine());
		reply = ChalReplyDAO.getInstance().getReply(replyNum);
		if (MemberService.memberInfo == null) {
			System.out.println("먼저 로그인을 해 주세요.");
		} else if (!(MemberService.memberInfo.getMemberAuth().equals("A")) && !(MemberService.memberInfo.getMemberId().equals(reply.getReplyWriter()))) {
			System.out.println("해당 댓글 삭제 권한이 없습니다.");
		} else {
			int result = ChalReplyDAO.getInstance().deleteReply(reply.getReplyNumber());
			if (result > 0) {
				System.out.println("댓글 삭제가 완료 되었습니다.");
			} else {
				System.out.println("댓글 삭제에 실패 했습니다.");
			}
		}
	}
	
	// 댓글 작업 메소드
	public void replyWork(int boardNum) {
		System.out.println("1. 댓글 작성 | 2. 댓글 수정 | 3. 댓글 삭제 | 4. 취소");
		int rpSelectNo = Integer.parseInt(sc.nextLine());
		if (rpSelectNo == 1) {
			writeReply(boardNum);
		} else if (rpSelectNo == 2) {
			updateReply(boardNum);
		} else if (rpSelectNo == 3) {
			deleteReply(boardNum);
		} else if (rpSelectNo == 4) {
			return;
		} else {
			System.out.println("잘못된 입력입니다.");
		}
	}
	

}
