package com.biz.rent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.rent.persistence.BookDTO;

public interface BookDao {

	// insert를 위해서 만든 b_code 자동생성 method
	public String getMaxCode();
	
	public List<BookDTO> selectAll();
	
	public BookDTO findById(String b_code);
	
	// 책이름이 완벽히 일치할 때만
	public BookDTO findByEName(String b_name);
	// 검색한 이름이 들어가는 모든 책 리스트
	public List<BookDTO> findByName(String b_name);
	
	// 저자가 완벽히 일치할 때만
	public BookDTO findByEAuther(String b_auther);
	// 키워드 저자 검색
	public List<BookDTO> findByAuther(String b_auther);
	
	public List<BookDTO> findByNameAndAuth(@Param("b_name") String b_name, @Param("b_auther") String b_auther);
	
	public int insert(BookDTO bookDTO);
	public int update(BookDTO bookDTO);
	public int delete(String b_code);
	
}
