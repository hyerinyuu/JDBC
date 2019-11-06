package com.biz.cbt.service;

import java.util.List;
import java.util.Scanner;

import com.biz.cbt.config.DBConnection;
import com.biz.cbt.dao.QuizDao;

public class QuizServiceV1 {

	protected QuizDao quizDao;
	List<QuizDao> quisList;
	Scanner scan;
	
	public QuizServiceV1() {
		
		quizDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(QuizDao.class);
		scan = new Scanner(System.in);
	}

	public void menu() {
		
		while(true) {

			System.out.println("==============================================");
			System.out.println("1.문제등록 2.문제수정 3.문제삭제 0.종료");
			System.out.println("==============================================");
			
			System.out.print("선택 >> ");
			String strMenu = scan.nextLine();
			int intMenu = 0;
			
			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
				System.out.println("숫자만 입력하세요");
				return;
			}
			
			if(intMenu == 0) {
				System.out.println("업무 종료!");
				break;
			}else if(intMenu == 1) {
				this.insert();
			}else if(intMenu == 2) {
				this.update();
			}else if(intMenu == 3) {
				this.delete();
			}
			break;
		}	
	}
	
	private void insert() {
		// TODO insert
		System.out.println("==============================================");
		System.out.println("문제 등록");
		System.out.println("==============================================");
		
		// 문제 입력
		while(true) {
			System.out.print("문제를 입력하세요(-Q : quit) >> ");
			String strQuest = scan.nextLine();
			
			if(strQuest.equals("-Q")) {
				System.out.println("문제 등록 종료");
				break;
			}
			
			if(strQuest.trim().isEmpty()) {
				System.out.println("문제는 반드시 입력하셔야 합니다");
				continue;
			}
			break;
		}
		
		// 선택지1
		String strOne;
		while(true) {
			System.out.print("선택지1 >> ");
			strOne = scan.nextLine();
			
			break;
		}
		String strTwo;
		while(true) {
			System.out.print("선택지2 >> ");
			strTwo = scan.nextLine();
			break;
		}
		String strThree;
		while(true) {
			System.out.print("선택지3 >> ");
			strThree = scan.nextLine();
			break;
		}
		String strFour;
		while(true) {
			System.out.print("선택지4 >> ");
			strFour = scan.nextLine();
			break;
		}
		
		while(true) {
			System.out.print("정답 >> ");
			String strRA = scan.nextLine();

			
			
			
			break;
		}
		
		// 입력한 문제를 보여주고
		// 맞는지 여부 확인
		while(true){
		
			System.out.print("이대로 등록하시겠습니까?(Enter : Yes, N : 문제 다시 등록하기) >> ");
			String strYesNo = scan.nextLine();
			
			try {
				if(strYesNo.equalsIgnoreCase("N")) {
					return;
				}
				if(strYesNo.trim().isEmpty()) {
					// 등록
				}
			} catch (Exception e) {
				System.out.println("Enter와 N만 입력 가능합니다.");
				continue;
			}
			
		}
		
	
	}

	private void update() {
		// TODO update
		
		while(true) {
			System.out.println("==============================================");
			System.out.println("문제 수정");
			System.out.println("==============================================");
			
			// 문제 리스트 보여주기
			
			System.out.print("수정할 문제 코드 >> ");
			String strCode = scan.nextLine();
			
			
		
			
			
			break;
		}
		
	}

	public void delete() {
		// TODO delete
	}
	
	
}
