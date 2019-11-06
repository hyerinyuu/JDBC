# 주소록 프로젝트

* 오라클의 tbl_addr 테이블을 참조하여 주소록 프로젝트 수행
* base package : com.biz.addr
* ~.persistence : AddrDTO 클래스
* ~.service : AddrServiceV1 클래스 

	method : dbConnection 부분
			 selectAll();
			 findById(long id);
			 findByName(String name);
			 findByTel(String tel);
			 findByChain(String chain);
* 오라클 DBMS에 접속하기 위해 ojdbc설정
* lombok 설정

* Test를 위해 ~.exec : AddrEx_** 		