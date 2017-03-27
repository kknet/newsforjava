package com.revanow.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revanow.news.domain.dte.Newsdogsdte;
import com.revanow.news.persistence.mapper.NewsMapper;
import com.revanow.news.service.NewsService;

@Service("newsService")
public class NewsDaoImpl implements NewsService {
	

	@Autowired
	private NewsMapper newsMapper;
	@Override
	public Newsdogsdte getNewsById(String news_id,String language) {
		// TODO Auto-generated method stub
		return newsMapper.getNewsById(news_id,language);
	}

	public int insertnews(Newsdogsdte news){
		
		return newsMapper.insertnews(news);
	}

	@Override
	public int updatenews(Newsdogsdte news) {
		// TODO Auto-generated method stub
		return newsMapper.updatenews(news);
	}
	
}
