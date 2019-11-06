package com.biz.rent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.rent.persistence.UserDTO;

public interface UserDao {

	public String getMaxCode();
	public List<UserDTO> selectAll();
	
	public UserDTO findByEName(String u_name);
	public List<UserDTO> findByName(String u_name);
	
	public UserDTO findById(String u_code);
	
	public List<UserDTO> findByTel(String u_tel);
	
	// 이름과 전화번호가 동일한 사람은 등록 금지
	public List<UserDTO> findByNameAndTel(@Param("u_name") String u_name, @Param("u_tel") String u_tel);
	
	public int insert(UserDTO userDTO);
	public int update(UserDTO userDTO);
	public int delete(String u_code);

	
	
	
	
	
}
