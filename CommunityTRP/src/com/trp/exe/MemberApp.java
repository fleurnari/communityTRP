package com.trp.exe;

import java.util.Scanner;

import com.trp.member.MemberDAO;
import com.trp.member.MemberService;

public class MemberApp {
	
	Scanner sc = new Scanner(System.in); 
	MemberService ms = new MemberService();
	
	public MemberApp() {
		memberRun();
	}
	
	private void memberRun() {
		boolean flag = true;
		while (flag) {
			System.out.println("1. 게시판 접속 | 2. 회원 정보 수정 | 3. 회원 탈퇴 | 4. 로그아웃");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				new BoardApp();
				break;
			case 2:
				ms.modifyMember();
				break;
			case 3:
				ms.deleteMember();
				if (MemberService.memberInfo == null) {
					flag = false;
				}
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
