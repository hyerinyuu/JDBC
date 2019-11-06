package com.biz.cbt.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDTO {

	private String cb_code;	//varchar2(5 byte)
	private String cb_quest;	//	nvarchar2(125 char)
	private String cb_one;	//	nvarchar2(125 char)
	private String cb_two;	//	nvarchar2(125 char)
	private String cb_three;	//	nvarchar2(125 char)
	private String cb_four;	//	nvarchar2(125 char)
	private String cb_ra;	//	varchar2(5 byte)
	
	
}
