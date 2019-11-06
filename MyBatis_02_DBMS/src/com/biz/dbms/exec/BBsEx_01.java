package com.biz.dbms.exec;

import com.biz.dbms.service.BBsServiceV1;

public class BBsEx_01 {

	public static void main(String[] args) {

		BBsServiceV1 bbs = new BBsServiceV1();
		
		// 키보드로 입력받아서 게시글 작성
		bbs.writeBBS();
		// 입력된 게시판 확인하기
		bbs.viewBBsList();
		
		
		
	}

}
