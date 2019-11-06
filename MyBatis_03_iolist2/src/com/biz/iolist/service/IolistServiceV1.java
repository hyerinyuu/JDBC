package com.biz.iolist.service;

import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSession;

import com.biz.iolist.config.DBConnection;
import com.biz.iolist.dao.DeptDao;
import com.biz.iolist.dao.IolistDao;
import com.biz.iolist.dao.IolistViewDao;
import com.biz.iolist.dao.ProDao;
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
	
	public void insertIolist() {
		
		System.out.println("=================================================");
		System.out.println("매입매출데이터작성");
		System.out.println("=================================================");
		
		while(true) {
			
			String strSeq = scan.nextLine();	// 자동	
			
			String strInout = scan.nextLine();
			String strQty = scan.nextLine();
			String strPrice = scan.nextLine();
			String strTotal = scan.nextLine();  // Qty * price
			String strPcode = scan.nextLine();  // 자동
			String strDcode = scan.nextLine(); // 자동
			
			System.out.print("거래일자(-Q : quit) >> ");
			String strDate = scan.nextLine();
			if(strDate.equals("-Q")) {
				System.out.println("작성종료");
				break;
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
