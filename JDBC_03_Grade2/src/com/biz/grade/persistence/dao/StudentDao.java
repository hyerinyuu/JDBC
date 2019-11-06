package com.biz.grade.persistence.dao;

import java.sql.Connection;
import java.util.List;

import com.biz.grade.config.DBConnection;
import com.biz.grade.persistence.domain.StudentDTO;

// 스프링 프레임 워크를 배우면 DAO를 전부 interface로 선언해야하지만
// 우리는 아직 단계에 못미쳐서 중간역할쯤 하는 abs class로 선언
public abstract class StudentDao {

	protected Connection dbConn = null;
	
	public StudentDao() {
		this.dbConn = DBConnection.getDBConnection();	
	}
	
	// 조건 없이 모든 데이터를 조회하는 method
	// List<> 형태의 데이터를 return
	 public abstract List<StudentDTO> selectAll();
	 
	 // PK를 조건(ID)으로 데이터를 조회하는 method
	 // PK를 조건으로 조회한다는 것 : 출력되는 데이터가 1개 (Record)이다. (2이상이면 pk라고 볼수 없음 개체무결성 무너진상태)
	 // VO(DTO) 형태의 데이터를 return해야하기 때문에
	 public abstract StudentDTO findById(String st_num);
		
	 // 학생 이름으로 조회해서 결과를 return하는 method
	 // 학생이름으로 검색을 해야하기 때문에 매개변수는 st_name
	 // 만약 동명이인이 무슨일이 있어도 절대 없다면 return값이 1개이기 때문에 StudentDTO이어야 하지만
	 // 그럴 가능성이 희박하므로 return 값은 List
	 public abstract List<StudentDTO> findByName(String st_name);
	
	 public abstract List<StudentDTO> findByGrade(int st_grade);
	 
	// DTO에 학생 데이터를 저장하여 method에 주입한 후 INSERT 수행	
	 public abstract int insert(StudentDTO stdDTO);
	 public abstract int update(StudentDTO stdDTO);
	 
	 // delete도 pk를 조건으로 해야하기 때문에 findById와 같은 형식이 됨
	 public abstract int delete(String st_num);
		
	
	
}
