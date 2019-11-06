package com.biz.grade.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.biz.grade.config.DBConnection;
import com.biz.grade.config.DBContract;
import com.biz.grade.persistence.ScoreDTO;
import com.biz.grade.persistence.ScoreVO;

public abstract class ScoreServiceV2 {

	protected Connection dbConn = null;
	
	// ScoreV2의 생성자
	public ScoreServiceV2() {
		this.dbConn = DBConnection.getDBConnection();
		
	}
	
	// [꼭 생성해야하는 최소한의 method]
	// table의 값을 읽어올 CRUD 생성
		
	// 데이터를 읽어서 사용할 용도이므로 return값 List<ScoreVO>
	public abstract List<ScoreVO> selectAll();
	
	public abstract ScoreVO findByID(long id);
	
	public abstract List<ScoreVO> findByStName(String stName);
	
	public abstract int insert(ScoreDTO scoreDTO);
	
	public abstract int update(ScoreDTO scoreDTO);
	
	public abstract int delete(long id);

	public abstract List<ScoreVO> findByStNum(String strStNum);

	public abstract List<ScoreVO> findBySubject(String strSubject);
	
}

