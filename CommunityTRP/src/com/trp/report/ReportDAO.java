package com.trp.report;

import java.util.ArrayList;
import java.util.List;

import com.trp.common.DAO;

public class ReportDAO extends DAO {
	
	
	private static ReportDAO reportDao = null;
	
	private ReportDAO() {
		
	}
	
	public static ReportDAO getInstance() {
		if (reportDao == null) {
			reportDao = new ReportDAO();
		}
		return reportDao;
	}
	
	
	
	// 게시물 신고 기능
	public int insertReport(Report report) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO trp_report VALUES (trp_report_seq.nextval,?,?,'게시물',?,?,?,sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, report.getComplainId());
			pstmt.setString(2, report.getSuspectId());
			pstmt.setString(3, report.getReportContent());
			pstmt.setString(4, report.getReportReason());
			pstmt.setInt(5, report.getBoardNumber());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return result;
		
	}
	
	
	// 댓글 신고 기능
	public int replyReport(Report report) {
		int result = 0;
		try {
			conn();
			String sql = "INSERT INTO trp_report VALUES (trp_report_seq.nextval,?,?,'댓글',?,?,?,sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, report.getComplainId());
			pstmt.setString(2, report.getSuspectId());
			pstmt.setString(3, report.getReportContent());
			pstmt.setString(4, report.getReportReason());
			pstmt.setInt(5, report.getBoardNumber());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return result;
		
	}
	
	// admin용 신고 목록 조회
	public List<Report> getReportList() {
		List<Report> list = new ArrayList<>();
		Report report = null;
		
		try {
			conn();
			String sql = "SELECT report_number, complain_id, report_type, report_regdate FROM trp_report";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				report = new Report();
				report.setReportNumber(rs.getInt("report_number"));
				report.setComplainId(rs.getString("complain_id"));
				report.setReportType(rs.getString("report_type"));
				report.setReportRegdate(rs.getDate("report_regdate"));
				list.add(report);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return list;
	}
	
	
	// admin용 신고 목록 상세 조회
	public Report getReport(int reportNum) {
		Report report = null;
		try {
			conn();
			String sql = "SELECT * FROM trp_report WHERE report_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reportNum);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				report = new Report();
				report.setReportNumber(rs.getInt("report_number"));
				report.setComplainId(rs.getString("complain_id"));
				report.setSuspectId(rs.getString("suspect_id"));
				report.setReportType(rs.getString("report_type"));
				report.setReportContent(rs.getString("report_content"));
				report.setReportReason(rs.getString("report_reason"));
				report.setBoardNumber(rs.getInt("board_number"));
				report.setReportRegdate(rs.getDate("report_regdate"));
			}
 			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		
		return report;
	}
	
	// 신고 완료 처리
	public int deleteReport(int reportNum) {
		int result = 0;
		try {
			conn();
			String sql = "DELETE FROM trp_report WHERE report_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reportNum);
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconn();
		}
		return result;
	}
	
	
	
	
	
	

}
