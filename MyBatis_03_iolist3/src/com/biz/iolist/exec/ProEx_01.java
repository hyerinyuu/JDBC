package com.biz.iolist.exec;

import org.apache.ibatis.session.SqlSession;

import com.biz.iolist.config.DBConnection;
import com.biz.iolist.service.IolistServiceV1;
import com.biz.iolist.service.pro.ProductServiceV1;

public class ProEx_01 {

	public static void main(String[] args) {

		ProductServiceV1 ps = new ProductServiceV1();
		
		ps.proUpdate();
		
	}

}
