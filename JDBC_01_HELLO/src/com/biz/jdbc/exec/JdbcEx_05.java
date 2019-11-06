package com.biz.jdbc.exec;

import java.util.List;

import com.biz.jdbc.domain.ScoreVO;
import com.biz.jdbc.service.ScoreJDBCServiceV2;

public class JdbcEx_05 {

	public static void main(String[] args) {

		ScoreJDBCServiceV2 sc = new ScoreJDBCServiceV2();
		
		// "'갈한수' OR 1 = 1" = 이름이 갈한수 이거나 1=1이라면(TRUE)  ==> 모든 리스트가 다 출력
		List<ScoreVO> stdList = sc.findByName("'갈한수' OR 1 = 1");
		
		for(ScoreVO sVO : stdList) {
			System.out.println(sVO.toString());
		}
		
	}

}
