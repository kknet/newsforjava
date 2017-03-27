package com.revanow.news.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.revanow.news.service.JCNewsService;
import com.revanow.news.util.Configuration;

@Service("jcnewsService")
public class JCNewsImpl implements JCNewsService {

	private static final Logger logger = LoggerFactory.getLogger(JCNewsImpl.class);

	private static final String JSUrl = Configuration.getInstance().getString("jc.apiurl");

	private static final String JS_CHANNEL = Configuration.getInstance().getString("jc.channel");

	private static final String JS_DEFAULT_CATEGORY = Configuration.getInstance().getString("jc.defaultcategoryurl");

	private static final String JS_DEFAULT_LIST = Configuration.getInstance().getString("jc.defaultlisturl");

	@Override
	public JSONArray category(String did, String ntype, String language, String location) {
		String ApiUrl = JSUrl + "v2/categories?channel=" + JS_CHANNEL + "&country=" + location + "&languages=" + language;
		String result = HttpRequest.get(ApiUrl).body();
		// 获取返回值
		JSONObject resultJson = JSON.parseObject(result);
		JSONArray responseJson = new JSONArray();
		if (resultJson.getInteger("status") == 0) {
			responseJson = this.formatCategory(resultJson.getJSONArray("data"));
		} else { //切换默认值
			String defaultResult = HttpRequest.get(JS_DEFAULT_CATEGORY).body();
			JSONObject defaultVal = JSON.parseObject(defaultResult);
			responseJson = this.formatCategory(defaultVal.getJSONArray("data"));
		}
		return responseJson;
	}

	@Override
	public JSONArray list(String did, String ntype, String language, String postData, String location) {
		// TODO Auto-generated method stub
		JSONObject requestJson = JSON.parseObject(postData);
		String categories = requestJson.getString("categories");
		String read_tag = requestJson.getString("read_tag");
		String ApiUrl = JSUrl;
		ApiUrl += "v2/articles?country=" + "us" + "&channel=" + JS_CHANNEL + "&language=" + "en" + "&image_list=true&topics=" + categories;
		if (!read_tag.isEmpty()) {
			ApiUrl += "&req_id=" + read_tag;
		}

		logger.info(ApiUrl + "-->" + postData);
		String result = HttpRequest.get(ApiUrl).body();
		logger.info(result);
		JSONArray responseJson = new JSONArray();
		JSONObject newsList = JSON.parseObject(result);
		int status = newsList.getIntValue("status");
		if (status == 0) {
			String reqId = newsList.getString("req_id");
			JSONArray newsListArr = newsList.getJSONArray("data");
			responseJson = this.formatList(newsListArr, reqId);
		} else { //切换默认值
			String defaultApiList = JS_DEFAULT_LIST + categories;
			if (!read_tag.isEmpty()) {
				defaultApiList += "&req_id=" + read_tag;
			}
			String defaultResult = HttpRequest.get(defaultApiList).body();
			logger.info(defaultResult);
			JSONObject defaultNewsList = JSON.parseObject(defaultResult);
			String reqId = defaultNewsList.getString("req_id");
			JSONArray defaultNewsListArr = defaultNewsList.getJSONArray("data");
			responseJson = this.formatList(defaultNewsListArr, reqId);
		}
		return responseJson;
	}

	public JSONArray formatCategory(JSONArray list) {
		JSONArray object = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject index = new JSONObject();
			JSONObject catagory = list.getJSONObject(i);
			index.put("id", i);
			index.put("catagory", catagory.getString("code"));
			index.put("title", catagory.getString("native_name"));
			object.add(index);
		}
		return object;
	}

	public JSONArray formatList(JSONArray list, String reqId) {
		JSONArray newsList = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject data = list.getJSONObject(i);
			JSONObject index = new JSONObject();

			JSONObject imageJson = data.getJSONObject("Images").getJSONObject("mainImage");
			int width = imageJson.getIntValue("width");
			int height = imageJson.getIntValue("height");
			String imageUrl = imageJson.getString("url");
			// imageUrl = imageUrl.replace("https://", "http://");
			index.put("detail_url", data.getString("URL"));
			index.put("like_count", 0);
			index.put("source", "");
			index.put("title", data.getString("Header"));
			index.put("type", "article");
			index.put("source_url", data.getString("URL"));
			index.put("op_recommend", false);
			index.put("is_hot", 1);
			index.put("site_url", "");
			index.put("isjump", 1);
			index.put("comments_count", 0);
			index.put("seq_id", reqId);
			index.put("id", data.getString("ArticleId"));
			index.put("published_at", data.getString("Published"));

			JSONArray imageInfo = new JSONArray();
			if (width != -1 && height != -1) {
				JSONObject imageIndex = new JSONObject();
				imageIndex.put("origin", imageUrl);
				imageIndex.put("height", height);
				imageIndex.put("thumb", imageUrl);
				imageIndex.put("thumb_height", height);
				imageIndex.put("thumb_width", width);
				imageIndex.put("width", width);
				imageIndex.put("source", imageUrl);
				imageInfo.add(imageIndex);
			}

			JSONArray imageInfo2 = new JSONArray();
			if (width != -1 && height != -1) {
				JSONObject imageIndex2 = new JSONObject();
				imageIndex2.put("origin", imageUrl);
				imageIndex2.put("height", height);
				imageIndex2.put("thumb", imageUrl);
				imageIndex2.put("thumb_height", height);
				imageIndex2.put("thumb_width", width);
				imageIndex2.put("width", width);
				imageIndex2.put("source", imageUrl);
				imageInfo2.add(imageIndex2);
			}
			index.put("top_images", imageInfo);
			index.put("related_images", imageInfo2);
			newsList.add(index);
		}
		return newsList;
	}

}
