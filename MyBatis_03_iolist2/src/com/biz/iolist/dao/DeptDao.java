package com.biz.iolist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.iolist.persistence.DeptDTO;

public interface DeptDao {

	public List<DeptDTO> selectAll();
	
	public DeptDTO findById(String d_code);
	
	public List<DeptDTO> findByName(String d_name);
	
	public List<DeptDTO> findByCeo(String d_ceo);
	
	// param("") => ""안에 들어가는 칼럼명은 mapper와 정확히 일치해야함(대소문자 포함)
	public List<DeptDTO> findByNameAndCEO(@Param("d_name") String d_name, @Param("d_ceo") String d_ceo);
	
	public int select(DeptDTO deptDTO);
	public int update(DeptDTO deptDTO);
	public int delete(String d_code);
	
	
}
