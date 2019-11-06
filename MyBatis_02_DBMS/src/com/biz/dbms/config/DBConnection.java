package com.biz.dbms.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBConnection {

	// 클래스는 기본값이 null이라 따로 초기화를 해주지 안정적임(굳이 써도 상관x)
	// (변수는 null로 꼭 초기화 해줘야함)
	private static SqlSessionFactory sqlSessionFactory;
	
	static {
		
		// config.xml 파일을 읽어서 MyBatis 초기 설정값을 가져온다.
		String configFile = "com/biz/iolist/config/mybatis-config.xml";
		InputStream inputStream = null;
		
		try {
			// configFile 파일을 읽어오는 절차 
			inputStream = Resources.getResourceAsStream(configFile);
			
			// sqlSessionFactory를 Single Tone으로 생성하기 위한 절차
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			if(sqlSessionFactory == null) {
				sqlSessionFactory = builder.build(inputStream);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// end static
	
	public static SqlSessionFactory getSqlSessionFactory() {
		
		return sqlSessionFactory;
	}
	
}
