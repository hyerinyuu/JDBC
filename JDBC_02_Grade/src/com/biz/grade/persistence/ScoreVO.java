package com.biz.grade.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * DB와 관련된 용어들 중
 * Table과 연관된 class들의 묶음(=> package)
 * 과거 : value object라 명명
 * vo(Value Object)
 * domain 
 * command
 * entity
 * persistence(영속성, 기속성 : 메모리가 아닌 물리적 공간에
 * 							    저장되는 데이터)
 */
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ScoreVO {	
	
	private String s_std; 		//	varchar2(5)
	private String st_name; 	//	nvarchar2(50)
	private int st_grade; 		//	number(1)
	private String st_dept; 	//	varchar2(5)
	private String d_name; 		//	nvarchar2(30)
	private String d_tel; 		//	varchar2(20)
	private String s_subject;	//	varchar2(4)
	private String sb_name; 	//	nvarchar2(20)
	private int s_score; 		//	number(3)
	private int s_id; 			//	number

	
	
	
}
