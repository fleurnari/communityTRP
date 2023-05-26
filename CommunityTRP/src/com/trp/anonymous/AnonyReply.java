package com.trp.anonymous;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnonyReply {
	private int boardNumber;
	private int replyNumber;
	private String replyWriter;
	private String replyContent;
	private Date replyRegdate;
}
