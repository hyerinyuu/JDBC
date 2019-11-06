package com.biz.grade.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.biz.grade.persistence.ScoreDTO;
import com.biz.grade.persistence.StudentDTO;
import com.biz.grade.utils.DBContract;

/*
 * abstract 추상클래스 선언
 * 		: 일부는 구현된 method
 * 		  일부는 형태만 갖는 method
 */
public abstract class StudentService {

	protected Connection dbConn = null;
	
	// dbConn을 설정하여 DBMS에 접속할 수 있는 통로 설정
	protected void dbConnection() {
		
		try {
			Class.forName(DBContract.DbConn.JdbcDriver);
			dbConn = DriverManager.getConnection(
					DBContract.DbConn.URL,
					DBContract.DbConn.USER,
					DBContract.DbConn.PASSWORD);
			
			System.out.println("DbConnection OK!!!");
		} catch (ClassNotFoundException e) {
			
			System.out.println("JDBC드라이버를 찾지 못함!!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("DBMS 접속 오류!!");
		}
	}
	
	// CRUD
	// 최소 이 다섯가지 method를 유지해야함 ==> crud규칙
	public abstract int insert(StudentDTO studentDTO);
	
	// 모든 record == 1개 이상의 record이기 때문에 무조건 list
	public abstract List<StudentDTO> selectAll();
	
	// 이 method는 id값을 매개변수로 받아서 
	// 한개의 record만 조회하는 method id==PK
	// 왜 return값이 ScoreVO인지 생각해보기
	public abstract StudentDTO findById(String num);
	
	// 동명이인이 있을 수 있으므로
	// 검색결과는 1개 이상 ==> 리턴값을 리스트로 설정해야함
	public abstract List<StudentDTO> findByName(String name);
	
	
	public abstract List<StudentDTO>findBySubject(String subject);
	
	// vo로부터 id칼럼을 가지고 와서 
	// 1이상의 값을 리턴하므로 int형으로 리턴값(만약 0이면 처리하는 코드를 짤 예정)
	public abstract int update(StudentDTO studentDTO);
	
	// delete를 하려면 무결성유지를 위해 pk를 사용해야 하므로 id를 매개변수로 받는다.
	public abstract int delete(long id);
	
	
	
	
}
