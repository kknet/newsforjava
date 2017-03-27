package com.revanow.news.domain.dto;

public class Regipdto {

	private long ip_from;
	
	private long ip_to;
	
	private String reg;

	public long getIp_from() {
		return ip_from;
	}

	public void setIp_from(long ip_from) {
		this.ip_from = ip_from;
	}

	public long getIp_to() {
		return ip_to;
	}

	public void setIp_to(long ip_to) {
		this.ip_to = ip_to;
	}

	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}
}
