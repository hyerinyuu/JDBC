package com.biz.iolist.service.dept;

import java.util.List;

import com.biz.iolist.persistence.DeptDTO;

public class DeptServiceV2 extends DeptServiceV1 {

	public void deptMenu(){
		
		System.out.println("=================================================");
		System.out.println("거래처 정보 관리");
		System.out.println("=================================================");
		System.out.println("1.등록 2.수정 3.삭제 4.검색 0.종료");
		System.out.println("-------------------------------------------------");
		System.out.print("업무선택 >> ");
		String strMenu = scan.nextLine();
		
		try {
			int intMenu = Integer.valueOf(strMenu);
			if(intMenu == 0) return;
			
			// else if는 괄호안의 값이 true이면 중괄호 안의 코드를 실행하고 그 밑의 코드는 무시하고 바로 빠져나가기 때문에
			// 모든 if문을 다 검사하는 if보다 코드의 효율성이 좋다.
			else if(intMenu == 1 ) {
				this.insertDept();
			}else if(intMenu == 2) {
				this.viewNameList();
				this.updateDept();
			}else if(intMenu == 3) {
				// 상호로 먼저 검색 후 리스트 보여주기
				this.viewNameList();
				// 보여준 리스트중에서 삭제할 거래처 코드를 입력받아서 삭제 수행
				this.deleteDept();
			}else if(intMenu == 4) {
				this.viewNameAndCeoList();
			}
				
		} catch (Exception e) {
			// TODO: handle exception
			// sql문에서 오류나면 오류를 보여줌
			e.printStackTrace();
		}
		
		
	}

	public void deleteDept() {

		while(true) {

			System.out.print("삭제할 거래처 코드(-Q : quit) >> ");
			String strDcode = scan.nextLine();
			
			if(strDcode.equals("-Q")) break;
			
			DeptDTO deptDTO = deptDao.findById(strDcode);
			if(deptDTO == null) {
				System.out.println("삭제할 코드가 없음");
				continue;
			}	
			this.viewDetail(deptDTO);
			
			System.out.print("정말 삭제?? (Enter : 실행) >> ");
			String strYesNo = scan.nextLine();
			
			if(strYesNo.trim().isEmpty()) {
				int ret = deptDao.delete(strDcode);
				if(ret > 0) {
					System.out.println("삭제 완료!");
					// 삭제가 완료되면 반복문 끝내기
					break;
				}else {
					System.out.println("삭제 실패!");
				}
			}
			
		}
		
	} // end delete
	
	// enter하면 skip/ 코드 입력하면 update
	public void updateDept() {
		
		while(true) {
			
			System.out.print("수정할 코드(-Q : quit) >> ");
			String strDcode = scan.nextLine();
			
			if(strDcode.equals("-Q")) {
				System.out.println("수정취소");
				break;
			}
			
			// 소문자로 입력해도 수정할 수 있게
			// strDcode = strDcode.toUpperCase이런식으로 변수를 재정의 해줘야 함.
			strDcode = strDcode.toUpperCase();
			
			// findById method를 이용해 strDcode를 주입
			DeptDTO deptDTO = deptDao.findById(strDcode);
			
			if(deptDTO == null) {
				System.out.println("코드를 다시 확인해주세요");
				// 코드를 잘못 입력하면 다시 입력할 수 있게 continue
				continue;
			}
			// 입력한 코드가 맞았으면 viewDetail로 최종 저장데이터 보여주고 수정 여부 확인하기
			this.viewDetail(deptDTO);
			System.out.print("정말 수정하시겠습니까? (Enter : skip / 수정 : Y ) >> ");
			String strYesNo = scan.nextLine();
			
			// 키보드로 입력받은 값이 없으면 == enter면
			if(strYesNo.trim().isEmpty()) {
				System.out.println("수정 취소");
				break;
			
			// 무엇이든 입력 받았으면
			}else if(strYesNo.equalsIgnoreCase("Y")){
				
				// 코드가 맞으면 update 수행하기
				System.out.printf("상호(%s / Enter : skip) >> \n" , deptDTO.getD_name());
				String strName = scan.nextLine();
				if(strName.trim().isEmpty()) {
					//deptDTO.setD_name(deptDTO.getD_name());  ==> 오류
					// 그냥 enter만 눌렀으면 건너뛰기
				}else {
					deptDTO.setD_name(strName);
				}
				
				System.out.printf("대표(%s / Enter : skip) >> \n", deptDTO.getD_ceo());
				String strCeo = scan.nextLine();
				if(strCeo.trim().isEmpty()) {
					//deptDTO.setD_ceo(deptDTO.getD_ceo());
				}else {
					deptDTO.setD_ceo(strCeo);
				}
				System.out.printf("전화(%s / Enter : skip) >> \n", deptDTO.getD_tel());
				String strTel = scan.nextLine();
				deptDTO.setD_tel(strTel);
				
				System.out.printf("주소(%s / Enter : skip)) >> \n", deptDTO.getD_addr());
				String strAddr = scan.nextLine();
				deptDTO.setD_addr(strAddr);
				
				
				int ret =  deptDao.update(deptDTO);
				if(ret > 0) {
					System.out.println("수정 완료!");
					break;
				}else {
					System.out.println("수정 실패!");
				}
			}
			
			
		}
		
	}
	
	public void insertDept() {
		
	}

	
	
}
