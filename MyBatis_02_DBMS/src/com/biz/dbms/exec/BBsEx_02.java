package com.biz.dbms.exec;

import com.biz.dbms.service.BBsServiceV1;
import com.biz.dbms.service.BBsServiceV2_write;

public class BBsEx_02 {

	public static void main(String[] args) {
		
		BBsServiceV2_write bbs = new BBsServiceV2_write();

		
		bbs.viewBBsList();
		bbs.bbsMenu();
		
		
		
		
	}

}
