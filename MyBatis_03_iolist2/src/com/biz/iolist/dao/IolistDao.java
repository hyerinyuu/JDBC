package com.biz.iolist.dao;

import java.util.List;

import com.biz.iolist.persistence.IolistDTO;

public interface IolistDao {

	public List<IolistDTO> selectAll();
	
	public IolistDTO findById(int io_seq);
	
	public int insert(IolistDTO ioListDTO);
	public int update(IolistDTO ioListDTO);
	public int delete(int io_seq);
	
	
	
}
