package com.revanow.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revanow.news.domain.dte.Newslikedte;
import com.revanow.news.persistence.mapper.LikeMapper;
import com.revanow.news.service.LikeService;

@Service("likeService")
public class LikeDaoImpl implements LikeService{

	@Autowired
	private LikeMapper likeMap;
	
	@Override
	public Newslikedte getNewsLikeRecord(String news_id) {
		// TODO Auto-generated method stub
		return likeMap.getNewsLikeRecord(news_id);
	}

	@Override
	public int InsertUserFavour(String news_id) {
		// TODO Auto-generated method stub
		return likeMap.InsertUserFavour(news_id);
	}

	@Override
	public int InsertUserUnFavour(String news_id) {
		// TODO Auto-generated method stub
		return likeMap.InsertUserUnFavour(news_id);
	}

	@Override
	public int AddUserFavour(String news_id) {
		// TODO Auto-generated method stub
		return likeMap.AddUserFavour(news_id);
	}

	@Override
	public int AddUserUnFavour(String news_id) {
		// TODO Auto-generated method stub
		return likeMap.AddUserUnFavour(news_id);
	}

	@Override
	public int MinUserFavour(String news_id) {
		// TODO Auto-generated method stub
		return likeMap.MinUserFavour(news_id);
	}

	@Override
	public int MinUserUnFavour(String news_id) {
		// TODO Auto-generated method stub
		return likeMap.MinUserUnFavour(news_id);
	}

}
