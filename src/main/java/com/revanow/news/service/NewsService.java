package com.revanow.news.service;


import com.revanow.news.domain.dte.Newsdogsdte;

public interface NewsService {

	public Newsdogsdte getNewsById(String news_id,String language);
	
	public int insertnews(Newsdogsdte news);
	
	public int updatenews(Newsdogsdte news);
}
