package com.revanow.news.service;

import com.alibaba.fastjson.JSONArray;

public interface GoogleNewsService {
	
	public JSONArray category(String did,String ntype,String language,String location);
	
	public JSONArray list(String did,String ntype,String language,String postData,String location);
	
}
