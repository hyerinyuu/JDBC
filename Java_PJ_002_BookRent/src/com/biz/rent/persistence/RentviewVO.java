package com.biz.rent.persistence;

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
public class RentviewVO {

	
	private int rent_seq;	//number
	private String u_code;	//varchar2(6)
	private String u_name;	//nvarchar2(125)
	private String b_code;	//varchar2(6)
	private String b_name;	//nvarchar2(125)
	private String rent_date;	//varchar2(10)
	private String rent_return_yn;	//varchar2(1)
	
}
