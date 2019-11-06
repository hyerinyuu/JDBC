package com.biz.oracle.service;

import java.util.Scanner;

import com.biz.oracle.persistence.BookDTO;
import com.biz.oracle.persistence.dao.BookDao;
import com.biz.oracle.persistence.dao.BookDaoImp;

public class BookCUDServiceV1 {

	private BookDao bookDao = null;
	private Scanner scanner = null;
	
	public BookCUDServiceV1() {

		scanner = new Scanner(System.in);
		bookDao = new BookDaoImp();
	}
	
	public void inputBook() {
		
		while(true) {
			System.out.println("=========================================");
			System.out.println("도서정보 등록");
			System.out.println("=========================================");
			
			String strB_Name = null;
			while(true) {
					System.out.print("1. 도서명(-Q : quit) >> ");
					strB_Name = scanner.nextLine();
					if(strB_Name.equals("-Q")) break;
					if(strB_Name.isEmpty()) {
						System.out.println("도서명은 반드시 입력해야합니다.");
						continue;
					}

				break;
			}
			if(strB_Name.equals("-Q")) break;
		
			System.out.print("2. 출판사(-Q : quit) >> ");
			String strB_comp = scanner.nextLine();
			if(strB_comp.equals("-Q")) break;
			
			System.out.print("3. 저자(-Q : quit) >> ");
			String strB_writer = scanner.nextLine();
			if(strB_writer.equals("-Q")) break;
			// 변수의 scope 잘 고민해보기
			String strB_price = null;
			int intB_Price = 0;
			
			while(true) {
				
				try {
					System.out.print("4. 가격(-Q : quit) >> ");
					strB_price = scanner.nextLine();
					intB_Price = Integer.valueOf(strB_price);
					
					if(strB_price.equals("-Q")) break;

				} catch (Exception e) {
					System.out.println("가격은 숫자로만 입력하세요");
					continue;
				}
				break;
			}
			if(strB_price.equals("-Q")) break;
			
			BookDTO bookDTO = BookDTO.builder()
					.b_name(strB_Name)
					.b_comp(strB_comp)
					.b_writer(strB_writer)
					.b_price(intB_Price)
					.build();
			
			int ret = bookDao.insert(bookDTO);
			if(ret > 0)
				System.out.println("도서정보 저장완료");
			else
				System.out.println("도서정보 저장실패");
		}
		
		
	}

	public void deleteBook() {

		while(true) {
			
			System.out.println("=========================================");
			System.out.print("삭제할 코드(-Q : quit) >> ");
			
			String strCode = scanner.nextLine();
			
			if(strCode.equals("-Q")) break;
			
			BookDTO bookDTO = bookDao.findById(strCode);
			if(bookDTO == null ) {
				System.out.println("코드명과 일치하는 도서가 없습니다.");
				continue;
			}
			
			int ret = bookDao.delete(strCode);
			
			
			
			
		}
		
		
	}
	
}
