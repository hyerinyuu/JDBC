package com.biz.grade.exec;

import java.util.List;

import com.biz.grade.persistence.ScoreVO;
import com.biz.grade.service.ScoreServiceV2;
import com.biz.grade.service.extend.ScoreServiceV2Ext;

public class ScoreEX_01 {

	public static void main(String[] args) {

		ScoreServiceV2 sc = new ScoreServiceV2Ext();
		
		List<ScoreVO> scoreList = sc.selectAll();
		
		if(scoreList == null || scoreList.size() < 1 ) {
			System.out.println("데이터가 없습니다.");
			
			// main()에서 return을 하면 project 종료하라는 의미
			return;
		}
		for(ScoreVO vo : scoreList) {
			// 전체 리스트를 확인하는 method
			System.out.println(vo.toString());
			
		}
		
		
	}

}
