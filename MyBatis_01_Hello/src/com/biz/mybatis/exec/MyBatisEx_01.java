package com.biz.mybatis.exec;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.biz.mybatis.config.DBConnection;
import com.biz.mybatis.mapper.BookDao;
import com.biz.mybatis.persistence.BookDTO;

public class MyBatisEx_01 {

	public static void main(String[] args) {

		/*
		 * JDBC의 다양한 클래스를 대신하여
		 * Java 어플리케이션과 DBMS간의 연결 Connection을 
		 * 대신 관리해줄 클래스(MyBatis)를 이용해 객체 선언
		 * 
		 * session : 네트워크 환경에서 지점과 지점 사이가 다양한 방법으로 연결되고  
		 * 			데이터를 주고받을 준비가 된 통로 ex) DBMS <------> Java 
		 */
		SqlSession sqlSession 
			= DBConnection.getSqlSessionFactory().openSession(true);
		
		BookDao bookDao = sqlSession.getMapper(BookDao.class);
		
		List<BookDTO> bookList = bookDao.selectAll();
		for(BookDTO dto : bookList) {
			
			System.out.println(dto.toString());
			
		}
		// config파일을 읽어서
		// sqlSesstionFactory에게
		// session을 열라고 지시
		
		// session(통로)가 만들어 지고 session에게  BookDao interface를 참조해서 dao를 만들라고 지시
		// <mapper resource="com/biz/mybatis/mapper/book-mapper.xml"/>	
		// book-maper.xml의 selectAll method를 호출해서 selectAll이라는 이름의 mapper파일을 찾아
		
		// 그 안에 있는 sql을 dbms에게 전달
		// dbms의 결과를 mybatis가 다시 받아서 dto에 담음(리턴값을 참조해서)
		
		
	}

}
