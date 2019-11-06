package com.biz.addr.exec;

import com.biz.addr.service.AddrCUDServiceV1;
import com.biz.addr.service.AddrServiceV1;

public class AddrEx_input {

	public static void main(String[] args) {

		AddrCUDServiceV1 aC = new AddrCUDServiceV1();
		AddrServiceV1 as = new AddrServiceV1();
		aC.inputAddr();
		
		
	}

}
