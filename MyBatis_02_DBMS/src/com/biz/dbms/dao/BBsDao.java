package com.biz.dbms.dao;

import java.util.List;

import com.biz.dbms.persistence.BBsDTO;

public interface BBsDao {
	
	// 일종의 패턴 공식이니까 return type이랑 매개변수지정 한거 이해하기
	
	// 모든것을 다 가져오기 때문에 매개변수x return type List
	public List<BBsDTO> selecAll();
	
	public BBsDTO findById(long bs_id);
	
	// cud는 return 타입 항상 int
	public int insert(BBsDTO bbsDTO);
	public int update(BBsDTO bbsDTO);
	
	// 애는 findById랑 같은 매개변수를 가짐
	public int delete(long bs_id);
	
}
