package com.trp.member;

import java.util.ArrayList;
import java.util.List;

import com.trp.common.DAO;

public class MemberDAO extends DAO {
	
	private static MemberDAO memberDao = null;
	
	private MemberDAO() {
		
	}
	
	public static MemberDAO getInstance() {
		if (memberDao == null) {
			memberDao = new MemberDAO();
		}
		return memberDao;
	}
	
	// 로그인
	public Member login(String id) {
		Member member = null;
		try {
			conn();
			String sql = "SELECT * FROM trp_member WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				member = new Member();
				member.setMemberId(id);
				member.setMemberPw(rs.getString("member_pw"));
				member.setMemberName(rs.getString("member_name"));
				member.setMemberRegdate(rs.getDate("member_regdate"));
				member.setMemberAuth(rs.getString("member_auth"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return member;
	}
	
	// 회원 가입
	public int joinMember(Member member) {
		int result = 0;
		
		try {
			conn();
			String sql = "INSERT INTO trp_member VALUES (?,?,?,?,sysdate,'N')";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberEmail());
			result = pstmt.executeUpdate();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return result;
		
	}
	
	// 회원 정보 수정
	public int modifyMember(Member member, int selectNo) {
		int result = 0;
		try {
			conn();
			String sql = "";
			if (selectNo == 1) {
				sql = "UPDATE trp_member SET member_pw = ? WHERE member_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberPw());
			} else if (selectNo == 2) {
				sql = "UPDATE trp_member SET member_name = ? WHERE member_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberName());
			} else if (selectNo == 3) {
				sql = "UPDATE trp_member SET member_email = ? WHERE member_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberEmail());
			}
			
			pstmt.setString(2, member.getMemberId());
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return result;
	}
	
	// 회원 탈퇴
	public int deleteMember(String id) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM trp_member WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
	
		return result;
	}
	
	// 전체 회원 조회
	public List<Member> getMemberList() {
		List<Member> list = new ArrayList<>();
		Member member = null;
		try {
			conn();
			String sql = "SELECT member_id, member_name, member_regdate, member_auth FROM trp_member";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				member = new Member();
				member.setMemberId(rs.getString("member_id"));
				member.setMemberName(rs.getString("member_name"));
				member.setMemberRegdate(rs.getDate("member_regdate"));
				member.setMemberAuth(rs.getString("member_auth"));
				list.add(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return list;
	}
	
	
	// 회원 검색
	public Member getMember(String id) {
		Member member = null;
		try {
			conn();
			String sql = "SELECT member_id, member_name, member_regdate, member_auth FROM trp_member WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				member = new Member();
				member.setMemberId(id);
				member.setMemberName(rs.getString("member_name"));
				member.setMemberRegdate(rs.getDate("member_regdate"));
				member.setMemberAuth(rs.getString("member_auth"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return member;
	}
	
	// admin용 회원 정보 수정
	public int adminModifyMember(Member member, String id, int selectNo) {
		int result = 0;
		try {
			conn();
			String sql = "";
		
			if (selectNo == 1) {
				sql = "UPDATE trp_member SET member_id = ? WHERE member_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberId());
			} else if (selectNo == 2) {
				sql = "UPDATE trp_member SET member_pw = ? WHERE member_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberPw());
			} else if (selectNo == 3) {
				sql = "UPDATE trp_member SET member_name = ? WHERE member_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberName());
			} else if (selectNo == 4) {
				sql = "UPDATE trp_member SET member_email = ? WHERE member_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberEmail());
			} else if (selectNo == 5) {
				sql = "UPDATE trp_member SET member_auth = ? WHERE member_id = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberAuth());
			}
			
			pstmt.setString(2, id);
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return result;
	}
	
	// admin용 회원 삭제
	public int adminDeleteMember(String id) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM trp_member WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return result;
	}
	
	


}
