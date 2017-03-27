package com.revanow.news.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revanow.news.domain.dte.Languagemapdte;
import com.revanow.news.persistence.mapper.LanguageMapper;
import com.revanow.news.service.LanguageService;

@Service("languageService")
public class LanguageImpl implements LanguageService{

	
	@Autowired
	private LanguageMapper lMapper;
	
	
	@Override
	public Languagemapdte FindByRegAndLanguage(String reg, String language) {
		// TODO Auto-generated method stub
		return lMapper.FindByRegAndLanguage(reg, language);
	}

	@Override
	public Languagemapdte FindByLanguage(String language) {
		// TODO Auto-generated method stub
		return lMapper.FindByLanguage(language);
	}

}
