package com.biz.oracle.service;

import java.util.List;
import java.util.Scanner;

import com.biz.oracle.persistence.BookDTO;
import com.biz.oracle.persistence.dao.BookDao;
import com.biz.oracle.persistence.dao.BookDaoImp;

public class BookServiceV1 {
	
	// 객체 선언(아직 사용준비 전 단계)
	BookDao bookDao = null;
	Scanner scanner = null;

	// 클래스 생성자
	public BookServiceV1() {
		
		// 선언된 객체를 사용할 수 있도록 초기화
		// 초기화된 클래스 = 인스턴스(또는 인스턴스화 되었다.)
		scanner = new Scanner(System.in);
		bookDao = new BookDaoImp();
	
	}
	
	// 도서정보 전체리스트를 DB로부터 읽어서 console에 보이는 method
	public void viewBookList() {
		
		// dao의 selectAll() method를 호출하여
		// 전체 리스트를 DB로부터 가져와서 bookList에 받기
		List<BookDTO> bookList = bookDao.selectAll();
		
		// bookList에는 전체리스트가 담겨 있을것이므로
		// viewList() 전체리스트를 콘솔에 보일 것이다.
		this.viewList(bookList);
		
		
	}// viewBookList end
	
	// bookList를 매개변수로 받아서 console에 보이기
	private void viewList(List<BookDTO> bookList) {
		
		System.out.println("====================================================");
		System.out.println("전체 도서 리스트 v1");
		System.out.println("====================================================");
		System.out.println("코드\t도서명\t출판사\t저자\t가격");
		System.out.println("----------------------------------------------------");
		
		// bookList를 읽어서 그 값을 bookDTO dto에 담겠다.
		for(BookDTO dto : bookList) {
			System.out.printf("%s\t", dto.getB_code());
			System.out.printf("%s\t", dto.getB_name());
			System.out.printf("%s\t", dto.getB_comp());
			System.out.printf("%s\t", dto.getB_writer());
			System.out.printf("%d\n", dto.getB_price());
			
		}
		System.out.println("====================================================");
	}// end biewBookList
	
	
	/*
	 * 키보드에서 도서이름을 입력받아서 
	 * 리스트를 콘솔에 보이기
	 */
	
	// searchBookName() method를 두번 활용하기 위해 
	// 사용안할 매개변수로 boolean bConti를 줘버리고
	// if안의 조건이 true인지 false인지만 확인함
	public void searchBookName(boolean bConti) {
		
		while(true) {
				
			if(this.searchBookName() != null) break;
		}
		
		
	}
	
	public String searchBookName() {
		
		
		System.out.println("=========================================");
		System.out.println("도서검색");
		System.out.println("=========================================");
		System.out.print("도서명(-Q : quit) >> ");
		String strName = scanner.nextLine();
		if(strName.equals("-Q")) return "-Q" ;
		this.searchBookName(strName);
		return strName;
		}
	
	public boolean searchBookName(String strName) {
		
		List<BookDTO> bookList = bookDao.findByName(strName);
			
		if(bookList == null || bookList.size() < 1) {
			System.out.println("찾는 도서명이 없음!");
			return false;
			// continue;
			}
			
			// bookList에는 입력한 도서명에 해당하는 리스트가 담겨 있을것
			// viewList()는 검색조건에 맞는 리스트만 보일 것
			this.viewList(bookList);
			return true;
		}
		
		


	public void searchBookPrice() {

		while(true) {
			
			System.out.println("=========================================");
			System.out.println("도서가격검색");
			System.out.println("=========================================");
			
			try {
				
				System.out.print("시작가격(-Q : quit) >> ");
				String strSPrice = scanner.nextLine();
				if(strSPrice.equals("-Q")) break;
				
				int sPrice = Integer.valueOf(strSPrice);
				
				System.out.print("종료가격(-Q : quit) >> ");
				String strEPrice = scanner.nextLine();
				if(strEPrice.equals("-Q")) break;
				
				int ePrice = Integer.valueOf(strEPrice);
				
				List<BookDTO> bookList = bookDao.findByPrice(sPrice, ePrice);
				
				this.viewList(bookList);
				
			} catch (Exception e) {

				System.out.println("가격은 숫자로만 입력하세요");
				// 처음부터 다시 입력하기 위해 continue
				continue;
			}
			
		}
		
		
	}

	
}
