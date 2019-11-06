package com.biz.rent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.rent.persistence.RentDTO;

public interface RentDao {

	public List<RentDTO> selectAll();
	

	public List<RentDTO> rentCheck(@Param("rent_bcode") String rent_bcode);
	public List<RentDTO> notReturn();
	
	public RentDTO findById(int rent_seq);
	
	public int insert(RentDTO rentDTO);
	public int delete(int rent_seq);
	public int update(RentDTO rentDTO);
	
}
