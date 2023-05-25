package com.trp.exe;

import java.util.Scanner;

import com.trp.member.MemberService;

public class MemManageApp {
	Scanner sc = new Scanner(System.in);
	MemberService ms = new MemberService();
	
	public MemManageApp() {
		memManageRun();
	}
	
	private void memManageRun() {
		boolean flag = true;
		while (flag) {
			System.out.println("1. 전체 회원 조회 | 2. 회원 검색 | 3. 회원 정보 수정 | 4. 회원 삭제 | 5. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				ms.getMemberList();
				break;
			case 2:
				ms.getMember();
				break;
			case 3:
				ms.adminModifyMember();
				break;
			case 4:
				ms.adminDeleteMember();
				break;
			case 5:
				flag = false;
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}

}
