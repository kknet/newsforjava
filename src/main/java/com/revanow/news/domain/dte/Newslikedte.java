package com.revanow.news.domain.dte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_news_like")
public class Newslikedte {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "news_id")
	private String news_id;
	
	@Column(name = "like_count")
	private int like_count;
	
	@Column(name = "unlike_count")
	private int unlike_count;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNews_id() {
		return news_id;
	}
	
	public void setNews_id(String news_id) {
		this.news_id = news_id;
	}
	
	public int getLike_count() {
		return like_count;
	}
	
	public void setLike_count(int like_count) {
		this.like_count = like_count;
	}
	
	public int getUnlike_count() {
		return unlike_count;
	}
	
	public void setUnlike_count(int unlike_count) {
		this.unlike_count = unlike_count;
	}
}
