package com.trp.report;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Report {
	
	private int reportNumber;
	private String complainId;
	private String suspectId;
	private String reportType;
	private String reportContent;
	private String reportReason;
	private int boardNumber;
	private Date reportRegdate;
}
