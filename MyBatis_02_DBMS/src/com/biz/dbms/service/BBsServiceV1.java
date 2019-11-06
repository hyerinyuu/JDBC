package com.biz.dbms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.biz.dbms.config.DBConnection;
import com.biz.dbms.dao.BBsDao;
import com.biz.dbms.persistence.BBsDTO;

public class BBsServiceV1  {

	SqlSession sqlSession = null;
	// scanner를 여기서 선언하고 생성해도 상관은 없지만 
	// 패턴상 생성자에서 초기화를 하는게 더 매끄러운 코드라서 이렇게 선언함
	Scanner scan = null;
	
	public BBsServiceV1() {
		sqlSession = DBConnection.getSqlSessionFactory().openSession(true);
		scan = new Scanner(System.in);
	}
	
	public void bbsMenu() {
		
		int intMenu = 0;
		while(true) {
			
			System.out.println("1.내용보기  2.작성  3.수정  4.삭제  0.종료");
			String strMenu = scan.nextLine();
			
			try {
				
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {

				System.out.println("0 또는 1~4 중에서 선택하세요");
				continue;
			}
			if(intMenu == 0) {
				return;
			}else if(intMenu == 1) {
				
				this.writeBBS();
				
			}else if(intMenu == 2) {
				
				
				
			}else if(intMenu == 3) {
				
				
				
			}else if(intMenu == 4) {
				
				
				
			}
	
		}

	}
	
	// 키보드를 사용해서 게시판 글쓰기를 구현
	public void writeBBS() {

		System.out.println("==============================================");
		System.out.println("게시판 작성");
		System.out.println("==============================================");
		
		/*
		 * 작성자, 제목, 내용을 입력하지 않으면 
		 * 메세지를 보여주고 다시 입력받도록 하기
		 * 왜냐하면 작성자, 제목, 내용(bs_writer, bs_subject, bs_text)은 not null로 선언이됐기 때문에
		 */
		while(true) {
		
			// -Q를 입력하는 까닭은 사용자가 Q를 사용할 수도 있기 때문에 오류를 방지하기 위해
			// 잘 쓰지 않는 -를 붙여줌
			System.out.print("작성자(-Q : 작성중단 ) >> ");
			String strWriter = scan.nextLine();
			if(strWriter.equals("-Q")) break;
			
			if(strWriter.trim().length() < 1 ) {
				System.out.println("작성자는 반드시 입력하셔야합니다.");
				continue;
			}
			/*
			if(strName == null) {
				System.out.println("작성자는 반드시 입력하셔야합니다.");
				continue;
			*/
			System.out.print("제목(-Q : 작성중단) >> ");
			String strSubject = scan.nextLine();
			if(strSubject.equals("-Q")) break;
			
			if(strSubject.trim().length() < 1 ) {
				System.out.println("제목은 반드시 입력하셔야합니다.");
				continue;
			}
			
			System.out.print("내용(-Q : 작성중단) >> ");
			String strText = scan.nextLine();
			if(strText.equals("-Q")) break;
			
			if(strText.trim().length() < 1 ) {
				System.out.println("내용은 반드시 입력하셔야합니다.");
				continue;
			}
			
			 // 작성일자, 작성시각은 컴퓨터 시간을 참조하여 자동생성
			//[java 1.7 이하의 코드 작성]
			
			// 컴퓨터의 현재 시각 가져오기(임포트할때 java util로 import)
			Date date = new Date(System.currentTimeMillis());
			
			// df: date format = 날짜형 값을 ex) "2019-10-24" 형식의 문자열형으로 변환
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			// tf : time format = 날짜형 값을 ex) "14:00:00" 형식의 문자열형으로 변환
			SimpleDateFormat tf = new SimpleDateFormat("HH:mm:SS");
			
			String curDate = df.format(date);
			String curTime = tf.format(date);
			
			BBsDTO bbsDTO = BBsDTO.builder()
								.bs_date(curDate)
								.bs_time(curTime)
								.bs_writer(strWriter)
								.bs_subject(strSubject)
								.bs_text(strText)
								.build();
			
			BBsDao bbsDao = sqlSession.getMapper(BBsDao.class);
			int ret = bbsDao.insert(bbsDTO);
			
			if(ret > 0) {
				System.out.println("게시판 작성 완료!");
			}else {
				System.out.println("게시판 작성 실패!");
			}
			
			this.viewBBsList();
			
			System.out.println("계속 작성 하시겠습니까? (Yes/No) ");
			String yesNo = scan.nextLine();
			
			if(yesNo.equals("N") || yesNo.equalsIgnoreCase("NO")) break;
		}
	}

	public void viewBBsList() {

		BBsDao bbsDao = sqlSession.getMapper(BBsDao.class);
		List<BBsDTO> bbsList = bbsDao.selecAll();
		
		System.out.println("=========================================================");
		System.out.println("슈퍼 BBS v1");
		System.out.println("=========================================================");
		System.out.println("작성일\t작성시각\t작성자\t제목");
		for(BBsDTO bbs : bbsList) {
			
			System.out.printf("%s",bbs.getBs_date());
			System.out.printf("%s",bbs.getBs_time());
			System.out.printf("%s",bbs.getBs_writer());
			System.out.printf("%s",bbs.getBs_subject());
			
		}
		System.out.println("=========================================================");
		
	}
	
}
