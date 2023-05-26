package com.trp.mainboard;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainReply {
	private int boardNumber;
	private int replyNumber;
	private String replyWriter;
	private String replyContent;
	private Date replyRegdate;
	

}
