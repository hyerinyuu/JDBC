package com.biz.iolist.dao;

import java.util.List;

import com.biz.iolist.persistence.IolistVO;

public interface IolistViewDao {

	public List<IolistVO> selectAll();
	public void findByID();
	
	public List<IolistVO> findByDcode(String io_dcode);
	public List<IolistVO> findByPcode(String io_pcode);
	public List<IolistVO> findByPname(String pName);
	public List<IolistVO> findByDname(String strDname);
	
	/*
	public List<IolistVO> findByPcode(String io_pcode);
	public List<IolistVO> findByPcode(String io_pcode);
	public List<IolistVO> findByPcode(String io_pcode);
	*/
	
	
	
}
