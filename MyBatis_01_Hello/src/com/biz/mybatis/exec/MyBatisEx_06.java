package com.biz.mybatis.exec;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.biz.mybatis.config.DBConnection;
import com.biz.mybatis.mapper.BookDao;
import com.biz.mybatis.persistence.BookDTO;

public class MyBatisEx_06 {

	public static void main(String[] args) {


		SqlSession sqlSession 
			= DBConnection.getSqlSessionFactory().openSession(true);
		
		BookDao bookDao = sqlSession.getMapper(BookDao.class);
	
		bookDao.delete("B0042");
		
		List<BookDTO> bookList = bookDao.selectAll();
		for(BookDTO dto : bookList) {
			System.out.println(dto.toString());
		}
	}

}
