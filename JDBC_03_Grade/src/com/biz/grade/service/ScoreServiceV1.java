package com.biz.grade.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.biz.grade.config.DBContract;
import com.biz.grade.persistence.ScoreDTO;
import com.biz.grade.persistence.ScoreVO;

public abstract class ScoreServiceV1 {

	protected Connection dbConn = null;
	
	// 
	protected void dbConnection() {
		
		// DB연결을 해서 dbConn을 생성하기
		String jdbcDriver = DBContract.DBConn.JdbcDriver;
		String url = DBContract.DBConn.URL;
		String user = DBContract.DBConn.USER;
		String password = DBContract.DBConn.PASSWORD;
		
		try {
			Class.forName(jdbcDriver);
			// DB에 연결을 시도하면 DriverManager url과 user, password를 받아서 일함.
			// 그래서 자꾸 열고 닫기를 시도하면 비용(인력/시간)이 많이 듦.
			// 따라서 이 프로젝트에서는 프로젝트 전체에서 이 한 method를 공용해서 사용할 수 있도록 할 예정
			// ==> DBConnection class 참고
			dbConn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	// [꼭 생성해야하는 최소한의 method]
	// table의 값을 읽어올 CRUD 생성
	
	
	// 데이터를 읽어서 사용할 용도이므로 return값 List<ScoreVO>
	public abstract List<ScoreVO> selectAll();
	
	public abstract ScoreVO findbyID(long id);
	
	public abstract int insert(ScoreDTO scoreDTO);
	
	public abstract int update(ScoreDTO scoreDTO);
	
	public abstract int delete(ScoreDTO scoreDTO);
	
}

