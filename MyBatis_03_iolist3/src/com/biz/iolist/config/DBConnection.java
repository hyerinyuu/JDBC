package com.biz.iolist.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBConnection {

	
	private static SqlSessionFactory sqlSessionFactory;
	
	static {
		
		String configFile = "com/biz/iolist/config/mybatis-config.xml";
		InputStream inputStream = null;
		
		try {
			inputStream = Resources.getResourceAsStream(configFile);
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			
			/*
			 * sqlSessionFactory를 null인가 검사하는 이유 :
			 * 
			 */
			if(sqlSessionFactory == null) {
				sqlSessionFactory = builder.build(inputStream);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static SqlSessionFactory getSqlSessionFactory() {
		
		return sqlSessionFactory;
	}
	
	
}
