package com.trp.notice;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeBoard {
	private int NoticeNumber;
	private String NoticeTitle;
	private String NoticeWriter;
	private String NoticeContent;
	private Date NoticeRegdate;
	private int NoticeHit;
}
