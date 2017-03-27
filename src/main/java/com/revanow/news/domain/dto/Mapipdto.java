package com.revanow.news.domain.dto;

public class Mapipdto {

	private int id;
	
	private long p_ip_from;
	
	private long p_ip_to;
	
	private String p_code;
	
	private String p_country;
	
	private String p_state;
	
	private String p_city;
	
	private String p_time_zone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getP_ip_from() {
		return p_ip_from;
	}

	public void setP_ip_from(long p_ip_from) {
		this.p_ip_from = p_ip_from;
	}

	public long getP_ip_to() {
		return p_ip_to;
	}

	public void setP_ip_to(long p_ip_to) {
		this.p_ip_to = p_ip_to;
	}

	public String getP_code() {
		return p_code;
	}

	public void setP_code(String p_code) {
		this.p_code = p_code;
	}

	public String getP_country() {
		return p_country;
	}

	public void setP_country(String p_country) {
		this.p_country = p_country;
	}

	public String getP_state() {
		return p_state;
	}

	public void setP_state(String p_state) {
		this.p_state = p_state;
	}

	public String getP_city() {
		return p_city;
	}

	public void setP_city(String p_city) {
		this.p_city = p_city;
	}

	public String getP_time_zone() {
		return p_time_zone;
	}

	public void setP_time_zone(String p_time_zone) {
		this.p_time_zone = p_time_zone;
	}
}
