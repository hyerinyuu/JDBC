package com.biz.rent.dao;

import java.util.List;

import com.biz.rent.persistence.RentviewVO;

public interface ViewDao {

	public RentviewVO findByID(int seq);

}
