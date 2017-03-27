package com.revanow.news.service;


import com.revanow.news.domain.dte.Languagemapdte;

public interface LanguageService {

	
	public Languagemapdte FindByRegAndLanguage( String reg,String language);
	
	
	public Languagemapdte FindByLanguage( String language);
}
