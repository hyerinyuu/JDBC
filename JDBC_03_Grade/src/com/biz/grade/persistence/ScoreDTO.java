package com.biz.grade.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 *  [ScoreDTO]
 *  CRUD중 CREATE(INSERT), UPDATE 용으로 주로 사용할 용도
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDTO {

	// ctrl + shift + y => 모두 소문자로 변경
	private long s_id;			//number
	private int s_score;		//	number(3,0)
	private String s_rem;		//	nvarchar2(50 char)
	private String s_subject;	//	varchar2(4 byte)
	private String s_std;		//	varchar2(5 byte)
	
	
}
