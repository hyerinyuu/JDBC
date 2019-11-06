package com.biz.jdbc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biz.jdbc.domain.ScoreVO;

public class ScoreJDBCServiceV2 {

	// jdbc를 이용해서 DBMS를 가져오는 방식들
	/*
	protected String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
	protected String url = "jdbc:oracle:thin:@localhost:1521:xe";
	protected String userName = "grade";
	protected String passWord = "grade";
	*/

	protected Connection dbConn = null;
	protected PreparedStatement pStr = null;
	
	protected List<ScoreVO> scoreList = null;
	
	public List<ScoreVO> getScoteList(){
		
		return this.scoreList;
	}
	
	protected void dbConnection() {
		
		try {
			Class.forName(DBConstract.DB_INFO.JdbcDriver);
			dbConn = DriverManager.getConnection(DBConstract.DB_INFO.URL,
												DBConstract.DB_INFO.USERNAME,
												DBConstract.DB_INFO.PASSWORD);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<ScoreVO> selectAll(){
		
		String sql = " SELECT * FROM tbl_score ";
		this.select(sql);
		// getter를 쓰지 않고 여기서 return
		return this.scoreList;
	}
	
	public List<ScoreVO> findById(int s_id){
		
		// id값을 매개변수로 받아서 해당 아이디를 리스트로 보여라
		String sql = " SELECT * FROM tbl_score ";
		sql += " WHERE s_id = " + s_id;
		
		this.select(sql);
		return this.scoreList;
		
	}
	
	// 이름으로 조회해서 리스트를 리턴하는 method
	public List<ScoreVO> findByName(String s_name){
		
		String sql = " SELECT * FROM tbl_score ";
		sql += " WHERE s_std = " + s_name + "";
		// sql += " WHERE s_std = '" + s_name + "'";
		// 작은따옴표를 넣는 이유 : SQL에서 문자열로 인식하기 위해서(없으면 오류)
		// 아님 불러올때 "'NAME'" 이런식으로 불러야함
		this.select(sql);
		return this.scoreList;
	}
	
	protected void select(String sql) {
		
		this.dbConnection();
		this.scoreList = new ArrayList<ScoreVO>();
				
		try {
			pStr = dbConn.prepareStatement(sql);
			ResultSet rst = pStr.executeQuery();
			while(rst.next()) {
				ScoreVO sVO = ScoreVO.builder()
						.s_id(rst.getInt(DBConstract.SCORE.S_ID))
						.s_std(rst.getString(DBConstract.SCORE.S_STD))
						.s_score(rst.getInt(DBConstract.SCORE.S_SCORE))
						.s_rem(rst.getString(DBConstract.SCORE.S_REM))
						.build();
				
				scoreList.add(sVO);
			
			}
			pStr.close();
			dbConn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
