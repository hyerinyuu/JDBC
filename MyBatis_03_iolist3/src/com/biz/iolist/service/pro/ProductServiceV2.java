package com.biz.iolist.service.pro;

import java.util.List;

import com.biz.iolist.persistence.ProductDTO;

public class ProductServiceV2 extends ProductServiceV1 {

	public void searchPName() {
			
		System.out.print("검색할 상품명(Enter:전체) >> ");
		String strName = scan.nextLine();
		
		List<ProductDTO> proList = null;
		
		if(strName.trim().isEmpty()) {
			
			// Enter만 치면 리스트를 모두 보여주기위해
			proList = proDao.selectAll();
		}else {
			proList = proDao.findByName(strName);
			
		}
		for(ProductDTO dto : proList) {
			
			System.out.println(dto.toString());
		}
			
	}
		
		


}
