package com.biz.addr.config;

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
			// Class를 이용해 jdbcDriver를 메모리에 로딩하여 사용할 준비하기
			Class.forName(jdbcDriver);
			dbConn = DriverManager.getConnection(url, user, password);
			System.out.println("DBConnection Success!");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// 외부에서 DBConnection을 가져갈 수 있게 하기 위해 return
	public static Connection getDBConnection() {
		
		return dbConn;
	}
	
	
}
