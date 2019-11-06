package com.biz.iolist.service;

import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.biz.iolist.config.DBConnection;
import com.biz.iolist.dao.DeptDao;
import com.biz.iolist.dao.IolistDao;
import com.biz.iolist.dao.IolistViewDao;
import com.biz.iolist.dao.ProDao;
import com.biz.iolist.persistence.DeptDTO;
import com.biz.iolist.persistence.IolistDTO;

public class IolistServiceV1  {
	
	protected IolistDao iolistDao;
	protected DeptDao deptDao;
	protected ProDao proDao;
	protected IolistViewDao viewDao;

	SqlSession sqlSession = null;
	Scanner scan = null;
	
	public IolistServiceV1() {
		
		sqlSession  = DBConnection.getSqlSessionFactory().openSession(true);
		this.proDao = sqlSession.getMapper(ProDao.class);
		this.iolistDao = sqlSession.getMapper(IolistDao.class);
		this.deptDao = sqlSession.getMapper(DeptDao.class);
		this.viewDao = sqlSession.getMapper(IolistViewDao.class);
		
		scan = new Scanner(System.in);
	
	}
	
	public void autoDcode(String strDCode) {
	
		while(true) {
			
			System.out.print("거래처코드(-Q : quit / Enter : 자동생성) >> ");
			strDCode = scan.nextLine();
			
			// 코드 자동생성
			if(strDCode.trim().isEmpty()) {

				// maxCode == D0236(dept-mapper에 getMaxDcode 참조)
				String strTMPcode = deptDao.getMaxDcode();
				
				// 자동생성할 코드를 int형으로 형변환을 시키고 subString으로 "D"문자열 잘라내고 숫자형 0236만 남기기 
				int intDcode = Integer.valueOf(strTMPcode.substring(1));
				
				// 0236 -> 237로 변경
				intDcode ++;
				
				// 현재코드에서(D0236) D문자열을 잘라내고
				strDCode = strTMPcode.substring(0,1); // strCode = "D"
				
				// intDcode를 0237형식으로 문자열형으로 형변환 시켜서 strDcode에 붙여줌 
				strDCode += String.format("%04d", intDcode);
				
				System.out.println("자동 생성된 코드 >> " + strDCode);
				System.out.println("사용하시겠습니까?(Yes : Enter / NO : 코드입력) >> ");
				String strYesNo = scan.nextLine();
				if(strYesNo.trim().isEmpty()) break;
				else continue;
				
			}// 코드 자동생성문 end
			
			// 코드 자동 생성 안할거면 코드 규칙에 맞게 코드 생성하게 해주기
			if(strDCode.length() != 5 ) {
				System.out.println("거래처코드는 5자리여야합니다.");
				continue;
			}
			strDCode = strDCode.toUpperCase();
			
			// 상품코드의 첫글자가 대문자 소문자 관계없이 D로 시작되지 않으면
			if(!strDCode.subSequence(0, 1).equals("D") ) {
				System.out.println("거래처 코드는 D로 시작되어야 합니다.");
				continue;
			}
			
			try {
				Integer.valueOf(strDCode.substring(1));
			} catch (Exception e) {
				System.out.println("거래처 코드의 2번째 자릿수부터는 숫자만 입력 가능합니다.");
				continue;
				
			// 이미 존재하는 코드를 입력했다면
			}if(deptDao.findById(strDCode) != null) {
				System.out.println("입력한 코드는 중복되는 코드입니다.");
				continue;
				
			}	break;
		}
	}
	/*
	 * 매입매출등록
	 * 
	 * 날짜는 현재 날짜로 자동등록
	 * 
	 *  거래처 검색(이름으로)
	 * 1. 거래처 코드 입력
	 * 2. 입력한 코드 검증
	 * 
	 *  상품 검색
	 * 1. 상품코드 입력
	 * 2. 입력한 코드 검증
	 * 
	 *  매입, 매출 구분 입력
	 *  
	 *  매입, 매출에 따라서
	 *  매입단가, 매출단가 가져오기(세팅)
	 *  
	 *  수량입력
	 *  
	 *  단가 * 수량
	 *  	매입매출 
	 *  
	 *  매입합계 또는 매출합계 계산하기
	 *  
	 *  insert
	 *  
	 *  추가된 데이터 보여주기
	 */
	
	public void insertIolist() {
	
		String strDcode = null;
		while(true) {
		
			System.out.print("거래처 코드 >> ");
			strDcode = scan.nextLine();
			if(strDcode.trim().isEmpty()) {
				System.out.println("거래처코드는 반드시 입력하셔야합니다.");
				return;
				
			
			}
		}
		
		
		
	}
	
	public void viewIolist() {
		
		List<IolistDTO> ioList = iolistDao.selectAll();
		
		System.out.println("=================================================");
		System.out.println("상품정보리스트");
		System.out.println("=================================================");
		System.out.println("SEQ\t거래일자\t구분\t수량\t가격\t합계\t상품코드\t거래처코드");
		
	for(IolistDTO iDTO : ioList) {
		/*
		private int io_seq;		//number
		private String io_date;	//	varchar2(10 byte)
		private String io_inout;	//	nvarchar2(2 char)
		private int io_qty;		//	number
		private int io_price;	//	number
		private int io_total;	//	number
		private String io_pcode;	//	varchar2(5 byte)
		private String io_dcode;	//	varchar2(5 byte)
		*/
		System.out.printf("%d\t", iDTO.getIo_seq());
		System.out.printf("%s\t", iDTO.getIo_date());
		System.out.printf("%s\t", iDTO.getIo_inout());
		System.out.printf("%d\t", iDTO.getIo_qty());
		System.out.printf("%d\t", iDTO.getIo_price());
		System.out.printf("%d\t", iDTO.getIo_total());
		System.out.printf("%s\t\t", iDTO.getIo_pcode());
		System.out.printf("%s\n", iDTO.getIo_dcode());

		
	}
		
		
	}
	

}
