package com.biz.grade.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * [ScoreVO]
 *  테이블을 JOIN해서 얻어진 View결과를 
 *  DBMS에 받아서 다른 Class에 전달하기 위한 용도
 *  (view를 위해 만들어진 Class)
 */
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ScoreVO {

	private String s_std;		//varchar2(5)
	private String st_name;		//	nvarchar2(50)
	private int st_grade;		//	number(1)
	private String st_dept;		//	varchar2(5)
	private String d_name;		//	nvarchar2(30)
	private String d_tel;		//	varchar2(20)
	private String s_subject;	//	varchar2(4)
	private String sb_name;		//	nvarchar2(20)
	private int s_score;		//	number(3)
	private long s_id;		//	number
	
	
}
