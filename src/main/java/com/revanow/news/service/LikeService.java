package com.revanow.news.service;

import com.revanow.news.domain.dte.Newslikedte;

public interface LikeService {
	
	public Newslikedte  getNewsLikeRecord(String news_id);
	
	public int InsertUserFavour(String news_id);

	public int InsertUserUnFavour(String news_id);

	public int AddUserFavour(String news_id);

	public int AddUserUnFavour(String news_id);

	public int MinUserFavour(String news_id);

	public int MinUserUnFavour(String news_id);

}
