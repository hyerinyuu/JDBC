package com.biz.iolist.service.dept;

import java.util.List;
import java.util.Scanner;

import com.biz.iolist.config.DBConnection;
import com.biz.iolist.dao.DeptDao;
import com.biz.iolist.persistence.DeptDTO;

public class DeptServiceV1 {

	protected DeptDao deptDao;
	Scanner scan;
	
	public DeptServiceV1() {
		
		deptDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(DeptDao.class);
		scan = new Scanner(System.in);
	
	}
	
	// deptDao.selectAll()을 호출하여 전체 리스트를 보여주는 method
	public void viewAllList() {
		
		List<DeptDTO> deptList = deptDao.selectAll();
		// #################
		if(deptList == null || deptList.size() < 1) {
			System.out.println("리스트가 없음");
		}else {

			System.out.println("=================================================");
			System.out.println("거래처 리스트");
			System.out.println("=================================================");
			System.out.println("코드\t이름\tCEO\t전화번호\t\t주소");
			
			for(DeptDTO deptDTO : deptList) {
				
				this.viewList(deptDTO);
				
			}

			System.out.println("=================================================");
		}
		
	}
	
	// 키보드에서 거래처이름을 입력하여 거래처 리스트를 보여주는 method
	public void viewNameList(){
		
			System.out.println("=================================================");
			System.out.println("거래처 이름 검색");
			System.out.println("=================================================");

			System.out.print("거래처명 >> ");
			String strDname = scan.nextLine();
			
			List<DeptDTO> deptList = null;
			
			if(strDname.trim().isEmpty()) {
				deptList = deptDao.selectAll();
			}else {
				deptList = deptDao.findByName(strDname);
			}
			this.viewList(deptList);
	
		
	}
	
	// 키보드에서 거래처명과 대표이름을 입력하여 거래처리스트를 보여주는 method
	public void viewNameAndCeoList() {
		
		System.out.println("=================================================");
		System.out.println("거래처 이름 검색");
		System.out.println("=================================================");
		
		System.out.print("거래처명 >> ");
		String strDname = scan.nextLine();
		
		List<DeptDTO> deptList = deptDao.findByName(strDname);
		this.viewList(deptList);
		
		System.out.print("대표자명 >> ");
		String strDceo = scan.nextLine();
		
		deptList = null;
		
		// 거래처명, 대표명 아무것도 입력하지 않았을 때
		if(strDname.trim().isEmpty() && strDceo.trim().isEmpty()) {
			deptList = deptDao.selectAll();
			
		// 대표명만 입력했을때	
		}else if(strDname.trim().isEmpty()){
			 deptList = deptDao.findByCeo(strDceo);
			
		// 대표명을 입력하지 않았을 때 ,
		// 거래처명만 입력했을 때 거래처명으로 검색
		}else if(strDceo.trim().isEmpty()){
			deptList = deptDao.findByName(strDname);
			
		// 둘다 입력하면 거래처명과 대표명으로 검색
		}else {
			deptList = deptDao.findByNameAndCEO(strDname, strDceo);
		}
		this.viewList(deptList);
		
	}
	
	// 각 view에서 List를 출력할 때 사용할 method 
	// List를 반복하면서 deptDTO를 매개변수로 전달
	protected void viewList(DeptDTO deptDTO) {
		
		System.out.printf("%s\t", deptDTO.getD_code());
		System.out.printf("%s\t", deptDTO.getD_name());
		System.out.printf("%s\t", deptDTO.getD_ceo());
		System.out.printf("%s\t", deptDTO.getD_tel());
		System.out.printf("%s\n", deptDTO.getD_addr());
		
	}
	
	// List를 받아서 출력할 때 사용할 method
	protected void viewList(List<DeptDTO> deptList) {
		
		for(DeptDTO deptDTO : deptList) {
			
			this.viewList(deptDTO);
			
		}

		System.out.println("=================================================");
		
	}
	
	protected void viewDetail(DeptDTO deptDTO) {
		

		System.out.println("=================================================");
		System.out.println("상호 : " + deptDTO.getD_name());
		System.out.println("대표 : " + deptDTO.getD_ceo());
		System.out.println("전화 : " + deptDTO.getD_tel());
		System.out.println("주소 : " + deptDTO.getD_addr());
		System.out.println("업종 : 슈퍼마켓");
		System.out.println("업태 : 도,소매");
		System.out.println("사업자번호 : 409-01-0001");
		System.out.println("담당자 : 홍길동 ");
		System.out.println("담당자 직통번호 : 010-1234-5678");
		System.out.println("=================================================");
	
	}
	
}	

