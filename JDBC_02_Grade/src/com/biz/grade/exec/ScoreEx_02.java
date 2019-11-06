package com.biz.grade.exec;

import java.util.Scanner;

import com.biz.grade.persistence.ScoreDTO;
import com.biz.grade.service.ScoreService;
import com.biz.grade.service.ScoreServiceV1;

public class ScoreEx_02 {

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		ScoreService sc = new ScoreServiceV1();
		
		while(true) {
			
			System.out.println("==================================================");
			System.out.println("성적처리 v1");
			System.out.println("==================================================");
			System.out.print("찾을 데이터 ID >> ");
			String strID = scan.nextLine();
			
			long s_id = Long.valueOf(strID);
			
			ScoreDTO scoreDTO = sc.findById(s_id);
			if(scoreDTO == null) {
				System.out.println("찾는 ID가 없습니다.");
			}else {
				// 아이디를 찾아서 아이디에 해당하는 데이터를 보여줌
				System.out.println(scoreDTO.toString());
			}
			
			
			
			
		}
		
		
	}

}
