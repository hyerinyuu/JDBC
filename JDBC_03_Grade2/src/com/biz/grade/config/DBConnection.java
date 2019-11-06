package com.biz.grade.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	// return값 밑에 있어서 private 상관x
	private static Connection dbConn = null;
	
	// 		[static 생성자]
	// 프로젝트가 시작됨과 동시에 JVM에 의해서 자동으로 실행되는
	// 전역(전체에서 접근 가능한) 생성자 method
	
	// 싱글톤으로 dbConnection을 만든것과 같은 역할을 함.
	// 어떤 클래스든 상관 없이
	// dbConn = DriverManager~~~에 접근가능하게 하기 위해 static{}로 묶음
	static {
		
		String jdbcDriver = DBContract.DBConn.JdbcDriver;
		String url = DBContract.DBConn.URL;
		String user = DBContract.DBConn.USER;
		String password = DBContract.DBConn.PASSWORD;
		
		try {
			Class.forName(jdbcDriver);
			dbConn = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// 프로젝트가 시작하면서 생성된
	// dbCopnn(연결객체, 인스턴스)를 필요할 때 dbConn변수에 직접 접근하지 않고
	// getDBConnection method를 호출해 가져가서 사용할 수 있도록 dbConn return
	// single tone 기법과 유사
	public static Connection getDBConnection() {
		
		return dbConn;
	}
	
}
