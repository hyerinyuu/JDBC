package com.biz.mybatis.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BookDTO {

	private String b_code;
	private String b_name;
	private String b_comp;
	private String b_writer;
	private int b_price;
	
	
}
