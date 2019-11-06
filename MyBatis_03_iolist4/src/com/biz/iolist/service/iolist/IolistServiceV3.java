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

public class IolistServiceV3 extends IolistServiceV2 {
	
	ProductServiceV4 proService = new ProductServiceV4();
	DeptServiceV3 deptService = new DeptServiceV3();

	@Override
	protected void update() {

		System.out.println("======================");
		System.out.println("매입매출수정");
		System.out.println("======================");
		
		System.out.print("거래처명 >> ");
		String strDname = scan.nextLine();
		ioView.viewListByDname(strDname);
		if(strDname.trim().isEmpty()) {
			ioView.viewAllList();
		}else {
			ioView.viewListByDname(strDname);
		}
		
		System.out.print("수정할 SEQ >> ");
		String strSEQ = scan.nextLine();
		
		long longSEQ = 0;
		try {
			
			longSEQ = Long.valueOf(strSEQ);
		} catch (Exception e) {
			System.out.println("SEQ형식이 틀렸습니다.");
			return;
		}
		// seq에 해당하는 iolist 가져오기
		IolistDTO iolistDTO = iolistDao.findById(longSEQ);
		
		while(true) {

			System.out.printf("거래구분[%s] 입력 (1: 매입, 2: 매출, -1:종료) >> " , iolistDTO.getIo_inout());
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
			
			// Date date = new Date();
			// String  curDate = sd.format(date);
			
			System.out.printf("거래일자(%s / Enter : 현재날짜 입력) >> " , iolistDTO.getIo_date());
			String strDate = scan.nextLine();
			if(strDate.trim().isEmpty()) {
				
				// iolistDTO.setIo_date(curDate);
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
			strDname = scan.nextLine();
			
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
		
		int ret = iolistDao.update(iolistDTO);
		if(ret > 0) {
			System.out.println("데이터 변경 성공");
		}else {
			System.out.println("데이터 변경 실패");
		}
		
		
		
	}

	// seq를 입력받아서 해당
	@Override
	protected void delete() {

		System.out.println("=============================");
		System.out.println("매입매출 데이터 삭제");
		System.out.println("=============================");
		
		System.out.print("거래처명(모든 리스트 보기 : Enter) >> ");
		String strDname = scan.nextLine();

		// 그냥 Enter만 입력하면 모든 거래처 리스트 보여주기
		if(strDname.trim().isEmpty()) {
			ioView.viewAllList();
		}else {
			// 거래처명을 입력했으면 listByDname method를 이용해서 입력받은 거래처 리스트만 보여주기
			ioView.viewListByDname(strDname);
		}
	
		// 삭제할 SEQ를 입력받고 해당 SEQ의 리스트를 보여주고 삭제 여부를 다시 물어본 후 삭제해주기
		System.out.print("삭제할 SEQ >> ");
		String strSEQ = scan.nextLine();
		
		
		
		
		
		
	}
	
	

}
