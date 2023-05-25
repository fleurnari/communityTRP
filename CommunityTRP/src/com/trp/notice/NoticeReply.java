package com.trp.notice;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeReply {
	private int noticeNumber;
	private int replyNumber;
	private String replyWriter;
	private String replyContent;
	private Date replyRegdate;
	
}

