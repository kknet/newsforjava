package com.revanow.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revanow.news.domain.dte.Regipdte;
import com.revanow.news.persistence.mapper.RegipMapper;
import com.revanow.news.service.RegipService;

@Service("regipService")
public class RegipDaoImpl implements RegipService{

	
	@Autowired
	private RegipMapper ipMapper;
	
	@Override
	public Regipdte getRegForIp(long ip) {
		// TODO Auto-generated method stub
		return ipMapper.getRegForIp(ip);
	}

}
