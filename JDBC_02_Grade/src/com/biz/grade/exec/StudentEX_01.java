package com.biz.grade.exec;

import java.util.List;
import java.util.Scanner;

import com.biz.grade.persistence.ScoreDTO;
import com.biz.grade.persistence.StudentDTO;
import com.biz.grade.service.ScoreService;
import com.biz.grade.service.ScoreServiceV1;
import com.biz.grade.service.StudentService;
import com.biz.grade.service.StudentServiceV1;

public class StudentEX_01 {

	public static void main(String[] args) {

		StudentService st = new StudentServiceV1();
		ScoreService sc = new ScoreServiceV1();
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			
			System.out.println("==================================================");
			System.out.println("내맘대로 성적처리");
			System.out.println("==================================================");
			System.out.print("학생이름 >> ");
			String strName = scan.nextLine();
			
			List<StudentDTO> stdList = st.findByName(strName);
			
			// 학생을 못 찾으면 stdList가 null값이 되어야 하는데 
			//  = new ArrayList() 생성을 하면
			// null 아닌 size()가 0인 값이 return되기 때문에
			// null 이거나 size() < 1 인 경우를 검사
			if(stdList == null || stdList.size() < 1 ) {
				
				System.out.println("찾는 학생이 없음");
				// 처음으로 돌아가기
				continue;
				
			}
			
			for(StudentDTO sDTO : stdList) {
					
				// 학생정보리스트의 현재값을 검사해
				// debugging용 코드
				System.out.println(sDTO.toString());
				
					List<ScoreDTO> scList = sc.findByName(sDTO.getSt_num());
					if(scList != null) {
						for(ScoreDTO scDTO : scList) {
							System.out.println(scDTO.toString());
				    	}
					}
					
			}
				
		}

			
	}
		
		
}


