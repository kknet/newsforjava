package com.revanow.news.domain.dte;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_newsdog")
public class Newsdogsdte  {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "news_id")
	private String news_id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "top_image")
	private String top_image;
	
	@Column(name = "top_image_width")
	private int top_image_width;
	
	@Column(name = "top_image_height")
	private int top_image_height;
	
	@Column(name = "related_images")
	private String related_images;
	
	@Column(name = "related_image_width")
	private int related_image_width;
	
	@Column(name = "related_image_height")
	private int related_image_height;
	
	@Column(name = "source")
	private String source;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "published_at")
	private Timestamp published_at;
	
	@Column(name = "source_url")
	private String source_url;
	
	@Column(name = "t_language")
	private String t_language;
	
	@Column(name = "t_location")
	private String t_location;
	
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
	
	public String getT_location() {
		return t_location;
	}
	
	public void setT_location(String t_location) {
		this.t_location = t_location;
	}
}
