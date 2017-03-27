package com.revanow.news.service;


import com.revanow.news.domain.dte.BlackListdte;

public interface BlackListService {
	
	BlackListdte checkBlackList(String blackcode);
}
