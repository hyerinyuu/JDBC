package com.biz.addr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.biz.persistence.AddrDTO;
import com.biz.persistence.dao.AddrDao;
import com.biz.persistence.dao.AddrDaoImp;

public class AddrServiceV1 {

	AddrDao addrDao = null;
	Scanner scan = null;
	
	public AddrServiceV1() {
		
		addrDao = new AddrDaoImp();
		scan = new Scanner(System.in);
		
	}
	public void viewAllList() {
		
		// dao의 selectAll() method를 호출하여
		// 전체 리스트를 DB로부터 가져와서 bookList에 받기
		List<AddrDTO> addrList = addrDao.selectAll();
		
		// bookList에는 전체리스트가 담겨 있을것이므로
		// viewList() 전체리스트를 콘솔에 보일 것이다.
		this.viewAddrList(addrList);
		
		
	}// viewBookList end
	/*
	 * selectAll();
	 findById(long id);
	 findByName(String name);
	 findByTel(String tel);
	 findByChain(String chain);
	 */
	// 모든 주소록 리스트를 보여주는 method
	public void viewAddrList(List<AddrDTO> addrList) {
		
		System.out.println("====================================================");
		System.out.println("주소록 전체 리스트");
		System.out.println("====================================================");
		System.out.println("아이디\t이름\t전화번호\t주소\t관계");
		System.out.println("----------------------------------------------------");
		for(AddrDTO addrDTO : addrList) {
			System.out.print(addrDTO.getId() + "\t");
			System.out.print(addrDTO.getName() + "\t");
			System.out.print(addrDTO.getTel() + "\t");
			System.out.print(addrDTO.getAddr() + "\t");
			System.out.print(addrDTO.getChain() + "\n");
		}
		System.out.println("====================================================");
		
	}
	
	
	
	// 키보드에서 이름을 입력받아서 해당하는 이름만 보여주는 method
	public void viewAddrName() {
	
		while(true) {
			
			System.out.println("====================================================");
			System.out.println("이름 검색");
			System.out.println("====================================================");
				
				System.out.print("이름(-Q : quit) >> ");
				String strName = scan.nextLine();
				if(strName.equals("-Q")) {
					System.out.println("검색종료");
					break;
				}
				
				List<AddrDTO> addrList = addrDao.findByName(strName);
				this.viewAddrList(addrList);
		
			
		}
	}
	
	// 키보드에서 전화번호 뒷자리를 입력받아서 해당 전화번호와 일치하는 리스트만 보여주는 method
	public void viewAddrTel() {
		
		while(true) {
			
			System.out.println("====================================================");
			System.out.println("전화번호 검색");
			System.out.println("====================================================");
			
			System.out.print("전화번호 (-Q : quit) >> ");
			String strTel = scan.nextLine();
			if(strTel.equals("-Q")) {
				System.out.println("검색종료");
				break;
			}
			
			List<AddrDTO> addrList = addrDao.findByTel(strTel);
			this.viewAddrList(addrList);
			
		}
		
	}
	

	// 키보드에서 관계를 입력받아서 해당 관계와 일치하는 리스트를 보여주는 method
	public void viewAddrChain() {
		
		while(true) {
			
			System.out.println("====================================================");
			System.out.println("관계 검색");
			System.out.println("====================================================");
			
			System.out.print("관계(-Q : quit) >> ");
			String strChain = scan.nextLine();
			if(strChain.equals("-Q")) {
				System.out.println("검색종료");
				break;
			}
			List<AddrDTO> addrList = addrDao.findByChain(strChain);
			this.viewAddrList(addrList);
			
		}
		
	}
	
}
