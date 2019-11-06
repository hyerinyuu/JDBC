package com.biz.jdbc.exec;

import java.util.List;

import com.biz.jdbc.domain.ScoreVO;
import com.biz.jdbc.service.ScoreJDBCServiceV3;

public class JdbcEx_07 {

	public static void main(String[] args) {

		ScoreJDBCServiceV3 sc = new ScoreJDBCServiceV3();
		
		// (데이터를 입력할때는 VO에 담아서 입력함)
		// 새로운 데이터를 입력해 scoreVO에 담음
		// insert method에게 scoreVO를 매개변수로 전달함
		// insert method는 sql명령을 수행하고
		// ?로 되어있는 부분에 vo로부터 각각의 요소를 빼서 저장함
		// execute명령을 이용해 INSERT수행함
		ScoreVO scoreVO = ScoreVO.builder()
				.s_id(606)
				.s_std("이몽룡")
				.s_score(100)
				.s_rem("연습").build();
		
		int ret = sc.insert(scoreVO);
		System.out.println(ret);
		
		List<ScoreVO> scList = sc.findByName("이몽룡");
		for(ScoreVO s : scList) {
			System.out.println(s);
		}
		
	}

}
