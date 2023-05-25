package com.trp.exe;

import java.util.List;
import java.util.Scanner;

import com.trp.member.MemberService;
import com.trp.notice.NoticeBoard;
import com.trp.notice.NoticeBoardDAO;
import com.trp.notice.NoticeBoardService;
import com.trp.notice.NoticeReply;
import com.trp.notice.NoticeReplyService;

public class BoardApp {
	
	Scanner sc = new Scanner(System.in);
	NoticeBoardService nbs = new NoticeBoardService();
	
	public BoardApp() {
		boardRun();
	}
	
	private void boardRun() {
		boolean flag = true;
		while (flag) {
			System.out.println("1. 공지사항 | 2. 베스트 삼행시 | 3. 삼행시 게시판 | 4. 창작 유머 게시판 | 5. 주제어 글쓰기 게시판 | 6. 익명 자유 게시판 | 7. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				noticeBoardRun();
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
				
				break;
			case 6:
				
				break;
			case 7:
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
		nbs.getBoardList(page);
		while (flag) {
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
	
}
