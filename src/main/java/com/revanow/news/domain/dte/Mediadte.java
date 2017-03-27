package com.revanow.news.domain.dte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_media_list")
public class Mediadte {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "source_id")
	private String source_id;
	
	@Column(name = "site_url")
	private String site_url;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "language")
	private String language;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSource_id() {
		return source_id;
	}

	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}

	public String getSite_url() {
		return site_url;
	}

	public void setSite_url(String site_url) {
		this.site_url = site_url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
