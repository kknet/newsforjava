package com.revanow.news.domain.dte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_feedback")
public class Feedbackdte {

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "question")
	private String question;
	
	@Column(name = "subtime")
	private String subtime;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getSubtime() {
		return subtime;
	}
	
	public void setSubtime(String subtime) {
		this.subtime = subtime;
	}
}
