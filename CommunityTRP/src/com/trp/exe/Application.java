package com.trp.exe;

import java.util.Scanner;

import com.trp.member.MemberService;

public class Application {

	Scanner sc = new Scanner(System.in);
	MemberService ms = new MemberService();
	
	public Application() {
		run();
	}
	
	private void run() {
		while(true) {
			if (MemberService.memberInfo == null) {
				System.out.println("1. 게시판 접속 | 2. 로그인 | 3. 회원가입 | 4. 종료");
				int menu = Integer.parseInt(sc.nextLine());
				if (menu == 1) {
					new BoardApp();
				} else if (menu == 2) {
					ms.login();
				} else if (menu == 3) {
					ms.joinMember();
				} else if (menu == 4) {
					System.out.println("TRP를 이용해 주셔서 감사합니다. 다음에 또 만나요😊😊");
					break;
				} else {
					System.out.println("잘못된 입력입니다.");
				}
			} else if (MemberService.memberInfo != null) {
				if (MemberService.memberInfo.getMemberAuth().equals("N")) {
					new MemberApp();
				} else if (MemberService.memberInfo.getMemberAuth().equals("A")) {
					new AdminApp();
				}
			}
			
		}
		
		
	}
}
