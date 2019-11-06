package com.biz.rent.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.ibatis.session.SqlSessionFactory;

import com.biz.rent.config.DBConnection;
import com.biz.rent.dao.BookDao;
import com.biz.rent.dao.RentDao;
import com.biz.rent.dao.UserDao;
import com.biz.rent.dao.ViewDao;
import com.biz.rent.persistence.BookDTO;
import com.biz.rent.persistence.RentDTO;
import com.biz.rent.persistence.RentviewVO;
import com.biz.rent.persistence.UserDTO;

public class RentServiceV1 {

	SqlSessionFactory sqlSessionFactory;
	RentDao rentDao = null;
	BookDao bookDao = null;
	UserDao userDao = null;
	ViewDao viewDao = null;
	
	Scanner scan = null;
	List<RentDTO> rentList = null;
	SimpleDateFormat sd;
	BookServiceV1 bs;
	UserServiceV1 us;
	RentDTO rentDTO = null;
	RentviewVO rentVO = null;
	
	
	
	public RentServiceV1() {
		
		rentDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(RentDao.class);
		bookDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(BookDao.class);
		userDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(UserDao.class);
		viewDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(ViewDao.class);
				
		rentDTO = new RentDTO();
		rentVO = new RentviewVO();
		
		
		bs = new BookServiceV1();
		us = new UserServiceV1();
		
		scan = new Scanner(System.in);
	}

