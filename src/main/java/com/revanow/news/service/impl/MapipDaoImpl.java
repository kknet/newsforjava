package com.revanow.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revanow.news.bean.MapipBean;
import com.revanow.news.domain.dte.Mapipdte;
import com.revanow.news.persistence.mapper.MapipMapper;
import com.revanow.news.service.MapipService;

@Service("mapService")
public class MapipDaoImpl implements MapipService{

	@Autowired
	private MapipMapper mapMap;
	
	@Override
	public Mapipdte getLocationForIp(long ip) {
		// TODO Auto-generated method stub
		return mapMap.getLocationForIp(ip);
	}

}
