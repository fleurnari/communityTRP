package com.trp.mainboard;

import java.util.List;
import java.util.Scanner;

import com.trp.member.MemberService;


public class MainBoardService {
	
	Scanner sc = new Scanner(System.in);
	MainReplyService mrs = new MainReplyService();

	// 삼행시 게시판 글 목록 조회
	public void getBoardList(int page) {
		List<MainBoard> list = MainBoardDAO.getInstance().getBoardList(page);
		System.out.println("번호 | \t제목\t | \t작성자\t | \t등록일\t | 조회수 | 추천수");
		if (list.size() == 0) {
			System.out.println("등록된 게시물이 없습니다.");
		} else {
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getBoardNumber() + "\t" + list.get(i).getBoardTitle() + "\t" + list.get(i).getBoardWriter() + "\t" + list.get(i).getBoardRegdate() + "\t" + list.get(i).getBoardHit() + "\t" + list.get(i).getBoardRecomm());
			}
		}
	}
	
	// 삼행시 게시물 상세 조회
	public void getBoard() {
		System.out.println("조회할 게시물 번호를 입력하세요.");
		int boardNum = Integer.parseInt(sc.nextLine());
		boardHit(boardNum);
		MainBoard main = MainBoardDAO.getInstance().getBoard(boardNum);
		
		
		if (main != null) {
			System.out.println("===== 게시물 상세 조회 =====");
			System.out.println("번호 : " + main.getBoardNumber());
			System.out.println("제목 : " + main.getBoardTitle());
			System.out.println("작성자 : " + main.getBoardWriter());
			System.out.println("내용 : " + main.getBoardContent());
			System.out.println("등록일 : " + main.getBoardRegdate());
			System.out.println("조회수 : " + main.getBoardHit());
			System.out.println("추천수 : " + main.getBoardRecomm());
			
			mrs.getReplyList(boardNum);
			System.out.println("1. 댓글 작성 | 2. 댓글 수정 | 3. 댓글 삭제 | 4. 댓글 작업 취소");
			int rpSelectNo = Integer.parseInt(sc.nextLine());
			if (rpSelectNo == 1) {
				mrs.writeReply(boardNum);
			} else if (rpSelectNo == 2) {
				mrs.updateReply(boardNum);
			} else if (rpSelectNo == 3) {
				mrs.deleteReply(boardNum);
			} else if (rpSelectNo == 4) {
				
			} else {
				System.out.println("잘못된 입력입니다.");
			}
			
			int selectNo;
			
			if (MemberService.memberInfo == null || !(MemberService.memberInfo.getMemberId().equals(main.getBoardWriter())) && !(MemberService.memberInfo.getMemberAuth().equals("A"))) {
				System.out.println("1. 추천 | 2. 뒤로 가기");
				selectNo = Integer.parseInt(sc.nextLine());
				if (selectNo == 1) {
					boardRecomm(main);
				} else if (selectNo == 2) {
					return;
				} else {
					System.out.println("잘못된 입력입니다.");
				} 
				
			} else {
					System.out.println("1. 게시물 수정 | 2. 게시물 삭제 | 3. 추천 | 4. 뒤로 가기");
					selectNo = Integer.parseInt(sc.nextLine());
					if (selectNo == 1) {
						updateBoard(main);
					} else if (selectNo == 2) {
						deleteBoard(main);
					} else if (selectNo == 3) {
						boardRecomm(main);;
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
	
	// 삼행시 작성
	public void insertBoard() {
		MainBoard main = new MainBoard();
		if (MemberService.memberInfo == null) {
			System.out.println("먼저 로그인을 해 주세요.");
		} else {
			System.out.println("===== 게시물 작성 =====");
			System.out.println("제목 >");
			main.setBoardTitle(sc.nextLine());
			main.setBoardWriter(MemberService.memberInfo.getMemberId());
			System.out.println("내용 >");
			main.setBoardContent(sc.nextLine());	
			
			int result = MainBoardDAO.getInstance().insertBoard(main);
			
			if (result > 0) {
				System.out.println("게시물 작성이 완료 되었습니다.");
			} else {
				System.out.println("게시물 작성에 실패 했습니다.");
			}
		}

		
	}
	
	// 삼행시 수정
	public void updateBoard(MainBoard main) {
		System.out.println("===== 게시물 수정 =====");
		System.out.println("1) 제목 변경 | 2) 내용 변경");
		int selectNo = Integer.parseInt(sc.nextLine());
		if (selectNo == 1) {
			System.out.println("새 제목>");
			main.setBoardTitle(sc.nextLine());
		} else if (selectNo == 2) {
			System.out.println("새 내용>");
			main.setBoardContent(sc.nextLine());
		}
		
		int result = MainBoardDAO.getInstance().updateBoard(main, selectNo);
		
		if (result >= 1) {
			System.out.println("게시물 수정이 완료 되었습니다.");
		} else {
			System.out.println("게시물 수정에 실패 했습니다.");
		}
		
	}
	
	// 삼행시 삭제
	public void deleteBoard(MainBoard main) {
		System.out.println("===== 게시물 삭제 =====");
		System.out.println("정말 삭제 하시겠습니까? (Y/N)");
		String answer = sc.nextLine();
		
		if (answer.equals("Y")) {
			MainBoardDAO.getInstance().deleteBoard(main.getBoardNumber());
			System.out.println("게시물이 삭제 되었습니다.");
		} else {
			System.out.println("게시물 작성을 취소합니다.");
		}
		
	}
	
	// 삼행시 검색
	public void searchBoard() {
		System.out.println("===== 검색할 항목을 선택하세요. =====");
		System.out.println("1. 제목+내용 | 2. 작성자");
		int selectNo = Integer.parseInt(sc.nextLine());
		System.out.println("===== 검색 단어를 입력하세요. =====");
		String searchWord = sc.nextLine();
		
		List<MainBoard> list = MainBoardDAO.getInstance().searchBoard(searchWord, selectNo);
		System.out.println("번호 | \t제목\t | \t작성자\t | \t등록일\t | 조회수 | 추천수");
		if (list.size() == 0) {
			System.out.println("등록된 게시물이 없습니다.");
		} else {
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getBoardNumber() + "\t" + list.get(i).getBoardTitle() + "\t" + list.get(i).getBoardWriter() + "\t" + list.get(i).getBoardRegdate() + "\t" + list.get(i).getBoardHit() + "\t" + list.get(i).getBoardRecomm());
			}
		
	}

	}
	
	// 게시판 조회수
	public void boardHit(int boardNum) {
		MainBoardDAO.getInstance().boardHit(boardNum);
	}
	
	
	// 게시판 추천수
	public void boardRecomm(MainBoard main) {
		if (MemberService.memberInfo == null) {
			System.out.println("로그인한 사용자만 추천을 할 수 있습니다."); 
				
	    } else if (MemberService.memberInfo.getMemberId().equals(main.getBoardWriter())) {
	    	System.out.println("자신이 작성한 글은 추천할 수 없습니다.");
		
		} else {
			MainBoardDAO.getInstance().boardRecomm(main.getBoardNumber());
			System.out.println("해당 게시물을 추천 했습니다.");
		}
	}
	
	
}
