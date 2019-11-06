package com.biz.iolist.service;

import java.util.List;
import java.util.Scanner;

import com.biz.iolist.config.DBConnection;
import com.biz.iolist.dao.ProDao;
import com.biz.iolist.persistence.ProductDTO;

public class ProductServiceV1 {

	protected ProDao proDao;
	protected Scanner scan;
	
	public ProductServiceV1() {

		this.proDao = DBConnection.getSqlSessionFactory().openSession(true).getMapper(ProDao.class);
		scan = new Scanner(System.in);
	
	}
	
	public void proUpdate() {
		
		/*
		List<ProductDTO> proList = proDao.selectAll();
		for(ProductDTO dto  : proList) {
			System.out.println(dto.toString());
		}
		*/
		System.out.println("================================================");
		System.out.print("수정할 상품 코드 >> ");
		String strPcode = scan.nextLine();
		
		// 입력한 상품코드를 대문자로 변경
		strPcode = strPcode.toUpperCase();
		
		// 입력한 상품코드를 다시 조회
		ProductDTO proDTO = proDao.findById(strPcode);
		System.out.println("================================================");
		System.out.printf("상품코드 : %s\n", proDTO.getP_code());
		System.out.printf("상품이름 : %s\n", proDTO.getP_name());
		System.out.printf("매입단가 : %d\n", proDTO.getP_iprice() );
		System.out.printf("매출단가 : %d\n", proDTO.getP_oprice());
		
		// DECODE(VAT, '1', '과세', '면세')
		// VAT가 1이면 과세, 아니면 면세라는 문자열을 strVAT에 담아라(3항연산자)
		// 변수 = 비교연산자 ? "true", "false"
		String strVAT = proDTO.getP_vat().equals("1") ? "과세" : "면세" ;
		System.out.printf("과세여부(1:과세, 0:면세 ) : %s\n", strVAT);
		System.out.println("================================================");
		
					System.out.printf("변경할 상품명(%s) >> " , proDTO.getP_name());
					String strPname = scan.nextLine();
					
					// strPname 면수에 아무런 문자열도 들어있지 않다면 == 그냥 Enter만 눌렀다면
					// !strPname.trim().isEmpty() == 그렇지 않으면 == 무언가를 입력했다면
					if(!strPname.trim().isEmpty()) {
						proDTO.setP_name(strPname);
					}
					
					System.out.printf("변경할 매입단가(%d) >> " , proDTO.getP_iprice());
					String strIprice = scan.nextLine();
					
					try {
						// 만약 매입 단가를 입력하지 않고 그냥 enter만 눌렀다면
						// Integer.valueof(strIprice)에서 excepion이 발생할 것이다.
						// try로 감싸진 코드에서 exception이 발생을 하면
						// 나머지 코드는 무시되고 catch문으로 바로 빠져나간다.
						// 이러한 기능을 역이용하여 값을 입력하지 않거나
						// 문자열 등을 포함하면
						// 원래 proDTO의 iprice값을 변경하지 않도록 한다.
						
						proDTO.setP_iprice(Integer.valueOf(strIprice));
					} catch (Exception e) {
						// TODO: handle exception
					}
					
					System.out.printf("변경할 매출단가(%d) >> ", proDTO.getP_oprice());
					String strOprice = scan.nextLine();
					try {
						proDTO.setP_oprice(Integer.valueOf(strOprice));
					} catch (Exception e) {
					}
					/*
					 * proDTO의 필드변수들은
					 * 각 항목을 입력받을때 Enter만 입력했다면
					 * 원래 DB table에서 가져온 값이 그대로 유지 될것이고
					 * 
					 * 값을 입력했다면
					 * 새로운 값으로 변경 되어 있을 것이다.
					 */
					int ret = proDao.update(proDTO);
					if(ret > 0) {
						System.out.println("데이터 변경 완료");
					}else {
						System.out.println("데이터 변경 실패");
					}
					
				// 변경 됐는지 확인
				System.out.println(proDao.findById(strPcode).toString());
				
				
		
	
	}
	
}
