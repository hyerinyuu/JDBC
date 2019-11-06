package com.biz.iolist.dao;

import java.util.List;

import com.biz.iolist.persistence.DeptDTO;

public interface DeptDao {

	public List<DeptDTO> selectAll();
	
	public DeptDTO findById(String d_code);
	
	public int select(DeptDTO deptDTO);
	public int update(DeptDTO deptDTO);
	public int delete(String d_code);
	
	
}
