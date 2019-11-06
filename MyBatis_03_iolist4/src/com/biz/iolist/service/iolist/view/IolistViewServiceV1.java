package com.biz.iolist.service.iolist.view;

import java.util.List;

import com.biz.iolist.config.DBConnection;
import com.biz.iolist.dao.IolistViewDao;
import com.biz.iolist.persistence.IolistVO;

public class IolistViewServiceV1 {

	IolistViewDao ioViewDao;
	
	public IolistViewServiceV1() {
		
		ioViewDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(IolistViewDao.class);
	
	}
	
	protected void viewList(List<IolistVO> iolist) {

		System.out.println("=====================================================");
		System.out.println("매입매출정보");
		System.out.println("=====================================================");
		System.out.println("SEQ\t거래일자\t구분\t거래\t상품\t수량\t단가\t합계");
		System.out.println("-----------------------------------------------------");
		for(IolistVO vo : iolist) {
			
			this.viewItem(vo);
		}
		System.out.println("=====================================================");
	}
	
	protected void viewItem(IolistVO vo) {
		
		System.out.print(vo.getIo_seq() + "\t");
		System.out.print(vo.getIo_date() + "\t");
		System.out.print(vo.getIo_inout() + "\t");
		System.out.printf("(%s)%s", vo.getIo_date(), vo.getIo_name());
		System.out.printf("(%s)%s", vo.getIo_date(), vo.getIo_pname());
		System.out.print(vo.getIo_qty() + "\t");
		System.out.print(vo.getIo_price() + "\t");
		System.out.print(vo.getIo_total() + "\n");
		
	}
	
	public void viewAllList() {
		
		List<IolistVO> iolist = ioViewDao.selectAll();
		if(iolist != null && iolist.size() > 0) {
			
			this.viewList(iolist);
		}
		
	}

	// 상품코드를 매개변수로받아서 리스트 출력
	public void viewListByPcode(String pCode) {
		List<IolistVO> iolist = ioViewDao.findByPcode(pCode);
		if(iolist != null && iolist.size() > 0) {
			this.viewList(iolist);
		}
	}
	// 상품명을 매개변수로 받아서 리스트 출력
	public void viewListByPname(String pName) {
		List<IolistVO> iolist = ioViewDao.findByPname(pName);
		if(iolist != null && iolist.size() > 0) {
			this.viewList(iolist);
		}
	}
	
	// 거래처코드 매개변수로 받아 리스트 출력
	public void viewListByDcode(String dCode) {
		List<IolistVO> iolist = ioViewDao.findByDcode(dCode);
		if(iolist != null && iolist.size() > 0) {
			this.viewList(iolist);
		}
	}
	// 거래처명을 매개변수로 받아 리스트 출력
	public void viewListByDname(String dName) {
		List<IolistVO> iolist = ioViewDao.findByDname(dName);
		if(iolist != null && iolist.size() > 0) {
			this.viewList(iolist);
		}
	}

	
}
