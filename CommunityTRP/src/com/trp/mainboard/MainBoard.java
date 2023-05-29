package com.trp.mainboard;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainBoard {
	private int BoardNumber;
	private String BoardTitle;
	private String BoardWriter;
	private String BoardContent;
	private Date BoardRegdate;
	private int BoardHit;
	private int BoardRecomm;
	
	// 베스트 삼행시를 위한 필드
	private int ranking;
	
}
