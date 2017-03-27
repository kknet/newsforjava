package com.revanow.news.domain.dto;


public class Favrecorddto {
	
	private int id;
	private String news_id;
	private String token;
	private String like;
	private String unlike;
	
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLike() {
		return like;
	}
	public void setLike(String like) {
		this.like = like;
	}
	public String getUnlike() {
		return unlike;
	}
	public void setUnlike(String unlike) {
		this.unlike = unlike;
	}
}
