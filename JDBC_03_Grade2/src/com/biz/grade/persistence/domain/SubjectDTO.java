package com.biz.grade.persistence.domain;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDTO {

	private String sb_code;	//varchar2(4 byte)
	private String sb_name;	//	nvarchar2(20 char)
	private String sb_pro;	//	nvarchar2(20 char)
	
	
}
