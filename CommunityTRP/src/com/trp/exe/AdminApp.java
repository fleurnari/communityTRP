package com.trp.exe;

import java.util.Scanner;

import com.trp.member.MemberService;

public class AdminApp {
	
	Scanner sc = new Scanner(System.in);
	
	public AdminApp() {
		adminRun();
	}
	
	private void adminRun() {
		boolean flag = true;
		while(flag) {
			System.out.println("1. 게시판 접속 | 2. 회원 관리 | 3. 신고 처리 | 4. 로그아웃");
			int selectNo = Integer.parseInt(sc.nextLine());
			
			switch (selectNo) {
			case 1:
				new BoardApp();
				break;
			case 2:
				new MemManageApp();
				break;
			case 3:
				
				break;
			case 4:
				flag = false;
				MemberService.memberInfo = null;
				System.out.println("TRP에서 로그아웃 합니다.");
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
			
		}
	}

}
