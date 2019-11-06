package com.biz.jdbc.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biz.jdbc.domain.ScoreVO;

public class ScoreJDBCServiceV3 {

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
		this.dbConnection();
		try {
			pStr = dbConn.prepareStatement(sql);
			this.setScoreList(pStr);
			dbConn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.scoreList;
		
	}
	
	public List<ScoreVO> findById(int s_id){
		
		
		String sql = " SELECT * FROM tbl_score ";
		sql += " WHERE s_id = ? "; // query문의 ?가 있는 위치에
		// sql += " WHERE s_id = " + s_id
		
		this.dbConnection();
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setInt(1, s_id); // 첫번째 위치에 s_id가 있는 
			
			this.setScoreList(pStr);
			dbConn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.scoreList;
	
	}
	
	// 아이디값을 2개 전달 받아서 범위 검색을 수행하는 method
	public List<ScoreVO> findById(int s_id, int e_id){
		
		
		String sql = " SELECT * FROM tbl_score ";
		sql += " WHERE s_id BETWEEN ? AND ? "; // query문의 ?가 있는 위치에(? : 대치문자열) 
		// sql += " WHERE s_id = " + s_id
		
		this.dbConnection();
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setInt(1, s_id); // 첫번째 위치의 물음표 값에 전달받은 s_id를 붙이고
			pStr.setInt(2, e_id); // 두번째 위치의 물음표 값에 전달받은 s_id를 붙여라
			
			this.setScoreList(pStr);
			dbConn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.scoreList;
	
	}

	

	public List<ScoreVO> findByName(String s_name){
		
		String sql = " SELECT * FROM tbl_score ";
		sql += " WHERE s_std = ? ";
	
		this.dbConnection();
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setString(1, s_name);
			
			this.setScoreList(pStr);
			dbConn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.scoreList;
	
	}
	
	// ResultSet에서 데이터를 추출하여 List로 변환하는 method
	protected void setScoreList(PreparedStatement pStr) throws SQLException {
		
		this.scoreList = new ArrayList<ScoreVO>();
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
			rst.close();
	}
	
	public int insert(ScoreVO scoreVO) {
		
		String sql = " INSERT INTO tbl_score ( ";
		sql += " s_id, ";
		sql += " s_std, ";
		sql += " s_subject, ";
		sql += " s_score, ";
		sql += " s_rem ) ";
		
		// INSERT 수행할 VALUES값이 채워질 부분을
		// ? 대치문자로 처리
		sql += " VALUES(?, ?, 001, ?, ?)";
		
		this.dbConnection();
		
		try {
			pStr = dbConn.prepareStatement(sql);
			// 대치문자 위치에
			// scoreVO의 각 요소를 getter하여 
			// setting
			pStr.setLong(1, scoreVO.getS_id());
			pStr.setString(2, scoreVO.getS_std() );
			pStr.setInt(3, scoreVO.getS_score());
			pStr.setString(4, scoreVO.getS_rem());
			
			// update() method를 실행하여 INSERT 수행
			int ret = pStr.executeUpdate();
			return ret;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		return 0;
	}
	
}
