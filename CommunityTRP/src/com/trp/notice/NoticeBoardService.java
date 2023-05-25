package com.trp.notice;

import java.util.List;
import java.util.Scanner;

import com.trp.member.MemberService;



public class NoticeBoardService {
	
	Scanner sc = new Scanner(System.in);
	NoticeReplyService nrs = new NoticeReplyService();
	
	// 공지사항 게시판 글 목록 조회
	public void getBoardList(int page) {
		List<NoticeBoard> list = NoticeBoardDAO.getInstance().getBoardList(page);
		System.out.println("번호 | \t제목\t | \t작성자\t | \t등록일\t | 조회수");
		if (list.size() == 0) {
			System.out.println("등록된 게시물이 없습니다.");
		} else {
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getNoticeNumber() + "\t" + list.get(i).getNoticeTitle() + "\t" + list.get(i).getNoticeWriter() + "\t" + list.get(i).getNoticeRegdate() + "\t" + list.get(i).getNoticeHit());
			}
		}
	}
	
	// 공지사항 게시물 상세 조회
	public void getBoard() {
		System.out.println("조회할 게시물 번호를 입력하세요.");
		int boardNum = Integer.parseInt(sc.nextLine());
		
		NoticeBoard notice = NoticeBoardDAO.getInstance().getBoard(boardNum);
		
		if (notice != null) {
			boardHit(notice.getNoticeNumber());
			System.out.println("===== 게시물 상세 조회 =====");
			System.out.println("번호 : " + notice.getNoticeNumber());
			System.out.println("제목 : " + notice.getNoticeTitle());
			System.out.println("작성자 : " + notice.getNoticeWriter());
			System.out.println("내용 : " + notice.getNoticeContent());
			System.out.println("등록일 : " + notice.getNoticeRegdate());
			System.out.println("조회수 : " + notice.getNoticeHit());
			
			nrs.getReplyList(boardNum);
			System.out.println("1. 댓글 작성 | 2. 댓글 수정 | 3. 댓글 삭제 | 4. 취소");
			int rpSelectNo = Integer.parseInt(sc.nextLine());
			if (rpSelectNo == 1) {
				nrs.writeReply(boardNum);
			} else if (rpSelectNo == 2) {
				nrs.updateReply(boardNum);
			} else if (rpSelectNo == 3) {
				nrs.deleteReply(boardNum);
			} else if (rpSelectNo == 4) {
				
			} else {
				System.out.println("잘못된 입력입니다.");
			}
			
			int selectNo;
			
			if (MemberService.memberInfo == null || !(MemberService.memberInfo.getMemberId().equals(notice.getNoticeWriter()))) {
				System.out.println("0. 뒤로 가기");
				selectNo = Integer.parseInt(sc.nextLine());
				if (selectNo == 0) {

				} else {
					System.out.println("잘못된 입력입니다.");
				} 
				
			} else {
					System.out.println("1. 게시물 수정 | 2. 게시물 삭제 | 3. 뒤로 가기");
					selectNo = Integer.parseInt(sc.nextLine());
					if (selectNo == 1) {
						updateBoard(notice);
					} else if (selectNo == 2) {
						deleteBoard(notice);
					} else if (selectNo == 3) {
						return;
					} else {
						System.out.println("잘못된 입력입니다.");
					}
					
				} 
				
		} else {
				System.out.println("해당 번호의 게시물이 없습니다.");
		}

	}
	
	// 공지사항 작성
	public void insertBoard() {
		NoticeBoard notice = new NoticeBoard();
		if (MemberService.memberInfo == null) {
			System.out.println("먼저 로그인을 해 주세요.");
		} else if(!(MemberService.memberInfo.getMemberAuth().equals("A"))) {
			System.out.println("관리자만 공지사항을 작성할 수 있습니다.");
		} else {
			System.out.println("===== 게시물 작성 =====");
			System.out.println("제목 >");
			notice.setNoticeTitle(sc.nextLine());
			notice.setNoticeWriter(MemberService.memberInfo.getMemberId());
			System.out.println("내용 >");
			notice.setNoticeContent(sc.nextLine());	
			
			int result = NoticeBoardDAO.getInstance().insertBoard(notice);
			
			if (result > 0) {
				System.out.println("게시물 작성이 완료 되었습니다.");
			} else {
				System.out.println("게시물 작성에 실패 했습니다.");
			}
		}

		
	}
	
	// 공지사항 수정
	public void updateBoard(NoticeBoard notice) {
		System.out.println("===== 게시물 수정 =====");
		System.out.println("1) 제목 변경 | 2) 내용 변경");
		int selectNo = Integer.parseInt(sc.nextLine());
		if (selectNo == 1) {
			System.out.println("새 제목>");
			notice.setNoticeTitle(sc.nextLine());
		} else if (selectNo == 2) {
			System.out.println("새 내용>");
			notice.setNoticeContent(sc.nextLine());
		}
		
		int result = NoticeBoardDAO.getInstance().updateBoard(notice, selectNo);
		
		if (result >= 1) {
			System.out.println("게시물 수정이 완료 되었습니다.");
		} else {
			System.out.println("게시물 수정에 실패 했습니다.");
		}
		
	}
	
	// 공지사항 삭제
	public void deleteBoard(NoticeBoard notice) {
		System.out.println("===== 게시물 삭제 =====");
		System.out.println("정말 삭제 하시겠습니까? (Y/N)");
		String answer = sc.nextLine();
		
		if (answer.equals("Y")) {
			NoticeBoardDAO.getInstance().deleteBoard(notice.getNoticeNumber());
			System.out.println("게시물이 삭제 되었습니다.");
		} else {
			System.out.println("게시물 작성을 취소합니다.");
		}
		
	}
	
	// 공지사항 검색
	public void searchBoard() {
		System.out.println("===== 검색할 항목을 선택하세요. =====");
		System.out.println("1. 제목+내용 | 2. 작성자");
		int selectNo = Integer.parseInt(sc.nextLine());
		System.out.println("===== 검색 단어를 입력하세요. =====");
		String searchWord = sc.nextLine();
		
		List<NoticeBoard> list = NoticeBoardDAO.getInstance().searchBoard(searchWord, selectNo);
		System.out.println("번호 | \t제목\t | \t작성자\t | \t등록일\t | 조회수");
		if (list.size() == 0) {
			System.out.println("등록된 게시물이 없습니다.");
		} else {
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getNoticeNumber() + "\t" + list.get(i).getNoticeTitle() + "\t" + list.get(i).getNoticeWriter() + "\t" + list.get(i).getNoticeRegdate() + "\t" + list.get(i).getNoticeHit());
			}
		
	}

	}
	
	// 게시판 조회수
	public void boardHit(int boardNum) {
		NoticeBoardDAO.getInstance().boardHit(boardNum);
	}
	

	
	
}
