package com.biz.iolist.service.pro;

import java.util.List;

import com.biz.iolist.persistence.ProductDTO;

public class ProductServiceV4 extends ProductServiceV3 {

	public void menuProduct() {
		System.out.println("======================================");
		System.out.println("대한쇼핑몰 상품관리 시스템 v3");
		System.out.println("======================================");
		System.out.println("1.등록 2.수정 3.삭제 4. 검색 0. 끝");
		System.out.print("업무선택 >> ");
		String strMenu = scan.nextLine();
		int intMenu = Integer.valueOf(strMenu);
		
		if(intMenu == 1) {
			this.insertProduct();
		}else if(intMenu == 1 ) {
			this.proUpdate();
		}else if(intMenu == 2) {
			
			this.searchPName();
			this.proUpdate();
			
		}else if(intMenu == 3) {
			// 상품으로 리스트 검출
			this.searchPName();
			// 상품 코드를 입력받아서 삭제 수행
			this.deleteProduct();
		}else if(intMenu == 4) {
			this.searchPName();
		}else if(intMenu == 0) {
			
		}
		
	}
	
	// proDTO를 보여주기 전용으로 재 선언
	protected void viewPDetail(ProductDTO proDTO){
		
		System.out.println("================================================");
		System.out.println("같은 이름의 상품이 존재합니다.");
		System.out.println("------------------------------------------------");
		System.out.print("상품 코드 : " + proDTO.getP_name());
		System.out.print("품목 : " );
		System.out.println("주매입처 : ");
		System.out.println("매입 단가 : " + proDTO.getP_iprice());
		System.out.println("매출 단가 : " + proDTO.getP_oprice());
		System.out.println("------------------------------------------------");
		
	}
	
	
	/*
	 * 상품코드를 입력받아서 insert 수행
	 * 상품코드를 입력받아서
	 * 있으면 다시 입력하도록
	 * 없으면 다음으로 진행
	 * 
	 * 1. 상품코드를 어떻게 할 것인가?
	 * 		가. 자동으로 생성학
	 * 		나. 직접 입력하기(이미 문서로 작성된 경우)
	 * 
	 * 2. 상품이름을 입력하는데, 완전히 일치하는 상품이 이미 있는 경우
	 * 		가. 코드가 다르면 그냥 입력하기
	 * 		나. 코드가 다르고 단가부분이 다르면 그냥 입력하기
	 * 			상품정보 : 상품이름, 품목, 주매입처
	 * 		다. 같은 상품이름 입력 금지
	 * 
	 * 3. 단가부분
	 * 		가. 매입단가를 입력하면, 표준 판매단가를 계산하기
	 * 		나. 매입단가, 매출단가를 별도로 입력
	 * 		다. 매입단가일경우, VAT포함 여부
	 * 				소매점에서는 VAT 포함이 기본
	 * 				도매점에서는 VAT포함 여부 선택 
	 * 
	 */
	public void insertProduct() {
		
		System.out.println("================================================");
		String strPcode;
		while(true) {
			System.out.print("상품코드(Enter : 자동생성) >> ");
			strPcode = scan.nextLine();
			if(strPcode.equals("-Q")) {
				break;
			}
			if(strPcode.trim().isEmpty()) {
				
				// 상품코드 자동생성
				String strTMPcode = proDao.getMaxPcode();
				// strTMPcode =  P0393이라면
				
				// 그중에서 0393만 추출하여 숫자로 변환
				int intPcode = Integer.valueOf(strTMPcode.substring(1));
				
				// 1을 증가시키기(394)
				intPcode ++;
				
				// 현재코드에서 첫번째 문자열을 잘라내고(P)
				strPcode = strTMPcode.substring(0, 1);
				
				// 394를 0394문자열로 변환시키고 strPcode와 연결
				strPcode += String.format("%04d", intPcode);
				// ==> result : P0394
				
				System.out.println("생성된 코드 : " + strPcode);
				System.out.println("사용하시겠습니까? (Enter : Yes)");
				String strYesNo = scan.nextLine();
				if(strYesNo.trim().isEmpty()) break;
				else continue;
			}
			if(strPcode.length() != 5) {
				System.out.println("상품코드의 길이 규칙에 맞지 않음");
				continue;
			}
			strPcode = strPcode.toUpperCase();
			if(!strPcode.subSequence(0, 1).equals("P")) {
				System.out.println("상품코드의 첫글자는 P로 시작되어야 합니다.");
				continue;
			}
			try {
				Integer.valueOf(strPcode.substring(1));
			} catch (Exception e) {
				System.out.println("상품코드 2번째 이후는 숫자만 올 수 있습니다.");
				continue;
			}
			// 기존 코드와 중복이 있다는 소리
			if(proDao.findById(strPcode) != null) {
				System.out.println("이미 등록된(사용중인) 코드!");
				continue;
			}
			break;
			
		}// pCode 입력 끝
		if(strPcode.equals("-Q")) return;
		
		// 2. 상품 이름 입력
		String strPname;
		
		while(true) {
			
			System.out.print("상품이름(-Q : quit) >> ");
			strPname = scan.nextLine();
			if(strPname.equals("-Q")) break;
			if(strPname.trim().isEmpty()) {
				System.out.println("상품이름은 반드시 입력해야 합니다.");
				continue;
			}
			ProductDTO proDTO = proDao.findBySName(strPname);
			
			if(proDTO != null) {
				
				this.viewPDetail(proDTO);
				
				System.out.print("사용하시겠습니까? (Enter : 사용, NO : 다시입력) >> ");
				String yesNo = scan.nextLine();
				if(yesNo.trim().isEmpty())break;
				continue;
			}
			// 이 break문이 없으면 계속 상품이름 물어봄
			break;
		}// 상품 이름 입력 끝
		
		// 상품 매입단가 입력
		// 1. VAT여부를 입력
		// 2. 과세 : 입력가격 / 1.1
		// 	  면세 : 그대로	(면세 : 농산물(1차식품, 쌀), 흰우유, 재포장상품(과일 등 농수식품))
		String strVAT = "1";
		int intIprice = 0;
		String strOprice;
		while(true) {
			
			System.out.print("과세구분(1 : 과세, 0 : 면세, -Q : quit) >> ");
			strVAT = scan.nextLine();
			
			if(strVAT.equals("-Q"))break;
			
			int intVAT = 1;
			
			try {
				intVAT = Integer.valueOf(strVAT);
				// 1과 0이외의 값을 입력하면 되돌려 보내는 코드
				if(intVAT < 0 || intVAT > 1) {
					System.out.println("과세 구분은 0 또는 1만 입력 가능합니다");
				}
			} catch (Exception e) {
				System.out.println("0또는 1만 가능");
			}
			
			System.out.print("매입단가 >> ");
			String strIprice = scan.nextLine();
			
			try {
				intIprice = 
						(int)(intVAT ==  1 
						? Integer.valueOf(strIprice) / 1.1
						: Integer.valueOf(strIprice));
				
			} catch (Exception e) {
				System.out.println("매입단가는 숫자만 입력 가능합니다");
				continue;
			}
			break;
		} // 매입 단가 입력 끝
		
		// 매출단가 입력
		if(strVAT.equals("-Q")) return;
		
		int intOprice = 0;
		
		while(true) {
			System.out.print("매출단가(-Q : quit) >> ");
			strOprice = scan.nextLine();
			
			if(strOprice.equals("-Q")) break;
			
			try {
				intOprice = Integer.valueOf(strOprice);
				
			} catch (Exception e) {
				System.out.println("매출단가는 숫자만 가능합니다");
				continue;
			}
			break;
		} // 매출단가 입력 끝
		
		if(strOprice.equals("-Q")) return;
		
		ProductDTO proDTO = ProductDTO.builder()
				.p_code(strPcode)
				.p_name(strPname)
				.p_vat(strVAT)
				.p_iprice(intIprice)
				.p_oprice(intOprice)
				.build();
		
		int ret = proDao.insert(proDTO);
		if(ret > 0 ) {
			System.out.println("상품등록 성공!");
		}else {
			System.out.println("상품 등록 실패!");
		}
	}
	
	/*
	 * 상품코드를 입력받아서 delete 수행 
	 */
	public void deleteProduct() {
		
		/*
		System.out.print("상품이름 >> ");
		String strName = scan.nextLine();
		List<ProductDTO> proList = null;
		proList = proDao.findByName(strName);
		
		for(ProductDTO dto : proList) {
			System.out.println(dto.toString());
		}

		System.out.print("삭제할 상품코드 >> ");
		String strPcode = scan.nextLine();
		strPcode = strPcode.toUpperCase();
		*/
		
		System.out.print("삭제할 상품코드 >> ");
		String strPcode = scan.nextLine();
		strPcode = strPcode.toUpperCase();
		
		ProductDTO proDTO = this.viewPDetail(strPcode);
		
		if(proDTO == null) {
			System.out.println("상품코드가 없습니다");
			return;
			
		}
		int ret = proDao.delete(strPcode);
		if(ret > 0) {
			System.out.println("삭제 완료");
		}else {
			System.out.println("삭제 실패");
		}
		
		
	}
}
