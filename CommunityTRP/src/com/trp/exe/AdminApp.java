package com.trp.exe;

import java.util.Scanner;

import com.trp.member.MemberService;
import com.trp.report.ReportService;

public class AdminApp {
	
	Scanner sc = new Scanner(System.in);
	MemberService ms = new MemberService();
	ReportService rs = new ReportService();
	
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
				memManageRun();
				break;
			case 3:
				reportRun();
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
	
	private void reportRun() {
		boolean flag = true;
		while (flag) {
			rs.getReportList();
			System.out.println("1. 신고 상세 보기 | 2. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			switch (selectNo) {
			case 1:
				rs.getReport();
				break;
			case 2:
				flag = false;
				break;
			default:
				System.out.println("잘못된 입력입니다.");
				break;
			}
		}
	}
	

}
