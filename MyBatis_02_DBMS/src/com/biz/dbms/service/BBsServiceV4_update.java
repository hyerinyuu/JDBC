package com.biz.dbms.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.biz.dbms.config.DBConnection;
import com.biz.dbms.dao.BBsDao;
import com.biz.dbms.persistence.BBsDTO;

public class BBsServiceV4_update  {

	SqlSession sqlSession = null;
	// scanner를 여기서 선언하고 생성해도 상관은 없지만 
	// 패턴상 생성자에서 초기화를 하는게 더 매끄러운 코드라서 이렇게 선언함
	Scanner scan = null;	
	
	public BBsServiceV4_update() {
		sqlSession = DBConnection.getSqlSessionFactory().openSession(true);
		scan = new Scanner(System.in);
		
	}
	
	public void bbsMenu() {
		
		int intMenu = 0;
		while(true) {
			
			System.out.println("");
			System.out.println("내용보기(SQ입력)  W.작성  U.수정  D.삭제  Q.종료");
			System.out.print(" >> ");
			String strMenu = scan.nextLine();
			
			if(strMenu.equalsIgnoreCase("Q")) {
				System.out.println("게시판 작성 종료!");
				return;
			}else if(strMenu.equalsIgnoreCase("W")) {
				
				this.writeBBS();
				this.viewBBsList();
				
			}else if(strMenu.equalsIgnoreCase("U")) {
				
				
			}else if(strMenu.equalsIgnoreCase("D")) {
				
				this.viewBBsList();
				this.deleteBBs();
				
			}else {
				try {
					long longSEQ = Integer.valueOf(strMenu);
					this.viewText(longSEQ);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

	}
	
	public void viewText(long bs_id) {
		
		BBsDao bbsDao = sqlSession.getMapper(BBsDao.class);
		BBsDTO bbsDTO = bbsDao.findById(bs_id);
		
		if(bbsDTO == null) {
			System.out.println("내용이 없습니다.");
		}else {
			System.out.println("제목 : " + bbsDTO.getBs_subject());
			System.out.println("작성자 : " + bbsDTO.getBs_writer());
			System.out.println("작성일 : " + bbsDTO.getBs_date());
			System.out.println("작성시각 : " + bbsDTO.getBs_time());
			System.out.println(bbsDTO.getBs_text());
			
		}
		
	}
	
	// 1. 수정할 ID를 입력받고
	// 2. 내용을 한번 보여주고
	// 3. 각 항목을 입력받고
	// 4. Enter를 입력하면 원래 데이터 유지
	// 5. 새로 입력하면 새로운 값으로 대치
	public void updateBBs() {
		
		BBsDao bbsDao = sqlSession.getMapper(BBsDao.class);
		
		// 키보드로 입력받을 수정할 변수들 선언
		String strWriter = "";
		String strSubject = "";
		String strText = "";
		long longID = 0;
		
		System.out.println("==============================================");
		System.out.println("게시글 삭제");
		System.out.println("==============================================");
		System.out.print("수정할 ID(-Q : quit) >> ");
		// 1. 수정할 ID를 입력받는 method
		String strID = scan.nextLine();
		try {
			longID = Integer.valueOf(strID);
			
			BBsDTO bbsDTO = bbsDao.findById(longID);
			// 2. 삭제할 id 게시글 내용 보여주는 method
			this.viewText(longID);
			
			 // 작성일자, 작성시각은 컴퓨터 시간을 참조하여 자동생성
			//[java 1.7 이하의 코드 작성]
			
			System.out.print("수정하시겠습니까?(Y/N) >> ");
			String yesNo = scan.nextLine();
			if(yesNo.equalsIgnoreCase("Y")) {
				System.out.println("===========================================");
				System.out.println("게시판 수정");
				System.out.println("===========================================");
				
				System.out.print("작성자 >> " + strWriter);
				strWriter = scan.nextLine();
				
				System.out.print("제목 >> " + strSubject);
				strSubject = scan.nextLine();
				
				System.out.print("내용 >> " + strText);
				strText = scan.nextLine();
				
			//	bbsDao = sqlSession.getMapper(BBsDao.class);
				
				bbsDTO = BBsDTO.builder().bs_id(longID)
						.bs_date(this.getDate())
						.bs_time(this.getTime())
						.bs_writer(strWriter)
						.bs_subject(strSubject)
						.bs_text(strText)
						.build();
				bbsDao.update(bbsDTO);
				
			}else {
				System.out.println("수정 종료");
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("수정완료!");
		this.viewBBsList();
	
		
		
	}
	
	// 삭제할 게시판 id를 입력받고
	// 해당 id의 게시글 내용을 보여주고
	// 삭제여부 묻고 진행
	public void deleteBBs() {
		
		System.out.println("==============================================");
		System.out.println("게시글 삭제");
		System.out.println("==============================================");
		System.out.print("삭제할 ID(-Q : quit) >> ");
		String strId = scan.nextLine();
		
		try {
			
			int intId = Integer.valueOf(strId);
			this.viewText(intId);
			System.out.println("----------------------------------------------");
			System.out.println("삭제할까요? (Y/N)");
			String yesNo = scan.nextLine();
			if(yesNo.equalsIgnoreCase("Y")) {
				BBsDao bbsDao = sqlSession.getMapper(BBsDao.class);
				bbsDao.delete(intId);		
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public String getDate() {
		
		Date date = new Date(System.currentTimeMillis());
		// df: date format = 날짜형 값을 ex) "2019-10-24" 형식의 문자열형으로 변환
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String curDate = df.format(date);
		
		return curDate;
		
	}
	
	public String getTime(){
		
		Date date = new Date(System.currentTimeMillis());
		// tf : time format = 날짜형 값을 ex) "14:00:00" 형식의 문자열형으로 변환
		SimpleDateFormat tf = new SimpleDateFormat("HH:mm:SS");
		String curTime = tf.format(date);
		
		return curTime;
		
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
			
			// this.viewBBsList();
			break;
		}
	}

	public void viewBBsList() {

		BBsDao bbsDao = sqlSession.getMapper(BBsDao.class);
		List<BBsDTO> bbsList = bbsDao.selecAll();
		
		System.out.println("=========================================================");
		System.out.println("슈퍼 BBS v1");
		System.out.println("=========================================================");
		System.out.println("SQ\t작성일\t\t작성시각\t작성자\t제목\t내용");
		for(BBsDTO bbs : bbsList) {
			
			System.out.print(bbs.getBs_id() + "\t");
			System.out.printf("%s\t",bbs.getBs_date());
			System.out.printf("%s\t",bbs.getBs_time());
			System.out.printf("%s\t",bbs.getBs_writer());
			System.out.printf("%s\t",bbs.getBs_subject());
			System.out.printf("%s\n",bbs.getBs_text() );
			
		}
		System.out.println("=========================================================");
		
	}
	
}
