package com.revanow.news.domain.dto;


public class Newslikedto {
	
	private int id;
	private String news_id;
	private int like_count;
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
