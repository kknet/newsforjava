package com.revanow.news.web.controller.x;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.revanow.news.util.Api;
import com.revanow.news.util.Configuration;
import com.revanow.news.web.controller.BaseController;
import com.revanow.news.web.controller.v.ApiV1Controller;

@Controller
@RequestMapping("/x1/api")
public class ApiX1Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ApiV1Controller.class);

	private static final String JSUrl = Configuration.getInstance().getString("jc.apiurl");

	private static final String JS_CHANNEL = Configuration.getInstance().getString("jc.channel");

	private static final String INDONESIAN = "indonesian";

	@RequestMapping("catagory")
	private void getCatagoryInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) {
		//String lang = (language == null ? INDONESIAN : language);
		//lang = switch_language(lang);
		String ApiUrl = JSUrl + "v2/categories?channel=" + JS_CHANNEL + "&country=id&languages=id";
		String result = HttpRequest.get(ApiUrl).body();
		logger.info(result);
		JSONArray resultJson = JSON.parseObject(result).getJSONArray("data");
		JSONArray responseJson = new JSONArray();
		JSONObject popular = new JSONObject();
		popular.put("id", 0);
		popular.put("catagory","popular");
		popular.put("title","Popular");
		responseJson.add(popular);
		for (int i = 0; i < resultJson.size(); i++) {
			JSONObject index = new JSONObject();
			JSONObject catagory = resultJson.getJSONObject(i);
			index.put("id", i+1);
			index.put("catagory",catagory.getString("code"));
			index.put("title",catagory.getString("native_name"));
			responseJson.add(index);
		}
		try {
			Api.response(responseJson, response, "result_array");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("IOException",e);
		}
	}

	@RequestMapping("102")
	private void getNewsList(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) throws IOException {
		response.setContentType("Content-Type: text/json; charset=utf-8");
		String lang = (language == null ? INDONESIAN : language);
		lang = switch_language(lang);
		String postData = getPostContentBody(request);
		JSONObject requestJson = JSON.parseObject(postData);
		String categories = requestJson.getString("categories");
		//String action = requestJson.getString("action");
		String read_tag = requestJson.getString("read_tag");
		String ApiUrl = JSUrl;
		if ("popular".equals(categories)) {
			ApiUrl += "v2/articles/popular?country=id&channel=" + JS_CHANNEL + "&language=" + lang + "&image_list=true";
			if (!read_tag.isEmpty()) {
				ApiUrl += "&req_id=" + read_tag;
			}
		} else {
			ApiUrl += "v2/articles?country=id&channel=" + JS_CHANNEL + "&language=" + lang + "&image_list=true&topics=" + categories;
			if (!read_tag.isEmpty()) {
				ApiUrl += "&req_id=" + read_tag;
			}
		}

		logger.info(ApiUrl + "-->" + postData);
		String result = HttpRequest.get(ApiUrl).body();
		logger.info(result);
		String reqId = JSON.parseObject(result).getString("req_id");
		JSONArray newsList = JSON.parseObject(result).getJSONArray("data");
		JSONArray responseJson = this.formatList(newsList, reqId);
		Api.response(responseJson, response, "result_array");
	}

	@RequestMapping("articles")
	public void getFavour(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) throws IOException {
		//String lang = (language == null ? INDONESIAN : language);
		//lang = switch_language(lang);
		String postData = getPostContentBody(request);
		JSONObject requestJson = JSON.parseObject(postData);
		//String token = requestJson.getString("token");
		String id = requestJson.getString("id");
		//String type = requestJson.getString("type");
		String relatedNewsApi = JSUrl + "/v2/articles/related?uuid=" + id + "&channel=" + JS_CHANNEL;
		System.out.println(relatedNewsApi);
		String relatedNewsStr = HttpRequest.get(relatedNewsApi).body();
		JSONArray relatedNewsList = JSON.parseObject(relatedNewsStr).getJSONArray("data");
		JSONObject responseJson = new JSONObject();
		responseJson.put("favour", new JSONArray());
		if (relatedNewsList.size() > 0) {
			String reqId = JSON.parseObject(relatedNewsStr).getString("req_id");
			JSONArray relatedNewsJson = this.formatList(relatedNewsList, reqId);
			responseJson.put("favour", relatedNewsJson);
		}

		Api.response(responseJson, response, "result_array");
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
