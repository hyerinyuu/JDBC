package com.biz.iolist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.iolist.persistence.ProductDTO;

public interface ProDao {

	// tbl_product 테이블의 p_code의 최대값을 가져오기
	public String getMaxPcode();
	
	public List<ProductDTO> selectAll();
	
	public ProductDTO findById(String p_code);
	public List<ProductDTO> findByName(String p_name);
	
	// 상품정보를 입력할 때 완전히 일치하는 이름을 가진 상품이 있는가를 검사하는 코드를 만들 계획
	public ProductDTO findBySName(String p_name);
	
	
	
	public int insert(ProductDTO proDTO);
	public int update(ProductDTO proDTO);
	
	/*
	 * 매개변수가 1개일 경우에는 mapper로 값을 전달할 때
	 * 마치 1개자리 배열인 것처럼 전달된다.
	 */
	public int delete(@Param("p_code") String p_code);
	
	// 매개변수가 2개 이상일 경우 매개변수에 
	// @Param() Annotation을 반드시 명시해줘야한다.
	public List<ProductDTO> findByIPrice(@Param("sPrice") int sPrice, @Param("ePrice") int ePrice);
	
	
	
}
