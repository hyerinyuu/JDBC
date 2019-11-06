package com.biz.rent.service;

import java.util.List;
import java.util.Scanner;

import com.biz.rent.config.DBConnection;
import com.biz.rent.dao.UserDao;
import com.biz.rent.persistence.BookDTO;
import com.biz.rent.persistence.UserDTO;

public class UserServiceV1 {

	protected UserDao userDao;
	List<UserDTO> userList = null;
	Scanner scan;
	
	public UserServiceV1() {
		
		userDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(UserDao.class);
		scan = new Scanner(System.in);
	}
	
	public void userMenu() {
		
		while(true) {
			System.out.println("============================================");
			System.out.println("빛고을 회원 정보 서비스");
			System.out.println("============================================");
			System.out.println("1.회원검색  2.회원등록  3.회원수정  0.종료");
			System.out.println("--------------------------------------------");
			System.out.print("업무 선택 >> ");
			String strtMenu = scan.nextLine();
			int SelectMenu = 0;
			try {
				SelectMenu = Integer.valueOf(strtMenu);
			} catch (Exception e) {
				System.out.println("숫자만 입력해주세요.");
				continue;
				// TODO: handle exception
			}
			if(SelectMenu == 0 ) {
				System.out.println("업무 종료!");
				break;
			}
			else if(SelectMenu == 1) {
				this.search();
			}else if(SelectMenu == 2) {
				this.insert();
			}else if(SelectMenu == 3) {
				this.update();
			}else {
				System.out.println("다시 입력해주세요.");
			}
		}
	}
	
	public void viewDetail(UserDTO userDTO) {

		System.out.printf("회원 코드 : %s\n", userDTO.getU_code());
		System.out.printf("회원 이름 : %s\n", userDTO.getU_name());
		System.out.printf("회원 전화번호 : %s\n", userDTO.getU_tel());
		System.out.printf("회원 주소 : %s\n", userDTO.getU_addr());
		
		System.out.println("---------------------------------------------");
		
	}
	
	public void viewDTO(UserDTO userDTO) {
		
		System.out.printf("%s\t", userDTO.getU_code());
		System.out.printf("%s\t", userDTO.getU_name());
		System.out.printf("%s\t", userDTO.getU_tel());
		System.out.printf("%s\n", userDTO.getU_addr());
		
	}
	public void viewList(List<UserDTO> userList) {
		
		for(UserDTO userDTO : userList) {
			
			this.viewDetail(userDTO);
		}
	}
	

	private void search() {
		// TODO search

		System.out.println("===============================================");
		System.out.println("회원 정보 검색");
		System.out.println("===============================================");
		
		
		String strNT;
		while(true) {
			
			System.out.print("N : 회원명 검색 , T : 회원전화번호 검색 >> ");
			strNT = scan.nextLine();
			
			if(strNT.equalsIgnoreCase("N")) {
				System.out.println("===============================================");
				System.out.print("회원명 >> ");
				String strUname = scan.nextLine();
				
				userList = userDao.findByName(strUname);
				if(userList.size() > 0) {
					this.viewList(userList);
				}else {
					System.out.println("일치하는 회원이 없습니다");
					return;
				}
				
			}else if(strNT.equalsIgnoreCase("T")) {
				System.out.println("===============================================");
				System.out.print("전화번호 >> ");
				String strUTel = scan.nextLine();
				
				userList = userDao.findByTel(strUTel);
				if(userList.size() > 0) {
					this.viewList(userList);
				}else {
					System.out.println("일치하는 회원이 없습니다");
					return;
				}
			}else if(strNT.trim().isEmpty()) {
				System.out.println("검색어를 입력하세요");
				continue;
			}
			else {
				System.out.println("N과 T만 입력하세요");
				continue;
			}
			break;
		}
		
	}

