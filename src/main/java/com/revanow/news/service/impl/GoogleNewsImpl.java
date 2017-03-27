package com.revanow.news.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.revanow.news.domain.dte.Languagemapdte;
import com.revanow.news.domain.dte.Regipdte;
import com.revanow.news.service.GoogleNewsService;
import com.revanow.news.service.LanguageService;
import com.revanow.news.service.NewsService;
import com.revanow.news.service.RegipService;
import com.revanow.news.util.Configuration;
import com.revanow.news.util.newsHelper;

@Service("googleService")
public class GoogleNewsImpl implements GoogleNewsService {

	private static final String baseUrl = Configuration.getInstance().getString("lw.apiurl");

	private static final String defaultUrlCata = Configuration.getInstance().getString("lw.defaultcatagory");

	private static final String defaultUrlList = Configuration.getInstance().getString("lw.defaultlist");

	private static final Logger logger = LoggerFactory.getLogger(GoogleNewsImpl.class);

	@Autowired
	private RegipService regipService;

	@Autowired
	private LanguageService languageService;

	@Override
	public JSONArray category(String did, String ntype, String language, String location) {
		// TODO Auto-generated method stub

		
		String ApiUrl = baseUrl + "/news/type?lan=" + language + "&reg=" + location.toLowerCase();
		logger.info(ApiUrl);
		String result = HttpRequest.get(ApiUrl).body();
		logger.info(result);
		JSONObject googleReturn = JSON.parseObject(result);
		JSONArray responseJson;
		if (googleReturn.getInteger("code") == 0) {
			responseJson = this.formatCatagory(googleReturn);
		} else {
			responseJson = this.switchDefaultCatagory(language);
		}

		return responseJson;
	}

	@Override
	public JSONArray list(String did, String ntype, String language, String postData, String location) {
		// TODO Auto-generated method stub
		
		JSONObject requestJson = JSON.parseObject(postData);
		String categories = requestJson.getString("categories");
		String action = requestJson.getString("action");
		String read_tag = requestJson.getString("read_tag");
		// int req;
		// if ("next".equals(action)) {
		// req = 1;
		// } else {
		// req = Integer.parseInt(read_tag);
		// }
		int req;
		if (!read_tag.isEmpty()) {
			if ("next".equals(action)) {
				String[] read_arr = read_tag.split("_");
				if(read_arr.length >=2){
					int refreshTime = Integer.parseInt(read_arr[0]);
					req = refreshTime < 600 ? Integer.parseInt(read_arr[1]) : 1;
				}else{
					req = 1;
				}
			} else {
				req = Integer.parseInt(read_tag);
			}
		}else{
			req = 1;
		}

		String ApiUrl = baseUrl + "/news/listing?lan=" + language + "&reg=" + location.toLowerCase() + "&type=" + categories + "&page=" + req + "&limit=10";
		logger.info(ApiUrl);
		logger.info("google api start" + System.currentTimeMillis());
		String result = HttpRequest.get(ApiUrl).body();
		logger.info("google api end" + System.currentTimeMillis());
		logger.info(result);
		JSONObject googleReturn = JSON.parseObject(result);
		JSONArray responseJson = new JSONArray();
		if (googleReturn.getInteger("code") == 0) {
			JSONArray resultJson = googleReturn.getJSONObject("result").getJSONArray("datas");
			responseJson = this.formatList(resultJson, req);
		} else {
			responseJson = this.switchDefaultList(language, categories, req);
		}
		return responseJson;
	}

	public JSONArray switchDefaultCatagory(String language) {
		JSONArray catagoryArr = new JSONArray();
		// 切换默认地区
		Languagemapdte paramInfo = languageService.FindByLanguage(language);
		String finalResult;
		String apiUrl = defaultUrlCata;
		if (paramInfo != null) {
			String[] param = paramInfo.getNewsversion().split("_");
			apiUrl = baseUrl + "/news/type?lan=" + param[1] + "&reg=" + param[0];
		}
		logger.info(apiUrl);
		finalResult = HttpRequest.get(apiUrl).body();
		logger.info(finalResult);
		JSONObject finalJson = JSON.parseObject(finalResult);
		if (finalJson.getInteger("code") == 0) {
			catagoryArr = this.formatCatagory(finalJson);
		}
		return catagoryArr;
	}

