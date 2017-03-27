package com.revanow.news.domain.dte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_blacklist")
public class BlackListdte {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "blackcode")
	private String blackcode;
	
	@Column(name = "finalval")
	private String finalval;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBlackcode() {
		return blackcode;
	}

	public void setBlackcode(String blackcode) {
		this.blackcode = blackcode;
	}

	public String getFinalval() {
		return finalval;
	}

	public void setFinalval(String finalval) {
		this.finalval = finalval;
	}
}
