package com.biz.mybatis.exec;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.biz.mybatis.config.DBConnection;
import com.biz.mybatis.mapper.BookDao;
import com.biz.mybatis.persistence.BookDTO;

public class MyBatisEx_03 {

	public static void main(String[] args) {


		SqlSession sqlSession 
			= DBConnection.getSqlSessionFactory().openSession(true);
		
		BookDao bookDao = sqlSession.getMapper(BookDao.class);
		
		BookDTO bookDTO = BookDTO.builder()
				.b_name("MyBatis")
				.b_comp("경영원")
				.b_writer("내멋으로")
				.b_price(50000)
				.build();
		bookDao.insert(bookDTO);
		
		List<BookDTO> bookList = bookDao.selectAll();
		for(BookDTO dto : bookList) {
			System.out.println(dto.toString());
		}
	}

}
