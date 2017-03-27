package com.revanow.news.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IndiaNewsService {

	public JSONArray category(String did,String ntype,String language);
	
	public JSONArray search(String did,String ntype,String language,String postData);
	
	public JSONArray medias(String did,String ntype,String language,String postData);
	
	public JSONArray subscribe(String did,String ntype,String language,String postData);
	
	public JSONObject putsubscribe(String did,String ntype,String language,String postData);
	
	public JSONObject delsubscribe(String did,String ntype,String language,String postData);
	
	public JSONArray city(String did,String ntype,String language);
	
	public JSONObject feedback(String did,String ntype,String language,String postData);
	
	public JSONArray medialist(String did,String ntype,String language,String postData);
	
	public JSONObject like(String did,String ntype,String language,String postData);
	
	public JSONObject unlike(String did,String ntype,String language,String postData);
	
	public JSONObject log(String language,String postData);
	
	public JSONObject collection(String language,String postData);
	
	public JSONObject register(String did,String ntype,String language,String postData);
	
	public JSONArray list(String did,String ntype,String language,String postData);
	
	public JSONObject article(String did,String ntype,String language,String postData,String toUser);
	
}
