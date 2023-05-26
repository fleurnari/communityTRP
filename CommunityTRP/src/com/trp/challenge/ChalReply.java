package com.trp.challenge;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChalReply {
	private int boardNumber;
	private int replyNumber;
	private String replyWriter;
	private String replyContent;
	private Date replyRegdate;
}
