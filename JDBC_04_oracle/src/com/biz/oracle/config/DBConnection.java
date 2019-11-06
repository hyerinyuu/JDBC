package com.biz.oracle.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static Connection dbConn = null;
	
	static {
		
		String jdbcDriver = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "user4";
		String password = "user4";
		
	
		try {
			
			// class를 이용해 jdbcDriver를 메모리에 로드하여 사용할 준비
			Class.forName(jdbcDriver);
			dbConn = DriverManager.getConnection(url, user, password);
			System.out.println("DBConnection OK!");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}// end static

	// Connection을 밖에서 가져다 쓸 수 있도록 return
	public static Connection getDBConnection() {
		
		return dbConn;
	}
	
	
	
}
