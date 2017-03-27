package com.revanow.news.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface LTNewsService {
	
	public JSONArray category(String did,String ntype,String language);
	
	public JSONArray list(String did,String ntype,String language,String postData);
	
	public JSONObject article(String did,String ntype,String language,String postData,String toUser);

	public JSONArray search(String did,String ntype,String language,String postData);
	
	public JSONObject like(String did,String ntype,String language,String postData);
	
	public JSONObject unlike(String did,String ntype,String language,String postData);
}
