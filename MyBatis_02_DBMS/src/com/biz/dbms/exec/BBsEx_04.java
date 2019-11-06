package com.biz.dbms.exec;

import com.biz.dbms.service.BBsServiceV4_update;

public class BBsEx_04 {

	public static void main(String[] args) {
		
		BBsServiceV4_update bs = new BBsServiceV4_update();
		
		bs.viewBBsList();
		bs.updateBBs();
		
	}
}