	private void insert() {
		// TODO insert
		
		System.out.println("===============================================");
		System.out.println("회원 등록");
		System.out.println("===============================================");
		
		// 회원코드
		String strUcode;
		while(true) {
			
			System.out.print("회원 코드(Enter : 자동생성, -Q : quit ) >> ");
			strUcode = scan.nextLine();
			
			if(strUcode.equals("-Q")) {
				break;
			}
			if(strUcode.trim().isEmpty()) {
				
				String strMaxCode = userDao.getMaxCode();
				int intUcode = Integer.valueOf(strMaxCode.substring(1));
				
				intUcode ++;
				strUcode = strMaxCode.substring(0, 1);
				
				strUcode += String.format("%05d", intUcode);
				
				System.out.println("생성된 코드 : " + strUcode);
				System.out.print("사용하시겠습니까?(Yes : Enter, No : 코드입력) >> ");
				String strYesNo = scan.nextLine();
				if(strYesNo.trim().isEmpty()) break;
				else continue;
			}
			if(strUcode.length() != 6){
				System.out.println("회원 코드는 6자리로 이루어져야합니다");
				continue;
			}
			if(!strUcode.subSequence(0, 1).equals("S")) {
				System.out.println("회원코드는 S로 시작해야합니다");
				continue;
			}
			try {
				Integer.valueOf(strUcode.substring(1));
			} catch (Exception e) {
				System.out.println("도서코드는 'B00000' 형식으로 등록되어야합니다");
				continue;
			}
			if(userDao.findById(strUcode) != null) {
				System.out.println("이미 존재하는 코드입니다");
				continue;
			}
			break;
		}
		
		// 회원 이름
		String strUname;
		while(true) {
			
			System.out.print("회원 이름(-Q : quit) >> ");
			strUname = scan.nextLine();
			if(strUname.equals("-Q")) {
				System.out.println("등록 취소");
				return;
			}else if(strUname.trim().isEmpty()) {
				System.out.print("이름은 반드시 입력하셔야합니다");
				continue;
				
			}
			break;
		}
		
		// 회원 전화번호
		String strUTel;
		while(true) {
		
			System.out.print("회원 전화번호(-Q : quit) >> ");
			strUTel = scan.nextLine();
			if(strUTel.equals("-Q")) {
				System.out.println("등록 취소");
				return;
			}
			// 이름이 있고 전화번호가 없으면(둘다 만족)
			if(userDao.findByNameAndTel(strUname, strUTel).size() < 1) {
				System.out.println(strUTel);
				System.out.println("동명이인 등록 완료");
			
			// 이름이 있고 전화번호도 있으면(둘다 만족)
			}else if(userDao.findByNameAndTel(strUname, strUTel).size() > 0){
				System.out.println("이름과 전화번호가 동일한 회원이 이미 존재합니다");
				continue;
				
			}
			break;
		}
		
		// 회원 주소
		String strUAddr;
		while(true) {
			
			System.out.print("회원 주소(-Q : quit) >> ");
			strUAddr = scan.nextLine();
			if(strUAddr.equals("-Q")) {
				System.out.println("등록 취소");
				return;
			}
			
			break;
		}
				
		UserDTO userDTO = UserDTO.builder()
				.u_code(strUcode)
				.u_name(strUname)
				.u_tel(strUTel)
				.u_addr(strUAddr)
				.build();
		int ret = userDao.insert(userDTO);
		if(ret > 0 ) {
			System.out.println("등록 성공");
		}else {
			System.out.println("등록 실패");
		}
	}

	private void update() {
		// TODO update
		System.out.println("===================================");
		System.out.println("회원 정보 수정");
		System.out.println("===================================");
		
		String searchUname = null;
		
		while(true) {
			
			System.out.print("회원명 >> ");
			searchUname = scan.nextLine();
			userList = userDao.findByName(searchUname);
			if(userList.size() > 0) {
				this.viewList(userList);
			}else {
				System.out.println("회원명을 다시 확인하세요");
				continue;
			}
			
			break;
		}
		
		UserDTO userDTO = null;
		String strUcode;
		while(true) {
			
			System.out.print("수정할 회원코드(Q : quit) >> ");
			strUcode = scan.nextLine();
			
			if(strUcode.equals("-Q")) {   
				System.out.println("업무 종료");
				return;
			}else if(strUcode.trim().isEmpty()) {
				System.out.println("수정할 회원의 코드를 입력하세요");
				continue;
			}
			
			
			userDTO = userDao.findById(strUcode);
			
			this.viewDTO(userDTO);
			
			System.out.println("이 회원의 수정을 계속 하시겠습니까?(Enter : 계속) >> ");
			String strYesNo = scan.nextLine();
			if(strYesNo.trim().isEmpty()) {
				break;
			}else {
				System.out.println("수정종료");
				return;
			}
		}
		
		// 이름 
		String strUname;
		while(true) {
			
			System.out.printf("회원이름[%s] (-Q : quit) >> ", userDTO.getU_name());
			strUname = scan.nextLine();
			if(strUname.equals("-Q")) {
				System.out.println("입력 종료");
				return;
			}else if(strUname.trim().isEmpty()) {
				break;
			}
			userDTO.setU_name(strUname);
			break;
			
		}
		
		//  전화번호
		String strUtel;
		while(true) {
			
			System.out.printf("회원 전화번호[%s] (-Q : quit) >> ", userDTO.getU_tel());
			strUtel = scan.nextLine();
			if(strUname.equals("-Q")) {
				System.out.println("입력 종료");
				return;
			}else if(strUtel.trim().isEmpty()) {
				break;
			}
			userDTO.setU_tel(strUtel);
			
			break;
		}
		
		// 주소
		String strUaddr;
		while(true) {
			
			System.out.printf("회원 주소[%s] (-Q : quit) >> ", userDTO.getU_addr());
			strUaddr = scan.nextLine();
			if(strUname.equals("-Q")) {
				System.out.println("입력 종료");
				return;
			}else if(strUaddr.trim().isEmpty()) {
				break;
			}
			userDTO.setU_addr(strUaddr);
			
			break;
		}
		
		this.viewDTO(userDTO);
		
		System.out.print("이대로 수정하시겠습니까?(Yes : Enter, NO : n) >> ");
		String strYesNo = scan.nextLine();
		
		if(strYesNo.trim().isEmpty()) {

			int ret = userDao.update(userDTO);
			if(ret > 0) {
				System.out.println("수정 완료");
			}else {
				System.out.println("수정 실패");
			}
		}else {
			System.out.println("수정 종료");
			return;
		}
		
	}
	
	
}
