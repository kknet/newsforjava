package com.revanow.news.domain.dte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_language_mapper")
public class Languagemapdte {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "reg")
	private String reg;
	
	@Column(name = "lang")
	private String lang;
	
	@Column(name = "newsversion")
	private String newsversion;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getNewsversion() {
		return newsversion;
	}

	public void setNewsversion(String newsversion) {
		this.newsversion = newsversion;
	}
}
