package com.trp.exe;

import java.util.List;
import java.util.Scanner;

import com.trp.anonymous.AnonyBoardService;
import com.trp.challenge.ChalBoardService;
import com.trp.mainboard.MainBoardService;
import com.trp.member.MemberService;
import com.trp.notice.NoticeBoard;
import com.trp.notice.NoticeBoardDAO;
import com.trp.notice.NoticeBoardService;
import com.trp.notice.NoticeReply;
import com.trp.notice.NoticeReplyService;

public class BoardApp {
	
	Scanner sc = new Scanner(System.in);
	NoticeBoardService nbs = new NoticeBoardService();
	MainBoardService mbs = new MainBoardService();
	ChalBoardService cbs = new ChalBoardService();
	AnonyBoardService abs = new AnonyBoardService();
	
	public BoardApp() {
		boardRun();
	}
	
	private void boardRun() {
		boolean flag = true;
		while (flag) {
			System.out.println("1. 공지사항 | 2. 베스트 삼행시 | 3. 삼행시 게시판 | 4. 삼행시 챌린지 | 5. 익명 자유 게시판 | 6. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				noticeBoardRun();
				break;
			case 2:
				bestBoardRun();
				break;
			case 3:
				mainBoardRun();
				break;
			case 4:
				chalBoardRun();
				break;
			case 5:
				anonyBoardRun();
				break;
			case 6:
				flag = false;
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}
	
	private void noticeBoardRun() {
		boolean flag = true;
		int page = 1;
		while (flag) {
			nbs.getBoardList(page);
			System.out.println("1. 게시물 상세 조회 | 2. 게시물 검색 | 3. 게시물 작성 | 4. 이전 페이지 | 5. 다음 페이지 | 6. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				nbs.getBoard();
				break;
			case 2:
				nbs.searchBoard();
				break;
			case 3:
				nbs.insertBoard();
				break;
			case 4:
				page--;
				if (page <= 1) {
					page = 1;
				}
				nbs.getBoardList(page);
				break;
			case 5:
				page++;
				nbs.getBoardList(page);
				break;
			case 6:
				flag = false;
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}
	
	private void bestBoardRun() {
		boolean flag = true;
		while (flag) {
			System.out.println("1. 창작 삼행시 베스트 | 2. 삼행시 챌린지 베스트 | 3. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				bestMainRun();
				break;
			case 2:
				bestChalRun();
				break;
			case 3:
				flag = false;
				break;
			}
		}
		
	}
	
	private void bestMainRun() {
		boolean flag = true;
		while (flag) {
			mbs.getBestMainList();
			System.out.println("1. 게시물 상세 보기 | 2. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				mbs.getBoard();
				break;
			case 2:
				flag = false;
				break;
			}
		}
	}
	
	private void bestChalRun() {
		boolean flag = true;
		while (flag) {
			cbs.getBestChalList();
			System.out.println("1. 게시물 상세 보기 | 2. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				cbs.getBoard();
				break;
			case 2:
				flag = false;
				break;
			}
		}
	}
	

	
	private void mainBoardRun() {
		boolean flag = true;
		int page = 1;
		while (flag) {
			mbs.getBoardList(page);
			System.out.println("1. 게시물 상세 조회 | 2. 게시물 검색 | 3. 게시물 작성 | 4. 이전 페이지 | 5. 다음 페이지 | 6. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				mbs.getBoard();
				break;
			case 2:
				mbs.searchBoard();
				break;
			case 3:
				mbs.insertBoard();
				break;
			case 4:
				page--;
				if (page <= 1) {
					page = 1;
				}
				mbs.getBoardList(page);
				break;
			case 5:
				page++;
				mbs.getBoardList(page);
				break;
			case 6:
				flag = false;
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}
	
	private void chalBoardRun() {
		boolean flag = true;
		int page = 1;
		while (flag) {
			cbs.getBoardList(page);
			System.out.println("1. 게시물 상세 조회 | 2. 게시물 검색 | 3. 게시물 작성 | 4. 이전 페이지 | 5. 다음 페이지 | 6. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				cbs.getBoard();
				break;
			case 2:
				cbs.searchBoard();
				break;
			case 3:
				cbs.insertBoard();
				break;
			case 4:
				page--;
				if (page <= 1) {
					page = 1;
				}
				cbs.getBoardList(page);
				break;
			case 5:
				page++;
				cbs.getBoardList(page);
				break;
			case 6:
				flag = false;
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}
	
	
	private void anonyBoardRun() {
		boolean flag = true;
		int page = 1;
		while (flag) {
			abs.getBoardList(page);
			System.out.println("1. 게시물 상세 조회 | 2. 게시물 검색 | 3. 게시물 작성 | 4. 이전 페이지 | 5. 다음 페이지 | 6. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				abs.getBoard();
				break;
			case 2:
				abs.searchBoard();
				break;
			case 3:
				abs.insertBoard();
				break;
			case 4:
				page--;
				if (page <= 1) {
					page = 1;
				}
				abs.getBoardList(page);
				break;
			case 5:
				page++;
				abs.getBoardList(page);
				break;
			case 6:
				flag = false;
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}
	
	
}
