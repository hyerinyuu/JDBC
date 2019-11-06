package com.biz.iolist.service.dept;

import java.util.List;

import com.biz.iolist.persistence.DeptDTO;

public class DeptServiceV3 extends DeptServiceV2{

	// 키보드에서 정보를 입력받아서 DB에 추가하기
	// 1. 거래처 코드 : 자동생성 기존 코드가 있으면 추가 금지
	// 2. 상호는 같은 상호가 있더라도 대표자 명이 다르면 입력 가능
	public void insertDept() {

		String strDCode;
		while(true) {
			
			System.out.print("거래처코드(-Q : quit / Enter : 자동생성) >> ");
			strDCode = scan.nextLine();
			
			// 코드 자동생성
			if(strDCode.trim().isEmpty()) {

				// maxCode == D0236(dept-mapper에 getMaxDcode 참조)
				String strTMPcode = deptDao.getMaxDcode();
				
				// 자동생성할 코드를 int형으로 형변환을 시키고 subString으로 "D"문자열 잘라내고 숫자형 0236만 남기기 
				int intDcode = Integer.valueOf(strTMPcode.substring(1));
				
				// 0236 -> 237로 변경
				intDcode ++;
				
				// 현재코드에서(D0236) D문자열을 잘라내고
				strDCode = strTMPcode.substring(0,1); // strCode = "D"
				
				// intDcode를 0237형식으로 문자열형으로 형변환 시켜서 strDcode에 붙여줌 
				strDCode += String.format("%04d", intDcode);
				
				System.out.println("자동 생성된 코드 >> " + strDCode);
				System.out.println("사용하시겠습니까?(Yes : Enter / NO : 코드입력) >> ");
				String strYesNo = scan.nextLine();
				if(strYesNo.trim().isEmpty()) break;
				else continue;
				
			}// 코드 자동생성문 end
			
			// 코드 자동 생성 안할거면 코드 규칙에 맞게 코드 생성하게 해주기
			if(strDCode.length() != 5 ) {
				System.out.println("거래처코드는 5자리여야합니다.");
				continue;
			}
			strDCode = strDCode.toUpperCase();
			
			// 상품코드의 첫글자가 대문자 소문자 관계없이 D로 시작되지 않으면
			if(!strDCode.subSequence(0, 1).equals("D") ) {
				System.out.println("거래처 코드는 D로 시작되어야 합니다.");
				continue;
			}
			
			try {
				Integer.valueOf(strDCode.substring(1));
			} catch (Exception e) {
				System.out.println("거래처 코드의 2번째 자릿수부터는 숫자만 입력 가능합니다.");
				continue;
				
			// 이미 존재하는 코드를 입력했다면
			}if(deptDao.findById(strDCode) != null) {
				System.out.println("입력한 코드는 중복되는 코드입니다.");
				continue;
				
			}	break;
		} // code생성 반복문 end
		if(strDCode.equals("-Q")) return;
		
		String strDname = null;
		String strDceo = null;
		
		while(true) {
			System.out.print("상호명(-Q : quit) >> ");
			strDname = scan.nextLine();
			if(strDname.equals("-Q")) break;
			if(strDname.trim().isEmpty()) {
				System.out.println("상호명은 반드시 입력해야합니다");
				continue;
			}
			System.out.print("대표자명 >> ");
			strDceo = scan.nextLine();
			if(strDceo.trim().isEmpty()) {
				System.out.println("대표자명은 반드시 입력해야합니다");
				continue;
			}
			
			// 상호명은 대표자명이 중복되지 않는다면 중복 가능
			List<DeptDTO> deptList = deptDao.findByNameAndCEO(strDname, strDCode);
			
			// 사장 이름과 상호명이 모두 중복된다면
			if(deptList.size() > 0) {
				System.out.println("상호명과 대표명이 모두 중복됩니다(모두 중복 허용x)");
				continue;
			}
			break;
		} // 대표자명/상호명 중복검사 끝
		
		String strTel = null;
		String strAddr = null;
		while(true) {
			System.out.print("전화번호 >> ");
			strTel = scan.nextLine();
			
			
			System.out.print("주소 >> ");
			strAddr = scan.nextLine();
			
			break;
		}
		
		DeptDTO deptDTO = DeptDTO.builder().d_code(strDCode)
											.d_name(strDname)
											.d_ceo(strDceo)
											.d_tel(strTel)
											.d_addr(strAddr)
											.build();
		
		int ret = deptDao.insert(deptDTO);
		if(ret > 0) {
			System.out.println("insert 성공");
		}else {
			System.out.println("insert 실패");
		}
		
		
		
		
		
	}
	
	
}
