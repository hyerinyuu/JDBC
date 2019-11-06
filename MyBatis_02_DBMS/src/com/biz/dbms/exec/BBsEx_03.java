package com.biz.dbms.exec;

import com.biz.dbms.service.BBsServiceV1;
import com.biz.dbms.service.BBsServiceV2_write;
import com.biz.dbms.service.BBsServiceV3_delete;

public class BBsEx_03 {

	public static void main(String[] args) {
		
		BBsServiceV3_delete bbs = new BBsServiceV3_delete();
		
		bbs.viewBBsList();
		bbs.deleteBBs();
		
		
		
		
	}

}
