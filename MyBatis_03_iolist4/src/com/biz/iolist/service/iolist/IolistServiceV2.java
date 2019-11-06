package com.biz.iolist.service.iolist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.biz.iolist.persistence.DeptDTO;
import com.biz.iolist.persistence.IolistDTO;
import com.biz.iolist.persistence.ProductDTO;
import com.biz.iolist.service.dept.DeptServiceV3;
import com.biz.iolist.service.pro.ProductServiceV4;

public class IolistServiceV2 extends IolistServiceV1 {
	
	ProductServiceV4 proService = new ProductServiceV4();
	DeptServiceV3 deptService = new DeptServiceV3();

	/*
	 * 매입매출등록
	 * 
	 * 1.  매입, 매출 구분 입력
	 * 
	 * 2. 날짜는 현재 날짜로 자동등록
	 * 
	 * 3. 거래처 검색(이름으로)
	 * 	1. 거래처 코드 입력
	 * 	2. 입력한 코드 검증
	 * 
	 * 4. 상품 검색
	 * 	1. 상품코드 입력
	 * 	2. 입력한 코드 검증
	 *  
	 *  매입, 매출에 따라서
	 *  매입단가, 매출단가 가져오기(세팅)
	 *  
	 *  수량입력
	 *  
	 *  단가 * 수량
	 *  	매입매출 
	 *  
	 *  매입합계 또는 매출합계 계산하기
	 *  
	 *  insert
	 *  
	 *  추가된 데이터 보여주기
	 */
	
	@Override
	protected void insert() {

		System.out.println("======================");
		System.out.println("매입매출등록");
		System.out.println("======================");
		
		// 값이 없는 newIolistDTO를 생성
		IolistDTO iolistDTO = new IolistDTO();
		
		while(true) {

			System.out.print("거래구분 입력 (1: 매입, 2: 매출, -1:종료) >> ");
			String strInout = scan.nextLine();
			try {
				
				int intInout = Integer.valueOf(strInout);
				if(intInout == -1) break;
				
				// strInout = intInout == 1 ? "매입" : "매출";
				if(intInout == 1) {
					iolistDTO.setIo_inout("매입");
				}else if(intInout == 2) {
					iolistDTO.setIo_inout("매출");
				// 1도 2도 아닌 값을 입력했으면
				}else {
					System.out.println("매입 매출 구분을 다시 선택하세요");
					continue;
				}
			} catch (Exception e) {
				System.out.println("매입매출 구분을 다시 입력해주세요");
				continue;
			}
			break;
		}
		if(iolistDTO.getIo_inout().isEmpty()) {
			System.out.println("구분은 반드시 입력해야합니다");
			return;
		}
		
		while(true) {
			
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String  curDate = sd.format(date);
			
			System.out.printf("거래일자(%s / Enter : 현재날짜 입력) >> " , curDate);
			String strDate = scan.nextLine();
			if(strDate.trim().isEmpty()) {
				
				iolistDTO.setIo_date(curDate);
			}else {
				
				try {
					sd.parse(strDate);
				} catch (ParseException e) {
					
					System.out.println("날짜 형식이 잘못되었습니다.");
					continue;
				}
				iolistDTO.setIo_date(strDate);
			}
			break;
		}
		
		while(true) {
			
			System.out.print("거래처명 입력(-Q : quit) >> ");
			String strDname = scan.nextLine();
			
			if(strDname.equals("-Q")) break;
			
			List<DeptDTO> deptList = deptDao.findByName(strDname);
			if(deptList != null && deptList.size() > 0) {
				
				for(DeptDTO dto : deptList) {
					System.out.println(dto.toString());
				}
				
				System.out.println("-----------------------------------");
				System.out.print("거래처 코드 입력 >> ");
				String strDcode = scan.nextLine();
				
				DeptDTO deptDTO = deptDao.findById(strDcode);
				if(deptDTO == null) {
					System.out.println("존재하지 않는 거래처 코드입니다.");
					continue;
				}else {
					iolistDTO.setIo_dcode(strDcode);
				}
			}else {
				continue;
			}
			break;
		}
		if(iolistDTO.getIo_dcode().isEmpty()) return;

		
		while(true) {
			System.out.print("상품명 입력(-Q : quit) >> ");
			String strPname = scan.nextLine();
			
			if(strPname.equals("-Q")) break;
			
			List<ProductDTO> proList = proDao.findByName(strPname);
			
			if(proList == null && proList.size() < 1) {
				System.out.println("찾는 상품이 없음");
				continue;
			}else {
				
				for(ProductDTO dto : proList) {
					System.out.println(dto.toString());
			}
				System.out.print("상품코드 >> ");
				String strPcode = scan.nextLine();
				
				ProductDTO proDTO = proDao.findById(strPcode);
				if(proDTO == null) {
					System.out.println("상품코드를 다시 확인하세요");
					continue;
				// 상품코드가 정상 입력되었으면	
				}else {
					iolistDTO.setIo_pcode(strPcode);
					int intPrice = iolistDTO.getIo_inout().equals("매입")
							? proDTO.getP_iprice()
							: proDTO.getP_oprice();
							
					iolistDTO.setIo_price(intPrice);
				}
			}
			break;
		}
		if(iolistDTO.getIo_pcode().isEmpty()) return;
		
		
		while(true) {
			System.out.printf("단가 입력(%d) >> ", iolistDTO.getIo_price());
			String strPrice = scan.nextLine();
			
			// 그냥 Enter를 치면 get한 price가 그대로 저장되고
			// 값을 입력하면 입력한 값이 저장됨
			try {
				int intPrice = Integer.valueOf(strPrice);
				iolistDTO.setIo_price(intPrice);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			break;
		}
		
		while(true) {
			System.out.print("수량 입력 >> ");
			String strQty = scan.nextLine();
			
			try {
				int intQty = Integer.valueOf(strQty);
				iolistDTO.setIo_qty(intQty);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("수량은 숫자로만 입력하세요");
				continue;
			}
			break;
		}
		
		int intTotal = iolistDTO.getIo_price() * iolistDTO.getIo_qty();
		iolistDTO.setIo_total(intTotal);
		
		int ret = iolistDao.insert(iolistDTO);
		if(ret > 0) {
			System.out.println("데이터 추가 성공");
		}else {
			System.out.println("데이터 추가 실패");
		}
		
	}
	

	@Override
	protected void delete() {
		// TODO Auto-generated method stub
		super.delete();
	}

	
	
}
