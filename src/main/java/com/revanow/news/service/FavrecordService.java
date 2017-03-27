package com.revanow.news.service;

import com.revanow.news.domain.dte.Favrecorddte;

public interface FavrecordService {
	
	public Favrecorddte getFavourLikeRecord(String news_id,String token);
	
	public int insertUserlike(Favrecorddte fav);
	
	public int updateUserlike(Favrecorddte fav);

}
