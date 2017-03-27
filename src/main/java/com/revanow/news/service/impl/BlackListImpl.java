package com.revanow.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revanow.news.domain.dte.BlackListdte;
import com.revanow.news.persistence.mapper.BlackListMapper;
import com.revanow.news.service.BlackListService;

@Service("blacklistService")
public class BlackListImpl implements BlackListService{

	@Autowired
	private BlackListMapper black;
	
	@Override
	public BlackListdte checkBlackList(String blackcode) {
		// TODO Auto-generated method stub
		return black.checkBlackList(blackcode);
	}

}
