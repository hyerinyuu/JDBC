package com.biz.grade.persistence.dao;

import java.sql.Connection;
import java.util.List;

import com.biz.grade.config.DBConnection;
import com.biz.grade.config.DBContract;
import com.biz.grade.persistence.domain.DeptDTO;
import com.biz.grade.persistence.domain.StudentDTO;


public abstract class DeptDao {
	
	protected Connection dbConn = null;
	
	public DeptDao() {
		this.dbConn = DBConnection.getDBConnection();
	}

	// 조건 없이 모든 데이터를 조회하는 method
	// List<> 형태의 데이터를 return
	 public abstract List<DeptDTO> selectAll();
	 
	 // PK를 조건(ID)으로 데이터를 조회하는 method
	 // PK를 조건으로 조회한다는 것 : 출력되는 데이터가 1개 (Record)이다. (2이상이면 pk라고 볼수 없음 개체무결성 무너진상태)
	 // VO(DTO) 형태의 데이터를 return해야하기 때문에
	 public abstract DeptDTO findById(String d_code);
		

	 public abstract List<DeptDTO> findByName(String d_name);
	

	 public abstract int insert(DeptDTO deptDTO);
	 public abstract int update(DeptDTO deptDTO);
	 
	 // delete도 pk를 조건으로 해야하기 때문에 findById와 같은 형식이 됨
	 public abstract int delete(String d_num);
		
	
	
}
