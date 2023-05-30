package com.trp.member;

import java.util.List;
import java.util.Scanner;

public class MemberService {
	
	public static Member memberInfo = null;
	Scanner sc = new Scanner(System.in);
	
	// 로그인
	public void login() {
		System.out.println("===== 로그인 =====");
		System.out.println("ID>");
		String id = sc.nextLine();
		System.out.println("PW>");
		String pw = sc.nextLine();
		
		Member member = MemberDAO.getInstance().login(id);
		
		if (member != null) {
			if (member.getMemberPw().equals(pw)) {
				System.out.println("로그인 성공");
				memberInfo = member;
			} else {
				System.out.println("비밀번호가 일치하지 않습니다");
			}
		} else {
			System.out.println("존재하지 않는 아이디입니다");
		}
		
	}
	
	// 회원 가입
	public void joinMember() {
		Member member = new Member();
		System.out.println("===== 회원가입 =====");
		System.out.println("ID>");
		String id = sc.nextLine();
		if (MemberDAO.getInstance().login(id) != null) {
			System.out.println("이미 존재하는 아이디입니다");
		} else {
			member.setMemberId(id);
			System.out.println("PW>");
			member.setMemberPw(sc.nextLine());
			System.out.println("NAME>");
			member.setMemberName(sc.nextLine());
			System.out.println("EMAIL>");
			member.setMemberEmail(sc.nextLine());
			
			int result = MemberDAO.getInstance().joinMember(member);
			
			if (result > 0) {
				System.out.println("회원 가입이 완료 되었습니다");
			} else {
				System.out.println("회원 가입에 실패 했습니다");
			}
		}
			
		
	}
	
	// 회원 정보 수정
	public void modifyMember() {
		System.out.println("===== 회원 정보 수정 =====");
		System.out.println("1) 비밀번호 변경 | 2) 이름 변경 | 3) 이메일 변경");
		Member member = MemberService.memberInfo;
		int selectNo = Integer.parseInt(sc.nextLine());
		
		if (selectNo == 1) {
			System.out.println("기존 비밀번호를 입력하세요.");
			String pw = sc.nextLine();
			if (member.getMemberPw().equals(pw)) {
				System.out.println("새 비밀번호>");
				member.setMemberPw(sc.nextLine());
			} else {
				System.out.println("비밀번호가 틀렸습니다.");
				return;
			}
		} else if (selectNo == 2) {
			System.out.println("새 이름>");
			member.setMemberName(sc.nextLine());
		} else if (selectNo == 3) {
			System.out.println("새 이메일 주소>");
			member.setMemberEmail(sc.nextLine());
		} 
		
		int result = MemberDAO.getInstance().modifyMember(member, selectNo);
		
		if (result == 1) {
			System.out.println("회원 정보 수정이 완료 되었습니다.");
		} else {
			System.out.println("회원 정보 수정에 실패 했습니다.");
		}
		
	}
	
	// 회원 탈퇴
	public void deleteMember() {
		System.out.println("===== 회원 탈퇴 =====");
		System.out.println("회원 탈퇴를 위해 비밀번호를 다시 한 번 입력해 주세요.");
		String id = MemberService.memberInfo.getMemberId();
		String pw = sc.nextLine();
		if (MemberService.memberInfo.getMemberPw().equals(pw)) {
			System.out.println("정말 TRP를 떠나실 건가요? 1. 예 | 2. 아니오");
			int answer = Integer.parseInt(sc.nextLine());
			if (answer == 1) {
				MemberDAO.getInstance().deleteMember(id);
				System.out.println("회원 탈퇴가 완료 되었습니다. 다음에 다시 가입해 주세요😥😥");
				MemberService.memberInfo = null;
			} else {
				System.out.println("TRP를 계속 이용해 주셔서 감사합니다. 앞으로 더 잘 할게요🥰🥰");
			}
		} else {
			System.out.println("비밀번호가 틀렸습니다.");
		}
		
	}
	
	// 전체 회원 조회
	public void getMemberList() {
		List<Member> list = MemberDAO.getInstance().getMemberList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("==================================");
			System.out.println("ID : " + list.get(i).getMemberId());
			System.out.println("이름 : " + list.get(i).getMemberName());
			System.out.println("가입일 : " + list.get(i).getMemberRegdate());
			System.out.println("회원 분류 : " + (list.get(i).getMemberAuth().equals("N") ? "일반회원" : "관리자"));		
		}
	}
	
	// 회원 검색
	public void getMember() {
		System.out.println("검색할 회원 아이디를 입력하세요.");
		String id = sc.nextLine();
		
		Member member = MemberDAO.getInstance().getMember(id);
		
		if (member != null) {
			System.out.println("ID : " + member.getMemberId());
			System.out.println("이름 : " + member.getMemberName());
			System.out.println("가입일 : " + member.getMemberRegdate());
			System.out.println("회원 분류 : " + (member.getMemberAuth().equals("N") ? "일반회원" : "관리자"));		
		} else {
			System.out.println("검색된 회원이 없습니다.");
		}
		
	}
	
	// admin용 회원 정보 수정
	public void adminModifyMember() {
		System.out.println("===== 회원 정보 수정 =====");
		System.out.println("정보를 수정할 회원의 아이디를 입력하세요.");
		String id = sc.nextLine();
		Member member = MemberDAO.getInstance().getMember(id);
		if (member == null) {
			System.out.println("존재 하지 않는 회원입니다.");
		} else {
			System.out.println("1) ID 변경 | 2) 비밀번호 변경 | 3. 이름 변경 | 4. 이메일 변경 | 5. 권한 변경");
			int selectNo = Integer.parseInt(sc.nextLine());
			
			if (selectNo == 1) {
				System.out.println("새 ID>");
				member.setMemberId(sc.nextLine());
			} else if (selectNo == 2) {
				System.out.println("새 비밀번호>");
				member.setMemberPw(sc.nextLine());
			} else if (selectNo == 3) {
				System.out.println("새 이름>");
				member.setMemberName(sc.nextLine());
			} else if (selectNo == 4) {
				System.out.println("새 이메일>");
				member.setMemberEmail(sc.nextLine());
			} else if (selectNo == 5) {
				if (member.getMemberAuth().equals("N")) {
					member.setMemberAuth("A");
					System.out.println("일반 회원에서 관리자로 변경 되었습니다.");
				} else {
					member.setMemberAuth("N");
					System.out.println("관리자에서 일반 회원으로 변경 되었습니다.");
				}
			} else {
				System.out.println("잘못된 입력입니다.");
				
			}
			
			int result = MemberDAO.getInstance().adminModifyMember(member, id, selectNo);
			
			if (result == 1) {
				System.out.println("회원 정보 수정이 완료 되었습니다.");
			} else {
				System.out.println("회원 정보 수정에 실패 했습니다.");
			}
		}
		
	}
	
	// admin용 회원 삭제
	public void adminDeleteMember() {
		System.out.println("탈퇴 시킬 회원의 아이디를 입력하세요.");
		System.out.println("ID>");
		adminDeleteMem(sc.nextLine());
	}
	
	
	public void adminDeleteMem(String id) {
		System.out.println("정말 " + id + " 회원을 탈퇴 시킬까요? 1. 예 | 2. 아니오");
		int delete = Integer.parseInt(sc.nextLine());
		if (delete == 1) {
				MemberDAO.getInstance().deleteMember(id);
				System.out.println("해당 회원의 탈퇴가 완료 되었습니다.");
		} else {
			System.out.println("강제 탈퇴를 취소 합니다.");
		}
	}
}



