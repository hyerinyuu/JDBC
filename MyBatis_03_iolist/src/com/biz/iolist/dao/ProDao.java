package com.biz.iolist.dao;

import java.util.List;

import com.biz.iolist.persistence.ProductDTO;

public interface ProDao {

	public List<ProductDTO> selectAll();
	
	public ProductDTO findById(String p_code);
	public List<ProductDTO> findByName(String p_name);
	
	
	public int insert(ProductDTO proDTO);
	public int update(ProductDTO proDTO);
	public int delete(String p_code);
	
	
	
}
