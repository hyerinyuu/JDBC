package com.biz.grade.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import com.biz.grade.persistence.ScoreDTO;
import com.biz.grade.persistence.ScoreVO;
import com.biz.grade.utils.DBContract;

public class ScoreServiceV1 extends ScoreService {

	@Override
	public int insert(ScoreDTO scoreDTO) {
		
		this.dbConnection();
		PreparedStatement pStr = null;
		
		String sql = " INSERT INTO tbl_score ( " ;
		sql +=  " S_ID, " +
				" S_SCORE, " +
				" S_REM, " +
				" S_SUBJECT, " +
				" S_STD ) " ;
		sql += " VALUES(?,?,?,?,?) ";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setLong(1, scoreDTO.getS_id());
			pStr.setInt(2, scoreDTO.getS_score());
			pStr.setString(3, scoreDTO.getS_rem());
			pStr.setString(4, scoreDTO.getS_subject());
			pStr.setString(5, scoreDTO.getS_std());
			
			// 만약 insert가 정상적으로 수행되면
			// ret > 0
			// 그렇지 않으면 0, 
			// 간혹 < 0의 값이 나타나는 경우도 있다.
			int ret = pStr.executeUpdate();
			
			dbConn.close();
			
			return ret;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		return 0;
	}

	@Override
	public List<ScoreDTO> selectAll() {
		// TODO 여기는 모든 레코드를 조회하는 method
		this.dbConnection(); // dbConn 생성
		PreparedStatement pStr = null;
		/*
		String sql = " SELECT S_ID, S_STD, S_SUBJECT, " ;
			sql +=	" S_SCORE, S_REM FROM " ;
		sql += DBContract.TABLE.SCORE;
		*/
		String sql = DBContract.SQL.SCORE_SELECT;
		
		try {
			
			pStr = dbConn.prepareStatement(sql);
			ResultSet rst = pStr.executeQuery();
			List<ScoreDTO> scoreList = new ArrayList<ScoreDTO>();
			
			/*
			 * ResultSet으로부터 데이터를 getter 할 때
			 * 칼럼의 위치값(숫자)으로 사용하던 것을
			 * 지금 사용하는 버전에서는 칼럼 이름으로 사용할 수 있다.
			 */
			
			while(rst.next()) {
				
				scoreList.add(ScoreDTO.builder().s_id(rst.getLong("S_ID"))
												 .s_std(rst.getString("S_STD"))
												 .s_subject(rst.getString("S_SUBJECT"))
												 .s_score(rst.getInt("S_SCORE"))
												 .s_rem(rst.getString("S_REM"))
												 .build()
								);				 
			
				
			}
			rst.close();
			dbConn.close();
			
			return scoreList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public ScoreDTO findById(long id) {
		// TODO PK를 넘겨받아서 한개의 데이터를 보여주는 method
		this.dbConnection();
		PreparedStatement pStr = null;
		
		String sql = DBContract.SQL.SCORE_SELECT;
		sql += " WHERE s_id = ? ";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			// SQL문의 ? EOTLS 사용할 값을 setting
			pStr.setLong(1, id);
			
			// 값이 하나만 나와야 하므로
			ResultSet rst = pStr.executeQuery();
			ScoreDTO scoreDTO = null;
			// rst.next => 코드가 있냐
			if(rst.next()) {
				scoreDTO = ScoreDTO.builder().s_id(rst.getLong("S_ID"))
						.s_std(rst.getString("S_STD"))
						.s_score(rst.getInt("S_SCORE"))
						.s_subject(rst.getString("S_SUBJECT"))
						.s_rem(rst.getString("S_REM"))
						.build();
			}
			rst.close();
			dbConn.close();
			return scoreDTO;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public int update(ScoreDTO scoreVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ScoreDTO> findByName(String name) {

		this.dbConnection();
		PreparedStatement pStr = null;
		String sql = DBContract.SQL.SCORE_SELECT;
		sql += " WHERE s_std = ? ";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setString(1, name);
			ResultSet rst = pStr.executeQuery();
			
			List<ScoreDTO> scoreList = new ArrayList<ScoreDTO>();
			while(rst.next()) {
				
				scoreList.add(ScoreDTO.builder().s_id(rst.getLong("S_id"))
						.s_std(rst.getString("S_STD"))
						.s_score(rst.getInt("S_SCORE"))
						.s_subject(rst.getString("S_SUBJECT"))
						.s_rem(rst.getString("S_REM")).build());
						
			}
			rst.close();
			dbConn.close();
			return scoreList;
	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	
	}

	@Override
	public List<ScoreDTO> findBySubject(String subject) {
		// TODO Auto-generated method stub
		return null;
	}

}
