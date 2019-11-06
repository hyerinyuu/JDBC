package com.biz.iolist.exec;

import org.apache.ibatis.session.SqlSession;

import com.biz.iolist.config.DBConnection;
import com.biz.iolist.service.IolistServiceV1;

public class IolistEx_01 {

	public static void main(String[] args) {

	
		IolistServiceV1 ios = new IolistServiceV1();
		ios.viewIolist();
		
		
	}

}
