package com.biz.persistence.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.biz.addr.config.DBContract;
import com.biz.persistence.AddrDTO;

public class AddrDaoImp extends AddrDao {

	@Override
	public List<AddrDTO> selectAll() {

		PreparedStatement pStr = null;
		String sql = DBContract.SELECT_ADDR;
		AddrDao addrDao = null;
		List<AddrDTO> addrList = null;
		
		try {
			pStr = dbConn.prepareStatement(sql);
			ResultSet rst = pStr.executeQuery();
			
			addrList = new ArrayList<AddrDTO>();
			
			while(rst.next()) {
				
				// id, name, tel, addr, chain
				AddrDTO addrDTO = this.rst_2_DTO(rst);
						
				addrList.add(addrDTO);
			}
			rst.close();
			pStr.close();
			return addrList;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private AddrDTO rst_2_DTO(ResultSet rst) throws SQLException {
		
		AddrDTO addrDTO = AddrDTO.builder().id(rst.getInt("ID"))
				.name(rst.getString("NAME"))
				.tel(rst.getString("TEL"))
				.addr(rst.getString("ADDR"))
				.chain(rst.getString("CHAIN"))
				.build();
		
		return addrDTO;
	}
	

	@Override
	public AddrDTO findByID(long id) {

		PreparedStatement pStr = null;
		String sql = DBContract.SELECT_ADDR;
		sql += " WHERE id = ? ";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setLong(1, id);
			ResultSet rst = pStr.executeQuery();
			
			// return을 해줘야하는데 if문 안에서 addrDTO를 선언하면 scope에서 벗어나서
			// 오류나니까 밖에서 선언해줘야함
			AddrDTO addrDTO = null;
			
			if(rst.next()) {
				
				addrDTO = this.rst_2_DTO(rst);
				
			}
			rst.close();
			pStr.close();
			return addrDTO;
			
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
		
		
		return null;
	}

	@Override
	public List<AddrDTO> findByName(String name) {
		
		PreparedStatement pStr = null;
		String sql = DBContract.SELECT_ADDR;
		sql += " WHERE name LIKE '%' || ? || '%' ";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setString(1, name);
			ResultSet rst = pStr.executeQuery();
			
			List<AddrDTO> addrList = new ArrayList<AddrDTO>();
			AddrDTO addrDTO = null;
			
			while(rst.next()) {
				
				addrDTO = this.rst_2_DTO(rst);
				addrList.add(addrDTO);
				
			}
			rst.close();
			pStr.close();
			return addrList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
	}

	@Override
	public List<AddrDTO> findByTel(String tel) {

		PreparedStatement pStr = null;
		String sql = DBContract.SELECT_ADDR;
		sql += " WHERE tel LIKE '%' || ? || '%' ";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setString(1, tel);
			ResultSet rst = pStr.executeQuery();
			
			List<AddrDTO> addrList = new ArrayList<AddrDTO>();
			AddrDTO addrDTO = null;
			while(rst.next()) {
				
				addrDTO = this.rst_2_DTO(rst);
				addrList.add(addrDTO);
			}
			rst.close();
			pStr.close();
			return addrList;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public List<AddrDTO> findByChain(String chain) {

		PreparedStatement pStr = null;
		String sql = DBContract.SELECT_ADDR;
		sql += " WHERE chain = ? ";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setString(1, chain);
			
			ResultSet rst = pStr.executeQuery();
			List<AddrDTO> addrList = new ArrayList<AddrDTO>();
			AddrDTO addrDTO = null;
			while(rst.next()) {
				
				addrDTO = this.rst_2_DTO(rst);
				addrList.add(addrDTO);
				
			}
			rst.close();
			pStr.close();
			return addrList;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int insert(AddrDTO addrDTO) {

		PreparedStatement pStr = null;
		String sql = " INSERT INTO tbl_addr ( ID, " + 
											" NAME, " + 
											" TEL, " + 
											" ADDR, " + 
											" CHAIN ) " +
											" VALUES(SEQ_ADDR.NEXTVAL,?,?,?,?) ";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setString(1, addrDTO.getName());
			pStr.setString(2, addrDTO.getTel());
			pStr.setString(3, addrDTO.getAddr());
			pStr.setString(4, addrDTO.getChain());
			
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
	public int delete(long longId) {

		PreparedStatement pStr = null;
		String sql = " DELETE FROM tbl_addr ";
		sql += " WHERE id = ? ";
		
		try {
			pStr = dbConn.prepareStatement(sql);
			pStr.setLong(1, longId);
			
			int ret = pStr.executeUpdate();
			
			pStr.close();
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return 0;
	}

}
