package com.revanow.news.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.revanow.news.domain.dte.Languagemapdte;


public interface LanguageMapper {
	
	@Select("<script>select * from t_language_mapper where reg=#{reg} and lang=#{language}</script>")
	Languagemapdte FindByRegAndLanguage(@Param("reg") String reg,@Param("language") String language);
	
	
	@Select("<script>select * from t_language_mapper where reg = '' and lang=#{language}</script>")
	Languagemapdte FindByLanguage(@Param("language") String language);
	
}
