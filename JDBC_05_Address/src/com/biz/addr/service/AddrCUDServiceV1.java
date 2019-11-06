package com.biz.addr.service;

import java.util.Scanner;

import com.biz.persistence.AddrDTO;
import com.biz.persistence.dao.AddrDao;
import com.biz.persistence.dao.AddrDaoImp;

public class AddrCUDServiceV1 {

	private AddrDao addrDao = null;
	private Scanner scan = null;
	
	public AddrCUDServiceV1() {
		
		addrDao = new AddrDaoImp();
		scan = new Scanner(System.in);
		
	}
	
	// 키보드에서 이름, 주소, 전화번호, 관계를 입력받아서 tbl_addr에 INSERT하는 method
	public void inputAddr() {
		
		while(true) {
			
			System.out.println("====================================================");
			System.out.println("주소록 입력");
			System.out.println("====================================================");
			
			while(true) {
				
				System.out.print("1. 이름(-Q : quit) >> ");
				String strName = scan.nextLine();
				if(strName.equals("-Q")) {
					System.out.println("입력 종료");
					break;
				}
				if(strName.isEmpty()) {
					System.out.print("이름은 반드시 입력해야합니다");
					continue;
				}
				
				System.out.print("2. 전화번호(-Q : quit) >> ");
				String strTel = scan.nextLine();
				if(strTel.equals("-Q")) {
					System.out.println("입력 종료");
					break;
				}
				
				System.out.print("3. 주소(-Q : quit) >> ");
				String strAddr = scan.nextLine();
				if(strAddr.equals("-Q")) {
					System.out.println("입력 종료");
					break;
				}
				
				System.out.print("4. 관계(-Q : quit) >> ");
				String strChain = scan.nextLine();
				if(strChain.equals("-Q")) {
					System.out.println("입력 종료");
					break;
				}
				
				AddrDTO addrDTO = AddrDTO.builder()
						.name(strName)
						.tel(strTel)
						.addr(strAddr)
						.chain(strChain)
						.build();
				int ret = addrDao.insert(addrDTO);
				if(ret > 0 ) {
					System.out.println("주소정보 저장 완료");
				}else {
					System.out.println("주소정보 저장 실패");
				}
			}
			
			
		}
		
	}
	
	
	public void updateAddr() {
		
		
	}
	
	public void deleteAddr() {
		
		while(true) {
			
			System.out.println("====================================================");
			System.out.println("주소 삭제");
			System.out.println("====================================================");
			
			System.out.print("삭제할 아이디(-Q : quit) >> ");
			String strid = scan.nextLine();
			long longId = Long.valueOf(strid);
			
			if(strid.equals("-Q"))break;
			
			AddrDTO addrDTO = addrDao.findByID(longId);
			if(addrDTO == null) {
				System.out.println("일치하는 주소가 없습니다.");
				continue;
			}
			int ret = addrDao.delete(longId);
			System.out.println("주소 삭제 완료!");
		}
		
	}
	
	
}
