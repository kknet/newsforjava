package com.revanow.news.bean;

import java.sql.Timestamp;



public class NewsdogsBean  {
	
	private int id;
	private String news_id;
	private String title;
	private String content;
	private String top_image;
	private int top_image_width;
	private int top_image_height;
	private String related_images;
	private int related_image_width;
	private int related_image_height;
	private String source;
	private String type;
	private Timestamp published_at;
	private String source_url;
	private String t_language;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTop_image() {
		return top_image;
	}
	public void setTop_image(String top_image) {
		this.top_image = top_image;
	}
	public int getTop_image_width() {
		return top_image_width;
	}
	public void setTop_image_width(int top_image_width) {
		this.top_image_width = top_image_width;
	}
	public int getTop_image_height() {
		return top_image_height;
	}
	public void setTop_image_height(int top_image_height) {
		this.top_image_height = top_image_height;
	}
	public String getRelated_images() {
		return related_images;
	}
	public void setRelated_images(String related_images) {
		this.related_images = related_images;
	}
	public int getRelated_image_width() {
		return related_image_width;
	}
	public void setRelated_image_width(int related_image_width) {
		this.related_image_width = related_image_width;
	}
	public int getRelated_image_height() {
		return related_image_height;
	}
	public void setRelated_image_height(int related_image_height) {
		this.related_image_height = related_image_height;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Timestamp getPublished_at() {
		return published_at;
	}
	public void setPublished_at(Timestamp published_at) {
		this.published_at = published_at;
	}
	public String getSource_url() {
		return source_url;
	}
	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}
	public String getT_language() {
		return t_language;
	}
	public void setT_language(String t_language) {
		this.t_language = t_language;
	}
	
	

}
