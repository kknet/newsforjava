package com.revanow.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revanow.news.bean.UsersubBean;
import com.revanow.news.domain.dte.Usersubdte;
import com.revanow.news.persistence.mapper.SubscribeMapper;
import com.revanow.news.service.SubService;

@Service("subService")
public class SubscribeDaoImpl implements SubService{

	@Autowired
	private SubscribeMapper sub;
	
	@Override
	public int recordUserSubscribe(Usersubdte bean) {
		// TODO Auto-generated method stub
		return sub.recordUserSubscribe(bean);
	}

}
