package com.biz.mybatis.mapper;

import java.util.List;

import com.biz.mybatis.persistence.BookDTO;

public interface BookDao {

	// DB로부터 모든 레코드를 가지고 와서 리스트로 보여주는 method
	public List<BookDTO> selectAll();
	
	// id값을 주고 값을 1개만 가져옴
	public BookDTO findById(String b_code);
	public List<BookDTO> findByName(String b_name);
	
	public int insert(BookDTO bookDTO);
	public int update(BookDTO bookDTO);
	// b_code는 pk이기 때문에 delete method도 1개만 호출 가능(2개 이상이면 오류)
	public int delete(String b_code);
	
	
	
	
}
