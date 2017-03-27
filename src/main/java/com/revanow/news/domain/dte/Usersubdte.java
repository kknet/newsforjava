package com.revanow.news.domain.dte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_user_subscribe")
public class Usersubdte {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "site_url")
	private String site_url;
	
	@Column(name = "did")
	private String did;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSite_url() {
		return site_url;
	}

	public void setSite_url(String site_url) {
		this.site_url = site_url;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}
}
