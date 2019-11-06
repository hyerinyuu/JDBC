package com.biz.jdbc.exec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcEx_01 {

	public static void main(String[] args) {

		System.out.println("Hello 오라클");
		String jdbcDriver = "oracle.jdbc.driver.OracleDriver";  // oracle thin driver주소
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String userName = "grade";
		String password = "grade";
		
		try {
			// Class=>자바의 class관리자, 외부에 있는 driver을 관리하는 class
			// driver class파일을 읽어서 쓸수 있도록 드라이버를 메모리에 띄워놔라(사용할 준비를 해줘라)
			/*
			 * #####
			 * jdbcDriver(oracle.jdbc.driver.OracleDriver.class 파일)을 사용할 수 있도록 
			 * JVM 에 등록(loading)하라고 지시하는 코드
			 */
			Class.forName(jdbcDriver);
			Connection dbConn = null;
			// [Connection 설정]
			// 1. db를 연결하는 통로(=Connection)
			// 2. Connection의 DriverManager라는 class를 사용해 getConnection() method를 사용해서 
			//  	url, userName, password를 매개변수로 받는 db객체를 만듦
			// 		(new keyword 안씀 : 이 프로젝트에서 한개만 만들어야 해서)
			/*
			 * ###
			 * url : 연결주소
			 * userName : DB사용자
			 * password : 사용자의 비번
			 * 을 사용하여 DBMS와 SQL을 주고받을 통로를 설정
			 */
			dbConn = DriverManager.getConnection(url,userName,password);
			System.out.println("Connection OK!");
			
			// 여기까지 완료 하면	 [DBMS에게 SQL을 전송하는 절차]		를 시작해야함
			// JDBC와 우리의 APP간의 데이터 교환이 이루어지는 영역
			PreparedStatement pStr = null;
			
			// 연결된 통로를 통해서 SQL문을 전달하고 
			// 그 결과를 pStr변수에 받는다.
			String sql = " SELECT * FROM tbl_score ";
			pStr = dbConn.prepareStatement(sql);
			
			// SQL문(SELECT)을 실행하도록 지시
			// SELECT 된 결과를 ResultSet에 받아라
			ResultSet rst = pStr.executeQuery();
			
			/*
			 * 이제 rst 객체에는 DB로부터 읽어온 리스트가 
			 * ResultSet 이라는 데이터type으로 저장되어 있을 것이다.
			 * 이 ResultSet은 일종의 Iterator처럼 취급할 수 있다. 
			 * 
			 * rst.next() method가 한번 실행 될때마다
			 * 데이터 리스트의 앞쪽부터 한개씩 꺼내서 읽을 수 있도록 준비를 한다.
			 * 
			 * 또 rst.next() method가 실행되면
			 * 현재 위치의 다음번 리스트 요소를 읽을 수 있도록 준비한다.
			 * 
			 * 마지막 리스트를 모두 읽은 후 또 rst.next()가 실행되면
			 * false를 return하여 while을 끝내도록 해준다.
			 */ 
			// 사용법
			while(rst.next()) {
				// 읽을 준비가 된 리스트 요소의 1번 칼럼의 값을 꺼내라(id)
				System.out.print(rst.getString(1) + "\t");
				
				// id와 점수는 number(숫자)형식으로 저장됐기 때문에 그냥 String도 가능하고 getInt method로 읽을수도 있음
				// ### 저장된 데이터 칼럼의 type이 숫자형이면 숫자 get() method로 읽을 수 있다.
				// System.out.println(rst.getInt(1) + "\t");
				
				// 읽을 준비가 된 리스트 요소의 2번 칼럼의 값을 꺼내라(name)
				System.out.print(rst.getString(2) + "\t");
				System.out.print(rst.getString(3) + "\n");
			}
			// 열린 통로 닫아주기
			rst.close();
			dbConn.close();
		
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
