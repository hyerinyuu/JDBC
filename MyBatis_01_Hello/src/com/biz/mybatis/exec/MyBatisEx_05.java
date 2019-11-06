package com.biz.mybatis.exec;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.biz.mybatis.config.DBConnection;
import com.biz.mybatis.mapper.BookDao;
import com.biz.mybatis.persistence.BookDTO;
import com.biz.mybatis.service.BookServiceV1;

public class MyBatisEx_05 {

	public static void main(String[] args) {

		BookServiceV1 bs = new BookServiceV1();
		bs.searchName();

	}
}
