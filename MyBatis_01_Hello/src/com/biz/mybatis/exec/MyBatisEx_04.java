package com.biz.mybatis.exec;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.biz.mybatis.config.DBConnection;
import com.biz.mybatis.mapper.BookDao;
import com.biz.mybatis.persistence.BookDTO;

public class MyBatisEx_04 {

	public static void main(String[] args) {


		SqlSession sqlSession 
			= DBConnection.getSqlSessionFactory().openSession(true);
		
		BookDao bookDao = sqlSession.getMapper(BookDao.class);
		
		String[] codes = {"B0028",
						 "B0041",
						 "B0042"};
		
		for(String code : codes) {
			
			BookDTO bookDTO = BookDTO.builder()
					.b_code(code)
					.b_name(code + "-" + (int)(Math.random() * 10))
					.b_comp("경영원")
					.b_writer("내멋으로")
					.b_price(50000)
					.build();
			bookDao.update(bookDTO);
		}
		List<BookDTO> bookList = bookDao.selectAll();
		for(BookDTO dto : bookList) {
			System.out.println(dto.toString());
		}
	}

}