	public void menu() {
		
		while(true) {

			System.out.println("============================================");
			System.out.println("빛고을 도서 대여 서비스");
			System.out.println("============================================");
			System.out.println("1.대출 2.반납  0.종료");
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
			// 도서 대출 리스트에 대출목록 추가하는 거니까 insert
			else if(SelectMenu == 1) {
				this.rentInsert();
			// 반납한 도서 대출리스트에서 반납으로 update	
			}else if(SelectMenu == 2) {
				this.returnUpdate();
			}else {
				System.out.println("다시 입력해주세요.");
			}
		}
	}
	
	
	public void viewUserDetail(UserDTO userDTO) {

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
	public void viewUserList(List<UserDTO> userList) {
		
		for(UserDTO userDTO : userList) {
			
			this.viewUserDetail(userDTO);
		}
	}
	
	public void viewBookDetail(BookDTO bookDTO) {
		
		System.out.printf("도서 코드 : %s\n", bookDTO.getB_code());
		System.out.printf("도서 이름 : %s\n", bookDTO.getB_name());
		System.out.printf("도서 저자 : %s\n", bookDTO.getB_auther());
		System.out.printf("도서 출판사 : %s\n", bookDTO.getB_comp());
		System.out.printf("도서 출판년도 : %d\n", bookDTO.getB_year());
		System.out.printf("도서 매입가격 : %d\n", bookDTO.getB_iprice());
		System.out.printf("도서 대출가격 : %d\n", bookDTO.getB_rprice());
		
		System.out.println("-----------------------------------------------------------");
	}
	
	public void viewBookList(List<BookDTO> bookList) {
		
		
		for(BookDTO bookDTO : bookList) {
			this.viewBookDetail(bookDTO);
		}
	
	}
	
	public void viewBookDTO(BookDTO bookDTO) {
		
		this.viewBookDetail(bookDTO);
		
	}
	
	public void viewDetail(RentDTO rentDTO) {
		
		System.out.printf("SEQ : %d ", rentDTO.getRent_seq());
		System.out.printf("대출일 : %s ", rentDTO.getRent_date());
		System.out.printf("반납예정일 : %s ", rentDTO.getRent_return_date());
		System.out.printf("도서코드 : %s ", rentDTO.getRent_bcode());
		System.out.printf("회원코드 : %s ", rentDTO.getRent_ucode());
		System.out.printf("도서반납여부 : %s ", rentDTO.getRent_return_yn());
		System.out.printf("포인트 : %d ", rentDTO.getRent_point());
		
	}
	
	// 
	protected void viewRentVO(RentviewVO rentVO) {
		System.out.println("=============================================");
		System.out.println("SEQ : " + rentVO.getRent_seq());
		System.out.println("회원이름 : " + rentVO.getU_name());
		System.out.println("회원코드 : " + rentVO.getU_code());
		System.out.println("도서명 : " + rentVO.getB_name());
		System.out.println("도서코드 : " + rentVO.getB_code());
		System.out.println("대여일 : " + rentVO.getRent_date());
		String rent = rentVO.getRent_return_yn() == null ? "미반납" : "반납";
		System.out.println("반납여부 : " + rent);
		System.out.println("=============================================");
	}
	

	// 대출하고 싶은 도서 이름을 입력받아서
	// 키워드가 포함된 도서 리스트를 보여주고
	// 대출 여부를 물어본 다음
	// 회원번호를 입력받아서 누가 대출할지 세팅해주고
	// 도서 반납 여부 변경
	private void rentInsert() {
		// TODO 대출
		System.out.println("============================================");
		System.out.println("도서 대출 서비스");
		System.out.println("============================================");
		
		
		// 도서명을 입력 받아서 도서리스트 보여주기
		while(true) {

			System.out.print("도서명 >> ");
			String strBname = scan.nextLine();
			List<BookDTO> bookList = null;
			
			// 키워드가 포함된 도서가 있을 경우
			if(bookDao.findByName(strBname).size() > 0) {
				bookList = bookDao.findByName(strBname);
			
			// 키워드가 포함된 도서가 없을 경우	
			}else {
				System.out.println("도서명을 다시 확인하고 입력해주세요");
				continue;
			}
			
				this.viewBookList(bookList);
			
			break;
		}
		
		String strBcode;
		String strRentYN;
		
		while(true) {
		
			BookDTO bookDTO = null;
			System.out.print("대출하려는 도서의 코드를 입력하세요 >> ");
			strBcode = scan.nextLine();
			strBcode = strBcode.toUpperCase();
			
			try {
				bookDTO = bookDao.findById(strBcode);
				this.viewBookDTO(bookDTO);
			} catch (Exception e) {
				System.out.println("도서코드를 다시 확인하고 입력하세요");
				continue;
			}
			
			List<RentDTO> rentList = rentDao.rentCheck(strBcode);
			if(rentList.size() >0) {
				System.out.println("이미 대출 된 도서입니다");
				continue;
			}
			rentDTO.setRent_bcode(strBcode);
		    break;
		}
		
		while(true) {
			
			// 대출을 하면 rentList에 도서정보(도서정보,회원정보,대여일,반납예정일,반납여부) 저장하기
			System.out.print("이 도서를 대출 하시겠습니까?(Yes : Enter, NO : N) >> ");
			strRentYN = scan.nextLine();
			
			try {
				if(strRentYN.trim().isEmpty()) {
					rentDTO.setRent_return_yn(null);
				}else if(strRentYN.equalsIgnoreCase("N")) {
					return;
				}
			} catch (Exception e) {
				System.out.println("Enter와 N 중에서만 입력하세요");
				return;
			}
			
			break;
		}
		// 대출정보 등록
		while(true) {
					
			if(strRentYN.trim().isEmpty()) {
				System.out.println("================================================");
				System.out.println("대출 회원 정보 등록");
				System.out.println("================================================");
				
				UserDTO userDTO = null;
				System.out.print("회원코드(-Q) >> ");
				String strUcode = scan.nextLine();
						
				if(strUcode.equals("-Q")) {
					System.out.println("업무 종료");
					return;
				}

				if(userDao.findById(strUcode) == null) {
					System.out.println("회원코드를 다시 확인하고 입력하세요");
					continue;
				}else {
					userDTO = userDao.findById(strUcode);
					this.viewUserDetail(userDTO);
			
					System.out.print("회원정보를 확인해주세요(Enter : 계속진행, No : N) >> ");
					String strYN = scan.nextLine();
					if(strYN.equalsIgnoreCase("N")) {
						return;
					}
					try {
						if(strYN.trim().isEmpty()) {
							rentDTO.setRent_ucode(strUcode);
						}
					} catch (Exception e) {
						System.out.println("Enter와 N만 입력하세요");
						continue;
						}
					}
				}
					break;
				
			}
				
				Calendar cal = Calendar.getInstance();
				Date date = new Date(System.currentTimeMillis());
				cal.setTime(date);
				cal.add(Calendar.DATE, 14);
				SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
				String rentDate = sd.format(date);
				String returnDate = sd.format(cal.getTime());
				
				rentDTO.setRent_date(rentDate);
				rentDTO.setRent_return_date(returnDate);
				
				int ret = rentDao.insert(rentDTO);
				
				if(ret > 0) {
					System.out.println("대출 등록 성공");
				}else {
					System.out.println("대출 등록 실패");
				}
				
				/*
				//TODO 대출/반납일자 설정
				while(true) {
					
					System.out.println("대출일자(Enter : 현재날짜, YYYY-MM-DD) >> ");
					String strRentDate = scan.nextLine();
					
					if(strRentDate.trim().isEmpty()) {
						cal.setTime(date);
						cal.add(Calendar.DATE, 14);
						String rentDate = sd.format(date);
						String returnDate = sd.format(cal.getTime());
						
						rentDTO.setRent_date(rentDate);
						rentDTO.setRent_return_date(returnDate);
					}
					
					try {
						
					} catch (Exception e) {
						System.out.println("날짜는 YYYY-MM-DD 형식으로 입력해주세요 ");
						continue;
					}
				}
				*/
			}//  first while end
	
	

	// seq를 입력받아서 대여리스트에 있으면
	// 대여 여부를 null -> Y로 변경
	private void returnUpdate() {
		// TODO 반납
		
		System.out.println("================================================");
		System.out.println("도서 반납 서비스");
		System.out.println("================================================");
		
		
		while(true) {
			
			System.out.print("반납 도서 SEQ(-Q : quit) >> ");
			String strSEQ = scan.nextLine();
			
			if(strSEQ.equals("-Q")) {
				
				return;
			}
			
			int intSEQ = 0;
			
			try {
				intSEQ = Integer.valueOf(strSEQ);
			} catch (Exception e) {
				System.out.println("SEQ는 숫자만 입력 가능합니다");
				continue;
			}
			
			rentDTO = rentDao.findById(intSEQ);
			if(rentDTO == null) {
				
				System.out.println("SEQ를 다시 확인해주세요");
				continue;
			}
			
			List<RentDTO> rentCheckList = rentDao.rentCheck(rentDTO.getRent_bcode());
			if(rentCheckList.size() < 1) {
				System.out.println("이미 반납처리된 도서입니다.");
				return;
			}
			
			rentVO = viewDao.findByID(intSEQ);
			this.viewRentVO(rentVO);
			
			System.out.print("반납 정보를 확인해주세요(Enter : 계속 진행) >> ");
			String strYesNo = scan.nextLine();
			
			if(rentDTO.getRent_return_yn() == null) {
				rentDTO.setRent_return_yn("Y");
				
			}
			
			if(strYesNo.trim().isEmpty()) {
				
				int ret = rentDao.update(rentDTO);
				if(ret > 0) {
					System.out.println("반납 성공");
				}else {
					System.out.println("반납 실패");
				}
			}
			
			
			
			
			
			
			
			break;
		}
	}

	
}
