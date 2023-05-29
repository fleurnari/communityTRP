package com.trp.challenge;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChalBoard {
	private int BoardNumber;
	private String BoardTitle;
	private String BoardWriter;
	private String BoardContent1;
	private String BoardContent2;
	private String BoardContent3;
	private Date BoardRegdate;
	private int BoardHit;
	private int BoardRecomm;
	
	// 베스트 게시판을 위한 필드
	private int ranking;
}
