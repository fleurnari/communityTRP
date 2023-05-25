package com.trp.member;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberEmail;
	private Date memberRegdate;
	private String memberAuth;

	
}
