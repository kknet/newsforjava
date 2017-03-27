package com.revanow.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revanow.news.bean.FeedbackBean;
import com.revanow.news.domain.dte.Feedbackdte;
import com.revanow.news.persistence.mapper.FeedMapper;
import com.revanow.news.service.FeedService;

@Service("feedService")
public class FeedDaoImpl implements FeedService{

	@Autowired
	private FeedMapper feedMapper;
	@Override
	public int insertFeedBack(Feedbackdte feedback) {
		// TODO Auto-generated method stub
		return feedMapper.insertFeedBack(feedback);
	}

	
}
