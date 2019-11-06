# Java MyBatis 매입매출 프로젝트(2019-10-25)

### 오라클의 iolist2 Schema에 설정된 Table을 사용하여 매입매출 프로젝트 수행
* tbl_iolist, tbl_product, tbl_dept, view_iolist

### package : com.biz.iolist.persistence
* IolistDTO, ProductDTO, DeptDTO, IolistVO(view용)


### package : com.biz.iolist.config
* MyBatis-config.xml을 만들어서 Mybatis 초기설정하기 


### package : com.biz.iolist.config 
* iolist-mapper.xml, IolistDao(interface) 
* pro-mapper.xml, ProDao 
* dept-mapper.xml, DeptDao