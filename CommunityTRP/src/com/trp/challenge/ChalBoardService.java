package com.trp.challenge;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.trp.mainboard.MainBoard;
import com.trp.mainboard.MainBoardDAO;
import com.trp.member.MemberService;

public class ChalBoardService {
	
	Scanner sc = new Scanner(System.in);
	ChalReplyService crs = new ChalReplyService();

	// 삼행시 게시판 글 목록 조회
	public void getBoardList(int page) {
		List<ChalBoard> list = ChalBoardDAO.getInstance().getBoardList(page);
		System.out.println("================= 삼행시 챌린지 게시판 =================");
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
		ChalBoard chal = ChalBoardDAO.getInstance().getBoard(boardNum);
		
		
		if (chal != null) {
			System.out.println("===== 게시물 상세 조회 =====");
			System.out.println("번호 : " + chal.getBoardNumber());
			System.out.println("제목 : " + chal.getBoardTitle());
			System.out.println("작성자 : " + chal.getBoardWriter());
			System.out.println(chal.getBoardTitle().charAt(0) + " : "  + chal.getBoardContent1());
			System.out.println(chal.getBoardTitle().charAt(1) + " : " + chal.getBoardContent2());
			System.out.println(chal.getBoardTitle().charAt(2) + " : " + chal.getBoardContent3());
			System.out.println("등록일 : " + chal.getBoardRegdate());
			System.out.println("조회수 : " + chal.getBoardHit());
			System.out.println("추천수 : " + chal.getBoardRecomm());
			
			crs.getReplyList(boardNum);
			
			int selectNo;
			
			if (MemberService.memberInfo == null || !(MemberService.memberInfo.getMemberId().equals(chal.getBoardWriter())) && !(MemberService.memberInfo.getMemberAuth().equals("A"))) {
				System.out.println("1. 추천 | 2. 댓글 작업 | 3. 뒤로 가기");
				selectNo = Integer.parseInt(sc.nextLine());
				if (selectNo == 1) {
					boardRecomm(chal);
				} else if (selectNo == 2){
					crs.replyWork(boardNum);
				} else if (selectNo == 3) {
					return;
				} else {
					System.out.println("잘못된 입력입니다.");
				} 
				
			} else {
					System.out.println("1. 게시물 수정 | 2. 게시물 삭제 | 3. 추천 | 4. 댓글 작업 | 5. 뒤로 가기");
					selectNo = Integer.parseInt(sc.nextLine());
					if (selectNo == 1) {
						updateBoard(chal);
					} else if (selectNo == 2) {
						deleteBoard(chal);
					} else if (selectNo == 3) {
						boardRecomm(chal);;
					} else if (selectNo == 4) {
						crs.replyWork(boardNum);
					} else if (selectNo == 5) {
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
		
		String[] trpWords = {"고양이", "강아지", "마라탕", "컴퓨터", "미용실", "신발장", "동영상", "통나무", "도자기", "개나리"};
		Random random = new Random();
		String randomWord = trpWords[random.nextInt(trpWords.length)];
		
		ChalBoard chal = new ChalBoard();
		if (MemberService.memberInfo == null) {
			System.out.println("먼저 로그인을 해 주세요.");
		} else {
			System.out.println("===== 게시물 작성 =====");
			System.out.println("제시된 단어는 " + randomWord + "입니다.");
			chal.setBoardTitle(randomWord);
			chal.setBoardWriter(MemberService.memberInfo.getMemberId());
			while (true) {
				System.out.println("첫번째 행 > '" + randomWord.charAt(0) + "'로 시작하는 행을 입력해 주세요.");
				String content1 = sc.nextLine();
				if ((randomWord.substring(0,1).equals(content1.substring(0,1)))) {
					chal.setBoardContent1(content1);
					break;
				} else {
					System.out.println(randomWord.charAt(0) + "로 시작하는 행을 입력해 주세요.");
				}
			}
			
			while (true) {
				System.out.println("두번째 행 > '" + randomWord.charAt(1) + "'로 시작하는 행을 입력해 주세요.");
				String content2 = sc.nextLine();
				if ((randomWord.substring(1,2).equals(content2.substring(0,1)))) {
					chal.setBoardContent2(content2);
					break;
				} else {
					System.out.println(randomWord.charAt(1) + "로 시작하는 행을 입력해 주세요.");
				}
			}
			
			
			while (true) {
				System.out.println("세번째 행 > '" + randomWord.charAt(2) + "'로 시작하는 행을 입력해 주세요.");
				String content3 = sc.nextLine();
				if ((randomWord.substring(2).equals(content3.substring(0,1)))) {
					chal.setBoardContent3(content3);
					break;
				} else {
					System.out.println(randomWord.charAt(2) + "로 시작하는 행을 입력해 주세요.");
				}
			}
			
			int result = ChalBoardDAO.getInstance().insertBoard(chal);
			
			if (result > 0) {
				System.out.println("게시물 작성에 성공 했습니다.");
			} else {
				System.out.println("게시물 작성에 실패 했습니다.");
			}
			
			
		}

	}

		
	// 삼행시 수정
	public void updateBoard(ChalBoard chal) {
		System.out.println("===== 게시물 수정 =====");
		System.out.println("1) 첫번째 행 수정 | 2) 두번째 행 수정 | 3) 세번째 행 수정");
		int selectNo = Integer.parseInt(sc.nextLine());
		if (selectNo == 1) {
			while(true) {
				System.out.println("수정할 첫번째 행의 내용을 입력하세요.");
				String content1 = sc.nextLine();
				if (chal.getBoardTitle().substring(0,1).equals(content1.substring(0,1))) {
					chal.setBoardContent1(content1);
					break;
				} else {
					System.out.println(chal.getBoardTitle().charAt(0) + "로 시작하는 행을 입력해야 합니다.");
				}
			}
			
		} else if (selectNo == 2) {
			while(true) {
				System.out.println("수정할 두번째 행의 내용을 입력하세요.");
				String content2 = sc.nextLine();
				if (chal.getBoardTitle().substring(1,2).equals(content2.substring(0,1))) {
					chal.setBoardContent2(content2);
					break;
				} else {
					System.out.println(chal.getBoardTitle().charAt(1) + "로 시작하는 행을 입력해야 합니다.");
				}
			} 
		} else if (selectNo == 3) {
			while(true) {
				System.out.println("수정할 세번째 행의 내용을 입력하세요.");
				String content3 = sc.nextLine();
				if (chal.getBoardTitle().substring(2).equals(content3.substring(0,1))) {
					chal.setBoardContent3(content3);
					break;
				} else {
					System.out.println(chal.getBoardTitle().charAt(2) + "로 시작하는 행을 입력해야 합니다.");
				}
			}	
		}
		
		int result = ChalBoardDAO.getInstance().updateBoard(chal, selectNo);
		
		if (result >= 1) {
			System.out.println("게시물 수정이 완료 되었습니다.");
		} else {
			System.out.println("게시물 수정에 실패 했습니다.");
		}
		
	}
	
	// 삼행시 삭제
	public void deleteBoard(ChalBoard chal) {
		System.out.println("===== 게시물 삭제 =====");
		System.out.println("정말 삭제 하시겠습니까? (Y/N)");
		String answer = sc.nextLine();
		
		if (answer.equals("Y")) {
			ChalBoardDAO.getInstance().deleteBoard(chal.getBoardNumber());
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
			searchBoardList(searchWord, selectNo, 1);
			
		}
		
	// 삼행시 검색 결과 보여주기
	public void searchBoardList(String searchWord, int selectNo, int page) {
		List<ChalBoard> list = ChalBoardDAO.getInstance().searchBoard(searchWord, selectNo, page);
		boolean flag = true;
		
		while(flag) {
			System.out.println("번호 | \t제목\t | \t작성자\t | \t등록일\t | 조회수 | 추천수");
			if (list.size() == 0) {
				System.out.println("등록된 게시물이 없습니다.");
			} else {
				for(int i = 0; i < list.size(); i++) {
					System.out.println(list.get(i).getBoardNumber() + "\t" + list.get(i).getBoardTitle() + "\t" + list.get(i).getBoardWriter() + "\t" + list.get(i).getBoardRegdate() + "\t" + list.get(i).getBoardHit() + list.get(i).getBoardRecomm());
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
				searchBoardList(searchWord, selectNo, page);
			} else if (returnList == 4) {
				page++;
				searchBoardList(searchWord, selectNo, page);
			} else if (returnList == 5) {
				flag = false;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
			
		}

		}
	
	// 게시판 조회수
	public void boardHit(int boardNum) {
		ChalBoardDAO.getInstance().boardHit(boardNum);
	}
	
	
	// 게시판 추천수
	public void boardRecomm(ChalBoard chal) {
		if (MemberService.memberInfo == null) {
			System.out.println("로그인한 사용자만 추천을 할 수 있습니다."); 
				
	    } else if (MemberService.memberInfo.getMemberId().equals(chal.getBoardWriter())) {
	    	System.out.println("자신이 작성한 글은 추천할 수 없습니다.");
		
		} else {
			ChalBoardDAO.getInstance().boardRecomm(chal.getBoardNumber());
			System.out.println("해당 게시물을 추천 했습니다.");
		}
	}
	
	// 베스트 게시판 글 목록 조회
	public void getBestChalList() {
		List<ChalBoard> list = ChalBoardDAO.getInstance().getBestChalList();
		System.out.println("==================== 베스트 게시판 - 삼행시 챌린지 부문 ====================");
		System.out.println("추천수 상위 10개의 게시물이 표시 됩니다. (추천수가 같을 시 조회수 순서대로 정렬 됩니다.)");
		System.out.println("순위 | 번호 | \t제목\t | \t작성자\t | \t등록일\t | 조회수 | 추천수");
		if (list.size() == 0) {
			System.out.println("등록된 게시물이 없습니다.");
		} else {
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getRanking() + "\t" + list.get(i).getBoardNumber() + "\t" + list.get(i).getBoardTitle() + "\t" + list.get(i).getBoardWriter() + "\t" + list.get(i).getBoardRegdate() + "\t" + list.get(i).getBoardHit() + "\t" + list.get(i).getBoardRecomm());
			}
		}
	}
	

}
