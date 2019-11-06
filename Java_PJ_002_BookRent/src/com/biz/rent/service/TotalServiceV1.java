package com.biz.rent.service;

import java.util.Scanner;

public class TotalServiceV1 {
	
	BookServiceV1 bs = new BookServiceV1();
	UserServiceV1 us = new UserServiceV1();
	RentServiceV1 rs = new RentServiceV1();
	Scanner scan = new Scanner(System.in);
	
public void menu() {
		
	while(true) {

		System.out.println("===============================================");
		System.out.println("빛고을 Book Rent Management System 2019");
		System.out.println("===============================================");
		System.out.println("1.도서 서비스 2.회원 서비스 3.도서 대출/반납 서비스 0.종료");
		System.out.println("-----------------------------------------------");
		System.out.print("업무 선택 >> ");
		String strtMenu = scan.nextLine();
		
		int selectMenu = 0;
		
		try {
			selectMenu = Integer.valueOf(strtMenu);
		} catch (Exception e) {
			System.out.println("숫자만 입력해주세요.");
			continue;
			// TODO: handle exception
		}
		if(selectMenu == 0 ) {
			System.out.println("업무 종료!");
			break;
		}
		
		else if(selectMenu == 1) {
			
			bs.viewMenu();
		
		}else if(selectMenu == 2) {
			
			us.userMenu();
		}else if(selectMenu == 3){
			
			rs.menu();
		}else {
			System.out.println("다시 입력해주세요.");
		}
	}
}
}
