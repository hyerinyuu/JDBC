package com.biz.cbt.dao;

import java.util.List;

import com.biz.cbt.persistence.QuizDTO;

public interface QuizDao {

	public List<QuizDTO> selectAll();
	
	public QuizDTO findById(String cb_code);
	
	public int insert(QuizDTO quizDTO);
	public int update(QuizDTO quizDTO);
	public int delete(String cb_code);
	
}
