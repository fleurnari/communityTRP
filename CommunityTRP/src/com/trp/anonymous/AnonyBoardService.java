package com.trp.anonymous;

import java.util.List;
import java.util.Scanner;

import com.trp.member.MemberService;
import com.trp.notice.NoticeBoard;
import com.trp.notice.NoticeBoardDAO;
import com.trp.report.Report;
import com.trp.report.ReportService;

public class AnonyBoardService {
	
	Scanner sc = new Scanner(System.in);
	AnonyReplyService ars = new AnonyReplyService();
	ReportService report = new ReportService();
	
	// 익명 게시판 글 목록 조회
	public void getBoardList(int page) {
		List<AnonyBoard> list = AnonyBoardDAO.getInstance().getBoardList(page);
		System.out.println("==================== 익명 게시판 ===============");
		System.out.println("번호 | \t제목\t | \t작성자\t | \t등록일\t | 조회수");
		if (list.size() == 0) {
			System.out.println("등록된 게시물이 없습니다.");
		} else {
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getBoardNumber() + "\t" + list.get(i).getBoardTitle() + "\t" + "익명의 사용자" + "\t" + list.get(i).getBoardRegdate() + "\t" + list.get(i).getBoardHit());
			}
		}
	}
	
	// 익명 게시물 상세 조회
	public void getBoard() {
		System.out.println("조회할 게시물 번호를 입력하세요.");
		int boardNum = Integer.parseInt(sc.nextLine());
		getBoardPage(boardNum);
	}
	
	// 익명 게시물 상세 보기 페이지
	public void getBoardPage(int boardNum) {
		boardHit(boardNum);
		AnonyBoard anony = AnonyBoardDAO.getInstance().getBoard(boardNum);
		if (anony != null) {
			System.out.println("===== 게시물 상세 조회 =====");
			System.out.println("번호 : " + anony.getBoardNumber());
			System.out.println("제목 : " + anony.getBoardTitle());
			System.out.println("작성자 : 익명의 사용자");
			System.out.println("내용 : " + anony.getBoardContent());
			System.out.println("등록일 : " + anony.getBoardRegdate());
			System.out.println("조회수 : " + anony.getBoardHit());
			
			ars.getReplyList(boardNum);
			
			int selectNo;
			
			if (MemberService.memberInfo == null || !(MemberService.memberInfo.getMemberId().equals(anony.getBoardWriter())) && !(MemberService.memberInfo.getMemberAuth().equals("A"))) {
				System.out.println("1. 댓글 작업 | 2. 신고하기 | 3. 뒤로 가기");
				selectNo = Integer.parseInt(sc.nextLine());
				if (selectNo == 1) {
					ars.replyWork(anony);
				} else if (selectNo == 2) {
					report.insertReport(anony);
				} else if (selectNo == 3) {
					return;
				} else {
					System.out.println("잘못된 입력입니다.");
				} 
				
			} else {
					System.out.println("1. 게시물 수정 | 2. 게시물 삭제 | 3. 댓글 작업 | 4. 뒤로 가기");
					selectNo = Integer.parseInt(sc.nextLine());
					if (selectNo == 1) {
						updateBoard(anony);
					} else if (selectNo == 2) {
						deleteBoard(anony);
					} else if (selectNo == 3) {
						ars.replyWork(anony);
					} else if (selectNo == 4) {
						return;
					} else {
						System.out.println("잘못된 입력입니다.");
					}
					
				} 
				
		} else {
				System.out.println("해당 번호의 게시물이 없습니다.");
		}
	}
	
	// 익명 작성
	public void insertBoard() {
		AnonyBoard anony = new AnonyBoard();
		if (MemberService.memberInfo == null) {
			System.out.println("먼저 로그인을 해 주세요.");
		} else {
			System.out.println("===== 게시물 작성 =====");
			System.out.println("제목 >");
			anony.setBoardTitle(sc.nextLine());
			anony.setBoardWriter(MemberService.memberInfo.getMemberId());
			System.out.println("내용 >");
			anony.setBoardContent(sc.nextLine());	
			
			int result = AnonyBoardDAO.getInstance().insertBoard(anony);
			
			if (result > 0) {
				System.out.println("게시물 작성이 완료 되었습니다.");
			} else {
				System.out.println("게시물 작성에 실패 했습니다.");
			}
		}

		
	}
	
	// 익명 수정
	public void updateBoard(AnonyBoard anony) {
		System.out.println("===== 게시물 수정 =====");
		System.out.println("1) 제목 변경 | 2) 내용 변경");
		int selectNo = Integer.parseInt(sc.nextLine());
		if (selectNo == 1) {
			System.out.println("새 제목>");
			anony.setBoardTitle(sc.nextLine());
		} else if (selectNo == 2) {
			System.out.println("새 내용>");
			anony.setBoardContent(sc.nextLine());
		}
		
		int result = AnonyBoardDAO.getInstance().updateBoard(anony, selectNo);
		
		if (result >= 1) {
			System.out.println("게시물 수정이 완료 되었습니다.");
		} else {
			System.out.println("게시물 수정에 실패 했습니다.");
		}
		
	}
	
	// 익명 삭제
	public void deleteBoard(AnonyBoard anony) {
		System.out.println("===== 게시물 삭제 =====");
		System.out.println("정말 삭제 하시겠습니까? (Y/N)");
		String answer = sc.nextLine();
		
		if (answer.equals("Y")) {
			AnonyBoardDAO.getInstance().deleteBoard(anony.getBoardNumber());
			System.out.println("게시물이 삭제 되었습니다.");
		} else {
			System.out.println("게시물 삭제에 실패 했습니다.");
		}
		
	}
	
	// 익명 검색
	public void searchBoard() {
		System.out.println("===== 검색할 항목을 선택하세요. =====");
		System.out.println("1. 제목+내용 | 2. 작성자");
		int selectNo = Integer.parseInt(sc.nextLine());
		System.out.println("===== 검색 단어를 입력하세요. =====");
		String searchWord = sc.nextLine();
		searchBoardList(searchWord, 1);
		
	}
	
	
	// 공지사항 검색 결과 보여주기
	public void searchBoardList(String searchWord, int page) {
		List<AnonyBoard> list = AnonyBoardDAO.getInstance().searchBoard(searchWord, page);
		boolean flag = true;
		
		while(flag) {
			System.out.println("===== 익명 게시판 =====");
			System.out.println("번호 | \t제목\t | \t작성자\t | \t등록일\t | 조회수");
			if (list.size() == 0) {
				System.out.println("등록된 게시물이 없습니다.");
			} else {
				for(int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i).getBoardNumber() + " " + list.get(i).getBoardTitle() + "\t" + "익명의 사용자" + "\t" + list.get(i).getBoardRegdate() + "\t" + list.get(i).getBoardHit());
				}
		
		}
			System.out.println("1. 게시물 상세 조회 | 2. 게시물 작성 | 3. 이전 페이지 | 4. 다음 페이지 | 5. 전체 목록으로 돌아가기");
			int returnList = Integer.parseInt(sc.nextLine());
			if (returnList == 1) {
				getBoard();
			} else if (returnList == 2) {
				insertBoard();
			} else if (returnList == 3) {
				page--;
				if (page <= 1) {
					page = 1;
				}
				searchBoardList(searchWord, page);
			} else if (returnList == 4) {
				page++;
				searchBoardList(searchWord, page);
			} else if (returnList == 5) {
				flag = false;
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
			
	}

	}
	
	// 게시판 조회수
	public void boardHit(int boardNum) {
		AnonyBoardDAO.getInstance().boardHit(boardNum);
	}
	
	

}
