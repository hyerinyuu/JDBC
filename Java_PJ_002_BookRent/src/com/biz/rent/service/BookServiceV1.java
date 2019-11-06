package com.biz.rent.service;

import java.util.List;
import java.util.Scanner;
import com.biz.rent.config.DBConnection;
import com.biz.rent.dao.BookDao;
import com.biz.rent.persistence.BookDTO;

public class BookServiceV1 {
	
	protected BookDao bookDao;
	List<BookDTO> bookList = null;
	Scanner scan;
	
	public BookServiceV1() {
		
		bookDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(BookDao.class);
		scan = new Scanner(System.in);
	}
	
	public void viewMenu(){
		// TODO 메뉴
		
		while(true) {
			System.out.println("=================================================");
			System.out.println("빛고을 도서 정보 서비스");
			System.out.println("=================================================");
			
			System.out.println("1.등록 2.수정 3.삭제 4.검색 0.종료");
			System.out.println("-------------------------------------------------");
			
			System.out.print("업무선택 >> ");
			String strMenu = scan.nextLine();
			
			int intMenu = 0;
			
			try {
				intMenu = Integer.valueOf(strMenu);
			} catch (Exception e) {
				System.out.println("0과 1~4 사이의 숫자만 입력하세요");
				continue;
			}
			
			
			
			if(intMenu == 0) {
				System.out.println("업무 종료");
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
	
	public void viewList(BookDTO bookDTO) {
		// TODO 리스트 한개만 보여주는 method
		this.viewDetail(bookDTO);
		
	}
	public BookDTO viewList(String b_code) {
		// TODO 코드를 매개변수로 받아서 책 한개의 리스트를 보여주는 method
		
		BookDTO bookDTO = bookDao.findById(b_code);
		
		if(bookDTO == null) return null;
		
		System.out.println("==============================================");
		System.out.printf("도서코드 : %s\n", bookDTO.getB_code());
		System.out.printf("도서명 : %s\n", bookDTO.getB_name());
		System.out.printf("저자 : %s\n", bookDTO.getB_auther());
		System.out.printf("출판사 : %s\n", bookDTO.getB_comp());
		System.out.printf("출판년도 : %d\n", bookDTO.getB_year());
		System.out.printf("매입가격 : %d\n", bookDTO.getB_iprice());
		System.out.printf("대여가격 : %d\n", bookDTO.getB_rprice());
		System.out.println("==============================================");
		
		return bookDTO;
		
	}
	
	public void viewDetail(BookDTO bookDTO) {
		
		System.out.printf("%s\t", bookDTO.getB_code());
		System.out.printf("%s\t", bookDTO.getB_name());
		System.out.printf("%s\t", bookDTO.getB_auther());
		System.out.printf("%s\t", bookDTO.getB_comp());
		System.out.printf("%d\t", bookDTO.getB_year());
		System.out.printf("%d\t", bookDTO.getB_iprice());
		System.out.printf("%d\n", bookDTO.getB_rprice());
	}
	
	public void viewList(List<BookDTO> bookList) {
		
		System.out.println("==============================================");
		
		for(BookDTO bookDTO : bookList) {
			this.viewDetail(bookDTO);
		}
		System.out.println("==============================================");
	}

	// 도서리스트를 모두 보여주는 method
	public void viewAllList() {
		// TODO AllList
		
		List<BookDTO> bookList = bookDao.selectAll();
		
		System.out.println("=================================================================");
		System.out.println("도서리스트");
		System.out.println("=================================================================");
		System.out.println("도서코드  도서명\t\t\t저자\t출판사\t출판년도\t구입가격\t대여가격");
		System.out.println("-----------------------------------------------------------------");
		
		for(BookDTO bookDTO : bookList) {
			this.viewList(bookDTO);
		}
		System.out.println("==============================================================");
	}

	// 도서코드(not null), 도서명(not null), 저자(not null), 출판사, 출판년도(not null), 구입가격, 대여가격(not null)
	// 도서코드 자동생성 o
	// 도서명 입력(중복 명이면 코드는 달라야함)  
	// 저자
	// 출판사 입력
	// 출판년도 x
	// 구입가격 굳이
	// 대여가격 
	
	
	// 새로운 도서를 등록하는 method
	public void insert() {
		// TODO insert
		
		System.out.println("===================================");
		System.out.println("도서 등록");
		System.out.println("===================================");
		
		String strBcode;
		while(true) {
			
			System.out.print("도서코드(Enter : 자동생성, -Q : quit) >> ");
			strBcode = scan.nextLine();
			
			if(strBcode.equals("-Q")) {
				System.out.println("업무 종료");
				return;
			}
			if(strBcode.trim().isEmpty()) {
				
				// 코드 자동생성
				String strTMPcode = bookDao.getMaxCode();
				int intBcode = Integer.valueOf(strTMPcode.substring(2));
				
				// 620
				intBcode ++;
				// 도서코드 앞 두자리 문자열 자르기 strBcode == BK
				strBcode = strTMPcode.substring(0, 2);
				// BK0620
				strBcode += String.format("%04d", intBcode);
				
				System.out.println("생성된 코드 : " + strBcode);
				System.out.print("사용하시겠습니까?(Yes : Enter, No : 코드입력) >> ");
				String strYesNo = scan.nextLine();
				if(strYesNo.trim().isEmpty()) break;
				else continue;
			}
			if(strBcode.length() != 6){
				System.out.println("도서 코드는 6자리로 이루어져야합니다");
				continue;
			}
			if(!strBcode.subSequence(0, 2).equals("BK")) {
				System.out.println("도서코드는 BK로 시작해야합니다");
				continue;
			}
			try {
				Integer.valueOf(strBcode.substring(2));
			} catch (Exception e) {
				System.out.println("도서코드는 'BK0000' 형식으로 등록되어야합니다");
				continue;
			}
			if(bookDao.findById(strBcode) != null) {
				System.out.println("이미 존재하는 코드입니다");
				continue;
			}
			break;
			
		}// 도서코드 검증 반복 end
		
		// 도서명 입력(중복 허용x)
		
		String strBname;
		while(true) {
			
			System.out.print("도서명(-Q : quit) >> ");
			strBname = scan.nextLine();
			if(strBname.equals("-Q")) {
				System.out.println("입력 종료");
				return;
			}else if(strBname.trim().isEmpty()) {
				System.out.println("도서명은 반드시 입력하셔야합니다");
				continue;
			}
			if(bookDao.findByEName(strBname) != null) {
				System.out.println("이미 존재하는 도서명입니다");
				continue;
			}
			break;
			
		}// 도서명 검증 반복 end
		
		// 저자
		String strAname;
		while(true) {
			
			System.out.print("저자(-Q : quit) >> ");
			strAname = scan.nextLine();
			if(strAname.equals("-Q")) return;
			if(strAname.trim().isEmpty()) {
				System.out.println("저자명은 반드시 입력하셔야합니다.");
				continue;
			}
			break;
		}// 저자 입력 종료
		
		String strComp;
		// 출판사 입력
		while(true) {
			
			System.out.print("출판사(-Q : quit) >> ");
			strComp = scan.nextLine();
			if(strComp.equals("-Q")) {
				System.out.println("입력 종료");
				return;
			}

			break;
		}
		
		// 출판년도 입력
		long longYear = 0;
		while(true) {
			
			System.out.print("출판년도(-Q : quit) >> ");
			String strYear = scan.nextLine();
			
			if(strYear.equals("-Q")) return;
			if(strYear.trim().isEmpty()) {
				System.out.println("출판년도는 반드시 입력하셔야합니다");
				continue;
			}
			try {
				longYear = Long.valueOf(strYear); 
			} catch (Exception e) {
				System.out.println("출판년도는 숫자만 입력하세요");
				continue;
			}
			break;
		}
		// 구입가격
		
		int intIprice = 0;
		while(true) {
			
			System.out.print("구입가격(-Q : quit) >> ");
			String strIprice = scan.nextLine();
			
			if(strIprice.equals("-Q"))return;
			try {
				intIprice = Integer.valueOf(strIprice);
			} catch (Exception e) {
				System.out.println("구입가격은 숫자만 입력하세요");
				continue;
			}
			
			break;
		}
		
		
		// 대여가격(not null)
	
		int intRprice = 0;
		while(true) {
			
			System.out.print("대여가격(-Q : quit) >> ");
			String strRprice = scan.nextLine();
			
			if(strRprice.equals("-Q")) return;
			if(strRprice.trim().isEmpty()) {
				System.out.println("대여가격은 반드시 입력하셔야합니다.");
				continue;
			}
			try {
				intRprice = Integer.valueOf(strRprice);
			} catch (Exception e) {
				System.out.println("대여가격은 숫자만 입력하세요");
				continue;
			}
			break;
		}
		
		BookDTO bookDTO = BookDTO.builder()
				.b_code(strBcode)
				.b_name(strBname)
				.b_auther(strAname)
				.b_comp(strComp)
				.b_year(longYear)
				.b_iprice(intIprice)
				.b_rprice(intRprice)
				.build();
		
		int ret = bookDao.insert(bookDTO);
		if(ret > 0) {
			System.out.println("등록 완료");
		}else {
			System.out.println("등록 실패");
		}
		
	}

	// 도서코드를 입력받아서 도서정보를 수정하는 method
	public void update() {
		// TODO update
		
		System.out.println("===================================");
		System.out.println("도서 수정");
		System.out.println("===================================");
		
		String searchBname = null;
		
		while(true) {
			
			System.out.print("도서명 >> ");
			searchBname = scan.nextLine();
			bookList = bookDao.findByName(searchBname);
			if(bookList.size() > 0) {
				this.viewList(bookList);
			}else {
				System.out.println("도서명을 다시 확인하세요");
				continue;
			}
			
			break;
		}
		BookDTO bookDTO = null;
		String strBcode;
		while(true) {
			
			System.out.print("수정할 도서코드(Q : quit) >> ");
			strBcode = scan.nextLine();
			
			if(strBcode.equals("-Q")) {   
				System.out.println("업무 종료");
				return;
			}else if(strBcode.trim().isEmpty()) {
				System.out.println("수정할 도서의 코드를 입력하세요");
				continue;
			}
			
			
			bookDTO = bookDao.findById(strBcode);
			
			this.viewList(bookDTO);
			
			System.out.println("이 도서의 수정을 계속 하시겠습니까?(Enter : 계속) >> ");
			String strYesNo = scan.nextLine();
			if(strYesNo.trim().isEmpty()) {
				break;
			}
		}
		String strBname;
		while(true) {
			
			System.out.printf("도서명[%s] (-Q : quit) >> ", bookDTO.getB_name());
			strBname = scan.nextLine();
			if(strBname.equals("-Q")) {
				System.out.println("입력 종료");
				return;
			}else if(strBname.trim().isEmpty()) {
				break;
			}
			if(bookDao.findByEName(strBname) != null) {
				System.out.println("이미 존재하는 도서명입니다");
				continue;
			}
			bookDTO.setB_name(strBname);
			break;
			
		}// 도서명 검증 반복 end
		
		// 저자
		String strAname;
		while(true) {
			
			System.out.printf("저자[%s](-Q : quit) >> " , bookDTO.getB_auther());
			strAname = scan.nextLine();
			if(strAname.equals("-Q")) return;
			if(strAname.trim().isEmpty()) {
				break;
			}
			bookDTO.setB_auther(strAname);
			break;
		}// 저자 입력 종료
		
		String strComp;
		// 출판사 입력
		while(true) {
			
			System.out.printf("출판사[%s](-Q : quit) >> ", bookDTO.getB_comp());
			strComp = scan.nextLine();
			if(strComp.equals("-Q")) {
				System.out.println("입력 종료");
				return;
			}if(strComp.trim().isEmpty()) {
				break;
			}
			
			bookDTO.setB_comp(strComp);
			break;
		}
		
		// 출판년도 입력
		long longYear = 0;
		while(true) {
			
			System.out.printf("출판년도[%d](-Q : quit) >> ", bookDTO.getB_year());
			String strYear = scan.nextLine();
			
			if(strYear.equals("-Q")) return;
			if(strYear.trim().isEmpty()) {
				break;
			}
			try {
				longYear = Long.valueOf(strYear);
				bookDTO.setB_year(longYear);
				break;
			} catch (Exception e) {
				System.out.println("출판년도는 숫자만 입력하세요");
				continue;
			}
			
		}
		// 구입가격
		
		int intIprice = 0;
		while(true) {
			
			System.out.printf("구입가격[%d](-Q : quit) >> ", bookDTO.getB_iprice());
			String strIprice = scan.nextLine();
			
			if(strIprice.equals("-Q")) return;
			
			if(strIprice.trim().isEmpty()) break;
			
			try {
				intIprice = Integer.valueOf(strIprice);
				bookDTO.setB_iprice(intIprice);
				break;
			} catch (Exception e) {
				System.out.println("구입가격은 숫자만 입력하세요");
				continue;
			}
		}
		
		
		// 대여가격(not null)
		int intRprice = 0;
		while(true) {
			
			System.out.printf("대여가격[%d](-Q : quit) >> ", bookDTO.getB_rprice());
			String strRprice = scan.nextLine();
			
			if(strRprice.equals("-Q")) return;
			if(strRprice.trim().isEmpty()) {
				break;
			}
			try {
				intRprice = Integer.valueOf(strRprice);
				bookDTO.setB_rprice(intRprice);
				break;
			} catch (Exception e) {
				System.out.println("대여가격은 숫자만 입력하세요");
				continue;
			}
		}
		
		// 수정된 목록 보여주기
		this.viewList(bookDTO);
		
		System.out.print("이대로 수정하시겠습니까?(Yes : Enter, NO : n) >> ");
		String strYesNo = scan.nextLine();
		
		if(strYesNo.trim().isEmpty()) {

			int ret = bookDao.update(bookDTO);
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
	
	// 도서를 삭제할 수 있는 method
	// 도서명을 입력해서 도서코드를 보여주고 도서코드로 삭제할 수 있게 하기
	public void delete(){
		// TODO delete
		
		while(true) {
			
			System.out.print("삭제할 도서명(Enter : 전체리스트) >> ");
			String strBname = scan.nextLine();
			
			List<BookDTO> bookList = null;
			if(strBname.trim().isEmpty()) {
				//enter를 입력하면 전체리스트를 보여주기
				bookList = bookDao.selectAll();
				
			// 상품명을 입력했으면	
			}else if(bookDao.findByName(strBname) != null){
				bookList = bookDao.findByName(strBname);
				
			// 일치하는 도서가 없으면	
			}else if(bookDao.findByName(strBname) == null) {
				System.out.println("일치하는 도서가 없습니다.");
				continue;
			}
			
			for(BookDTO bookDTO : bookList) {
			
				this.viewList(bookDTO);
			}
			break;
		}
		
		
		while(true) {

			System.out.print("삭제할 도서 코드(-Q : quit) >> ");
			String strBcode = scan.nextLine();
			
			BookDTO bookDTO = this.viewList(strBcode);
			
			if(bookDTO == null) {
				System.out.println("일치하는 코드가 없습니다.");
				continue;
			}
			
			System.out.print("삭제 하시겠습니까?(Y/N) >> ");
			String strYesNo = scan.nextLine();
			if(!strYesNo.equalsIgnoreCase("Y")) {
				
				System.out.println("삭제 취소");
				System.out.print("다른 코드로 도서 삭제를 계속 하시겠습니까?(Y/N) >> ");
				String strYN = scan.nextLine();
				if(strYN.equalsIgnoreCase("Y")) {
					continue;
				}else {
					System.out.println("메뉴 선택으로 돌아갑니다");
					break;
				}
			}
			
			int ret = bookDao.delete(strBcode);
			
			if(ret > 0) {
				System.out.println("삭제 완료");
			}else {
				System.out.println("삭제 실패");
			}
			
			
		}
		
	}
	
	// 제목과 저자를 입력받아서 도서를 검색할 수 있는 method
	public void search() {
		// TODO search

			System.out.println("===========================================");
			System.out.println("도서검색");
			System.out.println("===========================================");

			while(true) {
				
			System.out.print("B : 도서명으로 검색 , A : 저자로 검색 >> ");
			String strBA = scan.nextLine();
			
			if(strBA.equalsIgnoreCase("B")) {
				
				while(true) {
					System.out.println("===================================");
					System.out.println("도서명 검색");
					System.out.println("===================================");
					
					System.out.print("도서명 >> ");
					String strBname = scan.nextLine();
					
					bookList = bookDao.findByName(strBname);
					
					if(bookList.size() > 0) {
						this.viewList(bookList);
					}else {
						System.out.println("일치하는 도서명이 없습니다");
						break;
					}
					break;
				}
				
			}else if(strBA.equalsIgnoreCase("A")){
				
				while(true) {
					
					System.out.println("===================================");
					System.out.println("저자 검색");
					System.out.println("===================================");
					
					System.out.print("저자 >> ");
					String strAname = scan.nextLine();
					
					List<BookDTO> bookList = bookDao.findByAuther(strAname);
					
					if(bookList.size() > 0) {
						bookList = bookDao.findByAuther(strAname);
						this.viewList(bookList);
					}else {
						System.out.println("일치하는 저자가 없습니다");
						break;
					}
					break;
				}
				
			}
			break;
			}
	}		

}