package com.biz.grade.service;

import java.util.List;
import java.util.Scanner;

import com.biz.grade.persistence.dao.ScoreDao;
import com.biz.grade.persistence.dao.StudentDao;
import com.biz.grade.persistence.dao.SubjectDao;
import com.biz.grade.persistence.dao.impl.ScoreDaoImp;
import com.biz.grade.persistence.dao.impl.StudentDaoImp;
import com.biz.grade.persistence.dao.impl.SubjectDaoImp;
import com.biz.grade.persistence.domain.ScoreDTO;
import com.biz.grade.persistence.domain.ScoreVO;
import com.biz.grade.persistence.domain.StudentDTO;
import com.biz.grade.persistence.domain.SubjectDTO;

public class ScoreServiceV3 {

	StudentDao stdDao = null;
	SubjectDao subDao = null;
	ScoreDao scoreDao = null;
	Scanner scan = null;
	
	public ScoreServiceV3() {
		// 클래스를 생성할 때 scanner를 만들 수 있도록 생성자 안에 scanner 생성
		scan = new Scanner(System.in);
		stdDao = new StudentDaoImp();
		subDao = new SubjectDaoImp();
		scoreDao = new ScoreDaoImp();
		
	}
	
	public int insert(ScoreDTO scoreDTO) {
		
		// 단순히 scoreDao에게 중계역할만 수행
		return scoreDao.insert(scoreDTO);
		
	}
	
	// 키보드로 학생정보(이름, 학번)를 입력받아서 
	// 학생 테이블에 데이터가 있는지를 조회하는 method
	public String inputStudent() {
		
		String strStNum = null;
		while(true) {
			
			strStNum = null;// strStNum clear
			
			System.out.print("학번 (-Q : quit) >> ");
			strStNum = scan.nextLine();
			if(strStNum.equals("-Q")) {
				break;
			}
			
			// 학번조회
			StudentDTO stdDTO = stdDao.findById(strStNum);
			
			if(stdDTO == null) {
				System.out.println("학생정보에 학번이 없습니다.");
				System.out.println("학생정보를 먼저 등록해야 합니다.");
				continue;
			}
			// 학번으로 조회해서 있으면 학생정보 확인차 보여주기
			System.out.println(stdDTO.toString());
			break;
		}
		
		if(strStNum.equals("-Q")) return null;
		return strStNum;
		
		
	}
	public String inputSubject() {
		
		String strSubject = null;
		
		// 과목코드 입력받고 처리
		while(true) {
			
			System.out.print("과목코드 (-Q : quit ) >> ");
			strSubject = scan.nextLine();
			if(strSubject.equals("-Q")) {
				break;
			}
			SubjectDTO subDTO = subDao.findById(strSubject);
			
			if(subDTO == null) {
				System.out.println("과목코드 없음");
				System.out.println("과목정보를 먼저 등록해주세요");
				continue;
			}
			System.out.println(subDTO.toString());
			break;
		}
		
		if(strSubject.equals("-Q")) return null;
			return strSubject;
	}
	
	// method의 return type을 int가 아닌
	// Integer Class로 선언
	// : int형 return은 0~9까지의 숫자형들만 사용이 가능(return null에 오류남)
	// 하지만 Integer Class형은 0~9 숫자형과 null을 사용 가능
	public Integer inputScore() {
		
		String strScore = null;
		Integer intScore = null;
		
		while(true) {
			
			System.out.print("점수 (-Q : quit ) >> ");
			strScore = scan.nextLine();
			if(strScore.equals("-Q")) {
				break;
			}
			try {
				intScore = Integer.valueOf(strScore);
				if(intScore < 0 || intScore > 100 ) {
					System.out.println("점수는 0 ~ 100 까지만 입력가능합니다.");
					continue;
				}
			} catch (Exception e) {
				System.out.println("성적은 숫자로만 입력해주세요");
				continue;
			}
			break;
		}
		
		if(strScore.equals("-Q")) return null;
		return intScore;
		
	}
	/*
	 * 학번을 입력하면
	 * 학번에 해당하는 성적 리스트를 return
	 */
	public List<ScoreVO> updateStudent() {
		
		List<ScoreVO> scoreList = null;
		String strStNum = null;
		
		while(true) {
			System.out.print("학번 (-Q : quit) >> ");
			
			strStNum = scan.nextLine();
			
			if(strStNum.equals("-Q")) break;
			
			scoreList = scoreDao.findByStNum(strStNum);
			
			if(scoreList == null || scoreList.size() < 1 ) {
				System.out.println("성적데이터 없음");
				continue;
			}
			// 성적데이터가 있으면 반복문을 끝냄
			break;
			
		}
		if(strStNum.equals("-Q")) return null;
		return scoreList;
	}
	
	/*
	 * score테이블에서 수정(편집)할 데이터 선택
	 */
	public ScoreVO selectScore(){
	
			ScoreVO scoreVO = null;
			String strScore = null;
			
		while(true) {
			
			System.out.println("----------------------------------------");
			System.out.print("수정할 ID (-q : quit) >> ");
			strScore = scan.nextLine();
			if(strScore.equals("-Q")) break;
			
			try {
				scoreVO = 
				scoreDao.findByID(Integer.valueOf(strScore));
				
				if(scoreVO == null) {
					System.out.println("ID값이 없습니다.");
					continue;
				}
				
			} catch (Exception e) {
				System.out.println("ID는 숫자만 가능!");
				continue;
			}
			break;
			
		}
		if(strScore.equals("-Q")) return null;
		return scoreVO;
		
	}

	public int updateScore(ScoreVO scoreVO) {

		System.out.printf("학번 (%s) >> ", scoreVO.getS_std());
		String strStNum = scan.nextLine();
		// 학번을 입력 했을때
		if(strStNum.length() > 0) {
			scoreVO.setS_std(strStNum);
		}
		
		System.out.printf("과목코드 (%s) >> ", scoreVO.getS_subject());
		String strSubject = scan.nextLine();
		if(strSubject.length() > 0) {
			scoreVO.setS_subject(strSubject);
		}
		
		System.out.printf("점수 (%d) >> ", scoreVO.getS_score());
		String strScore = scan.nextLine();
		
		/*
		 * Integer.valueof()
		 * 키보드에서 숫자 이외의 값이나
		 * 그냥 Enter를 입력하면
		 * Exception을 발생
		 * 그래서 그냥 Enter을 입력하면
		 * scoreVO.setS_score(점수) 명령을 skip한다.
		 * enter만 입력하면 원래 값을 그대로 유지
		 * 숫자를 입력하면 입력한 값으로 변경
		 */
		try {
			int intScore = Integer.valueOf(strScore);
			scoreVO.setS_score(intScore);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		/*
		 * scoreVO를 ScoreDTO형으로 변환시켜서
		 * update() method에게 전달
		 */
		return scoreDao.update(ScoreDTO.builder()
				.s_id(scoreVO.getS_id())
				.s_std(scoreVO.getS_std())
				.s_subject(scoreVO.getS_subject())
				.s_score(scoreVO.getS_score())
				.build()
				);
		
		
		
	}
	
}
