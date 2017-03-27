package com.revanow.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revanow.news.bean.FavrecordBean;
import com.revanow.news.domain.dte.Favrecorddte;
import com.revanow.news.persistence.mapper.FavrecodeMapper;
import com.revanow.news.service.FavrecordService;

@Service("favrecordService")
public class FavrecordDaoImpl implements FavrecordService{

	@Autowired
	private FavrecodeMapper favMapper;
	
	@Override
	public Favrecorddte getFavourLikeRecord(String news_id, String token) {
		// TODO Auto-generated method stub
		return favMapper.getFavourLikeRecord(news_id, token);
	}

	@Override
	public int insertUserlike(Favrecorddte fav) {
		// TODO Auto-generated method stub
		return favMapper.insertUserlike(fav);
	}

	@Override
	public int updateUserlike(Favrecorddte fav) {
		// TODO Auto-generated method stub
		return favMapper.updateUserlike(fav);
	}

}
