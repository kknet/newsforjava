package com.revanow.news.domain.dto;

public class BlackListdto {
	
	private int id;
	
	private String blackcode;
	
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