package com.trp.report;

import java.util.List;
import java.util.Scanner;

import com.trp.anonymous.AnonyBoard;
import com.trp.anonymous.AnonyBoardService;
import com.trp.anonymous.AnonyReply;
import com.trp.anonymous.AnonyReplyDAO;
import com.trp.member.MemberDAO;
import com.trp.member.MemberService;

public class ReportService {
	
	Scanner sc = new Scanner(System.in);
	MemberService ms = new MemberService();
	
	// 게시물 신고
	public void insertReport(AnonyBoard anony) {
		Report report = new Report();
		if (MemberService.memberInfo == null) {
			System.out.println("먼저 로그인을 해 주세요.");
		} else {
			System.out.println("===== 신고하기 =====");
			report.setComplainId(MemberService.memberInfo.getMemberId());
			report.setSuspectId(anony.getBoardWriter());
			report.setReportContent(anony.getBoardContent());
			System.out.println("신고 사유를 입력해 주세요.");
			report.setReportReason(sc.nextLine());
			report.setBoardNumber(anony.getBoardNumber());
			
			int result = ReportDAO.getInstance().insertReport(report);
			
			if (result > 0) {
				System.out.println("회원 신고가 완료 되었습니다.");
			} else {
				System.out.println("회원 신고에 실패 했습니다.");
			}
		}
		
	}
	
	
	
	// 댓글 신고
	public void replyReport(AnonyBoard anony) {
		AnonyReply reply = new AnonyReply();
		reply.setBoardNumber(anony.getBoardNumber());
		System.out.println("===== 댓글 신고 =====");
		System.out.println("신고할 댓글 번호를 입력해 주세요.");
		int replyNum = Integer.parseInt(sc.nextLine());
		reply = AnonyReplyDAO.getInstance().getReply(replyNum);
		
		if (MemberService.memberInfo == null) {
			System.out.println("먼저 로그인을 해 주세요.");
		} else {
			System.out.println("===== 신고하기 =====");
			Report report = new Report();
			report.setComplainId(MemberService.memberInfo.getMemberId());
			report.setSuspectId(reply.getReplyWriter());
			report.setReportContent(reply.getReplyContent());
			System.out.println("신고 사유를 입력해 주세요.");
			report.setReportReason(sc.nextLine());
			report.setBoardNumber(anony.getBoardNumber());
			
			int result = ReportDAO.getInstance().replyReport(report);
			
			if (result > 0) {
				System.out.println("회원 신고가 완료 되었습니다.");
			} else {
				System.out.println("회원 신고에 실패 했습니다.");
			}
		}
		
	}
	
	
	// admin용 신고 목록 조회
	public void getReportList() {
		List<Report> list = ReportDAO.getInstance().getReportList();
		System.out.println("번호 | \t신고 작성자\t | \t신고 분류\t | 등록일");
		if (list.size() == 0) {
			System.out.println("처리 해야할 신고 목록이 없습니다.");
		} else {
			for(int i = 0; i < list.size(); i++) {
				System.out.println(list.get(i).getReportNumber() + "\t" + list.get(i).getComplainId() + "\t" + list.get(i).getReportType() + "\t" + list.get(i).getReportRegdate());
			}
		}
	}
	
	// admin용 신고 목록 상세 조회
	public void getReport() {
		AnonyBoardService abs = new AnonyBoardService();
		System.out.println("조회할 게시물 번호를 입력 하세요.");
		int boardNum = Integer.parseInt(sc.nextLine());
		Report report = ReportDAO.getInstance().getReport(boardNum);
		
		if (report != null) {
			System.out.println("===== 신고 상세 조회 =====");
			System.out.println("번호 : " + report.getReportNumber());
			System.out.println("신고 작성자 : " + report.getComplainId());
			System.out.println("신고 대상자 : " + report.getSuspectId());
			System.out.println("신고 분류 : " + report.getReportType());
			System.out.println("신고 대상 내용 : " + report.getReportContent());
			System.out.println("신고 사유 : " + report.getReportReason());
			System.out.println("등록일 : " + report.getReportRegdate());
			
			System.out.println("1. 해당 게시물로 이동 | 2. 신고 대상자 강제 탈퇴 | 3. 신고 완료 처리 | 4. 뒤로 가기");
			int selectNo = Integer.parseInt(sc.nextLine());
			if (selectNo == 1) {
				abs.getBoardPage(report.getBoardNumber());
			} else if (selectNo == 2) {
				ms.adminDeleteMem(report.getSuspectId());
			} else if (selectNo == 3) {
				deleteReport(report.getReportNumber());
			} else if (selectNo == 4) {

			} else {
				System.out.println("잘못된 입력입니다.");
			}
			
			
		} else {
			System.out.println("해당 번호의 신고 게시물이 없습니다.");
		}
	}
	
	// 신고 완료 처리
	public void deleteReport(int boardNum) {
		System.out.println("해당 신고를 완료 처리 하시겠습니까? 1. 예 | 2. 아니오"); {
			int answer = Integer.parseInt(sc.nextLine());
			if (answer == 1) {
				ReportDAO.getInstance().deleteReport(boardNum);
				System.out.println("해당 신고를 완료 처리 했습니다.");
			} else {
				System.out.println("신고 완료 처리를 취소 합니다.");
			}
		}
	}
	

	
	
	

}
