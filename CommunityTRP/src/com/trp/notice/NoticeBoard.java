package com.trp.notice;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeBoard {
	private int BoardNumber;
	private String BoardTitle;
	private String BoardWriter;
	private String BoardContent;
	private Date BoardRegdate;
	private int BoardHit;
}