	public JSONArray switchDefaultList(String language, String catagory, int req) {
		JSONArray catagoryArr = new JSONArray();
		// 切换默认地区
		Languagemapdte paramInfo = languageService.FindByLanguage(language);
		String ApiUrl = defaultUrlList + catagory+ "&page=" + req + "&limit=10";
		if (paramInfo != null) {
			String[] param = paramInfo.getNewsversion().split("_");
			ApiUrl = baseUrl + "/news/listing?lan=" + param[1] + "&reg=" + param[0] + "&type=" + catagory + "&page=" + req + "&limit=10";
			logger.info(ApiUrl);
		}
		logger.info("default google api start" + System.currentTimeMillis());
		String finalResult = HttpRequest.get(ApiUrl).body();
		logger.info("default google api end" + System.currentTimeMillis());
		logger.info(finalResult);
		JSONObject finalJson = JSON.parseObject(finalResult);
		if (finalJson.getInteger("code") == 0) {
			catagoryArr = this.formatList(finalJson.getJSONObject("result").getJSONArray("datas"), req);
		}
		return catagoryArr;
	}

	public JSONArray formatCatagory(JSONObject googleReturn) {
		JSONArray responseJson = new JSONArray();
		JSONArray resultJson = googleReturn.getJSONObject("result").getJSONArray("types");
		for (int i = 0; i < resultJson.size(); i++) {
			JSONObject index = resultJson.getJSONObject(i);
			JSONObject index2 = new JSONObject();
			index2.put("id", i);
			int share = index.getInteger("share");
			if (share == 1) {
				index2.put("catagory", index.getString("type"));
			} else {
				index2.put("catagory", index.getInteger("id"));
			}

			index2.put("share", index.getInteger("share"));
			index2.put("title", index.getString("name"));
			responseJson.add(index2);
		}
		return responseJson;
	}

	public JSONArray formatList(JSONArray list, int reqId) {
		JSONArray newsList = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject index = list.getJSONObject(i);
			JSONObject returnJson = new JSONObject();

			JSONObject Images = new JSONObject();
			Images.put("thumb", index.getString("img_url"));
			Images.put("thumb_width", 0);
			Images.put("origin", index.getString("img_url"));
			Images.put("thumb_height", 0);
			Images.put("width", 0);
			Images.put("height", 0);
			JSONArray imageList = new JSONArray();
			imageList.add(Images);

			JSONObject Images2 = new JSONObject();
			Images2.put("thumb", index.getString("img_url"));
			Images2.put("thumb_width", 0);
			Images2.put("origin", index.getString("img_url"));
			Images2.put("thumb_height", 0);
			Images2.put("width", 0);
			Images2.put("height", 0);
			JSONArray imageList2 = new JSONArray();
			imageList2.add(Images2);

			returnJson.put("id", index.getString("guid"));
			returnJson.put("published_at", index.getString("time"));
			returnJson.put("detail_url", index.getString("url"));
			returnJson.put("title", index.getString("title"));
			returnJson.put("type", "article");
			returnJson.put("top_images", imageList);
			returnJson.put("like_count", 0);
			returnJson.put("isjump", 1);
			returnJson.put("source", index.getString("source"));
			returnJson.put("source_url", index.getString("url"));
			returnJson.put("site_url", "");
			returnJson.put("op_recommend", false);
			returnJson.put("is_hot", 0);
			returnJson.put("comments_count", 0);
			returnJson.put("related_images", imageList2);
			returnJson.put("seq_id", String.valueOf(reqId + 1));
			newsList.add(returnJson);
		}
		return newsList;
	}

}
