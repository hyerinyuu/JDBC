package com.biz.oracle.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biz.oracle.config.DBContrat;
import com.biz.oracle.persistence.BookDTO;

public class BookDaoImp extends BookDao {

	
	@Override
	public List<BookDTO> selectAll() {

		PreparedStatement pStr = null;
		String sql = DBContrat.SQL.SELECT_BOOKS;
		BookDao bookdao = null;
		List<BookDTO> bookList = null;
		// try catch로 묶어야 하기 때문에 선언과 인스턴스생성부분 분리
		try {
			// sql문자열을 JDBC 드라이버를 통해
			// DBMS로 전송하기 위한 데이터형으로 변환하는 절차
			pStr = dbConn.prepareStatement(sql);
			
			// DBMS에게 지금 보낸 SQL을 실행하여 얻어진 결과를 나에게 달라
			// DBMS가 보낸 결과를 ResultSet 에 받기
			ResultSet rst = pStr.executeQuery();
			
			bookList = new ArrayList<BookDTO>();
			
			// rst가 받은 데이터가 최소 1개의 record이상이면
			// rst.next() method,는 결과값이 true가 되고
			// rst내부에서는 record를 추출할 수 있도록 준비상태가 된다.
			
			// list로부터 resultSet을 가져오기 위해 반복문 사용
			// 반복문내에서 rst.next()를 실행하여 읽을 record가 있는가를 검사하고
			// 있으면 while() {} 내의 코드를 실행하여 값을 추출
			while(rst.next()) {

				BookDTO dto = this.rst_2_DTO(rst);
				// DTO를 List에 추가	
				bookList.add(dto);
			}
			rst.close();
			pStr.clearBatch();
			return bookList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private BookDTO rst_2_DTO(ResultSet rst) throws SQLException{
		
		// rst로부터 각 칼럼별로 데이터를 추출
		// 추출한 데이터를 DTO에 담고,
		// DTO를 List에 추가
		BookDTO dto = BookDTO.builder().b_code(rst.getString("B_code"))
				.b_name(rst.getString("B_NAME"))
				.b_comp(rst.getString("B_COMP"))
				.b_writer(rst.getString("B_WRITER"))
				.b_price(rst.getInt("B_PRICE")).build();
		
		return dto;
	}

	
	@Override
	public BookDTO findById(String b_code) {

		PreparedStatement pStr = null;
		String sql = DBContrat.SQL.SELECT_BOOKS;
		sql += " WHERE b_code = ? ";
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setString(1, b_code);
			ResultSet rst = pStr.executeQuery();
			BookDTO bookDTO = null;
			
			if(rst.next()) {
				
				bookDTO = this.rst_2_DTO(rst);
			}
			rst.close();
			pStr.close();
			return bookDTO;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public List<BookDTO> findByName(String b_name) {

		PreparedStatement pStr = null;
		String sql = DBContrat.SQL.SELECT_BOOKS;
		
		// " WHERE b_name LIKE '%' + ? + '%' "; -> MYSQL
		// " WHERE b_name LIKE '%' & ? & '%' "; -> MSSQL
		// == " WHERE b_name LIKE '%?%' " ; // SQL 문법오류 때문에 밑 코드처럼 써야함
		sql += " WHERE b_name LIKE '%' || ? || '%' ";
		
		try {
			// sql문자열을 DBMS에게 전달할 수 있는 데이터로 변환
			pStr = dbConn.prepareStatement(sql);
			
			// SQL문자열내의 ?대신 b_name 변수값을 추가하여
			// sql을 완성하라
			pStr.setString(1, b_name);
			
			// sql을 실행하여 결과를 rst에 받기
			ResultSet rst = pStr.executeQuery();
			
			List<BookDTO> bookList = new ArrayList<BookDTO>();
			
			while(rst.next()) {
				
				// bookList를 while문 안에서 선언하면
				// rst가 반복되면서 인스턴스가 반복회수만큼 새로 생성되기때문에
				// bookList에는 값이 1개 담기고 다시 새로 생성되고 1개 담기고 다시 새로생성되는 식의 코드가 되어서
				// 결국 bookList에는 tbl_book의 첫값만 담기게 됨
				// List<BookDTO> bookList = new ArrayList<BookDTO>();
				
				
				// rst로부터 각 칼럼별로 데이터를 추출,
				// 추출한 데이터를 DTO에 담고,
				// 별도의 method를 
				BookDTO dto = this.rst_2_DTO(rst);
				// DTO를 List에 추가	
				bookList.add(dto);
			
			}
			rst.close();
			pStr.close();
			return bookList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		
		return null;
	}

	@Override
	public List<BookDTO> findByComp(String b_comp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> findByWriter(String b_writer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> findByPrice(int price) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BookDTO> findByPrice(int sPrice, int ePrice) {

		PreparedStatement pStr = null;
		String sql = DBContrat.SQL.SELECT_BOOKS;
		sql += " WHERE b_price BETWEEN ? AND ? ";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setInt(1, sPrice);
			pStr.setInt(2, ePrice);
			
			ResultSet rst = pStr.executeQuery();
			
			List<BookDTO> bookList = new ArrayList<BookDTO>();
			while(rst.next()) {
				bookList.add(this.rst_2_DTO(rst));
			}
			rst.close();
			pStr.close();
			return bookList;

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int insert(BookDTO bookDTO) {

		PreparedStatement pStr = null;
		String sql = " INSERT INTO tbl_books ( ";
		sql += " B_CODE, " ;
		sql += " B_NAME, " ;
		sql += 	" B_COMP, " ;
		sql += 	" B_WRITER, " ;
		sql += 	" B_PRICE ) " ;
		sql += " VALUES ("
				+ " 'B' || LPAD(SEQ_BOOKS.NEXTVAL,4,'0'), "
				+ "?,"
				+ "?,"
				+ "?,"
				+ "? ) "; 
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setString(1, bookDTO.getB_name());
			pStr.setString(2, bookDTO.getB_comp());
			pStr.setString(3, bookDTO.getB_writer());
			pStr.setInt(4, bookDTO.getB_price());
			
			int ret = pStr.executeUpdate();
			
			pStr.close();
			return ret;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int update(BookDTO bookDTO) {

		PreparedStatement pStr = null;
		String sql = " UPDATE tbl_books SET " ;
				sql += " B_CODE = ?, "; 
				sql +=" B_NAME = ?, " ;
				sql += " B_COMP = ?, "  ;
				sql += " B_WRITER = ?, " ; 
				sql += " B_PRICE = ? ";
				sql += " WHERE B_CODE = ? ";
				
				try {
					pStr = dbConn.prepareStatement(sql);
					pStr.setString(1, bookDTO.getB_name());
					pStr.setString(2, bookDTO.getB_comp());
					pStr.setString(3, bookDTO.getB_writer());
					pStr.setInt(4, bookDTO.getB_price());
					pStr.setString(5, bookDTO.getB_code());
					
					int ret = pStr.executeUpdate();
					
					pStr.close();
					return ret;
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
		return 0;
	}

	@Override
	public int delete(String b_code) {

		PreparedStatement pStr = null;
		String sql = " DELETE FROM tbl_books ";
		sql += " WHERE b_code = ? " ;
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setString(1, b_code);
		
			int ret = pStr.executeUpdate();
			
			pStr.close();
			
			// service에서 제대로 업데이트 됐는지 확인하기 위해 넘겨주는 값
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return 0;
	}

}
