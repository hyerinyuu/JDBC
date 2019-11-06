package com.biz.iolist.service.iolist;

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
import com.biz.iolist.service.iolist.view.IolistViewServiceV1;

public class IolistServiceV1  {
	
	protected IolistDao iolistDao;
	protected DeptDao deptDao;
	protected ProDao proDao;
	protected IolistViewDao viewDao;
	
	protected IolistViewServiceV1 ioView;

	SqlSession sqlSession = null;
	protected Scanner scan = null;
	
	public IolistServiceV1() {
		
		sqlSession  = DBConnection.getSqlSessionFactory().openSession(true);
		this.proDao = sqlSession.getMapper(ProDao.class);
		this.iolistDao = sqlSession.getMapper(IolistDao.class);
		this.deptDao = sqlSession.getMapper(DeptDao.class);
		this.viewDao = sqlSession.getMapper(IolistViewDao.class);
		
		scan = new Scanner(System.in);
		ioView = new IolistViewServiceV1();
		
	}
	
	public void iolistMenu() {

		while(true){

			System.out.println("=====================================================");
			System.out.println("새나라 마트 매입매출 관리 v1");
			System.out.println("=====================================================");
			System.out.println("1.등록 2.수정 3.삭제 4.검색 0.종료");
			System.out.println("-----------------------------------------------------");
			
			System.out.print("업무선택 >> ");
			String strMenu = scan.nextLine();
			
			int intMenu = -1; 
			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
			}
			if(intMenu == 0) {
				System.out.println("업무종료");
				break;
			}else if(intMenu == 1) {
				this.insert();
			}else if(intMenu == 2) {
				this.update();
			}else if(intMenu == 3) {
				this.delete();
			}else if(intMenu == 4) {
				this.search();
			}
		}

		}

	private void search() {
		// TODO Auto-generated method stub
		
	}

	protected void delete() {
		// TODO Auto-generated method stub
		
	}

	protected void update() {
		// TODO Auto-generated method stub
		
	}

	protected void insert() {
		// TODO 매입매출 등록 method
	}	

}
