package com.revanow.news.web.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.revanow.news.bean.NewsdogsBean;
import com.revanow.news.domain.dte.Newsdogsdte;
import com.revanow.news.service.NewsService;
import com.revanow.news.util.Configuration;
import com.revanow.news.util.Curl;

@Controller
@RequestMapping("articles")
public class WebController extends BaseController {

	@Autowired
	private NewsService newsService;

	private Timestamp ts;

	private String day_background = "#FFF";
	private String day_color = "#000";
	private String night_background = "#444";
	private String night_color = "#FFF";

	private static final String detailUrl = Configuration.getInstance().getString("detail.url");

	private static final Logger logger = LoggerFactory.getLogger(WebController.class);

	@RequestMapping("")
	private String getWebView(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestParam(value = "type") String type, @RequestParam(value = "id") String id, @RequestParam(value = "nopic", required = false) String nopic,
			@RequestParam(value = "atnight", required = false) String atnight, @RequestParam(value = "font", required = false) String font,
			@RequestParam(value = "action", required = false) String action, @RequestParam(value = "touser", required = false) String touser,
			@RequestParam(value = "from", required = false) String from) throws IOException {
		response.setCharacterEncoding("UTF-8"); 
		Newsdogsdte ndb = newsService.getNewsById(id, language);
		String token;
		ts = new Timestamp(System.currentTimeMillis());
		String lang = (language == null ? "" : language);
		String typeface = (font == null) ? "m" : font;
		String isnight = (atnight == null) ? "no" : atnight;
		if (!"indonesian".equals(lang)) {
			switch (lang) {
			case "english":
				token = Configuration.getInstance().getString("eng.token");
				break;
			case "india":
				token = Configuration.getInstance().getString("ind.token");
				break;
			default:
				token = Configuration.getInstance().getString("eng.token");
				break;
			}

			String ApiUrl = this.choiceApiUrl(lang);
			ApiUrl = ApiUrl + "v2/articles/" + type + "/" + id + "/";
			String responseStr = Curl.curl_get(ApiUrl, "GET", token);
			logger.info(responseStr);
			JSONObject responseJson = JSON.parseObject(responseStr);
			ndb = new Newsdogsdte();
			if ("article".equals(type)) {
				ndb.setNews_id(responseJson.getString("id"));
				
				ndb.setTitle(responseJson.getString("title"));
				// newsDog.setContent();
				JSONArray topImageArr = responseJson.getJSONArray("top_images");
				String topImage = "";
				if (topImageArr.size() > 0) {
					for (int i = 0; i < topImageArr.size(); i++) {
						JSONObject topJson = topImageArr.getJSONObject(i);
						topImage += topJson.getString("origin") + ",";
					}
					topImage = topImage.substring(0, topImage.length() - 1);
				}
				ndb.setTop_image(topImage);
				String relatedImage = "";

				JSONArray relImageArr = responseJson.getJSONArray("related_images");
				if (relImageArr.size() > 0) {
					for (int i = 0; i < relImageArr.size(); i++) {
						JSONObject relJson = relImageArr.getJSONObject(i);
						relatedImage += relJson.getString("origin") + ",";
					}
					relatedImage = relatedImage.substring(0, relatedImage.length() - 1);
				}

				String content = responseJson.getString("content");
				Document doc = Jsoup.parse(content);
				Elements aTags = doc.getElementsByClass("image");
				if (relImageArr.size() > 0) {
					for (int i = 0; i < relImageArr.size(); i++) {
						JSONObject relJson = (JSONObject) relImageArr.get(i);
						String imageHtml = "<img src='" + relJson.getString("origin") + "'>";
						aTags.html(imageHtml);
					}
				}
				ndb.setContent(doc.html());
				responseJson.put("content", doc.html());
				ndb.setRelated_images(relatedImage);
				ndb.setSource(responseJson.getString("source"));
				ndb.setType(type);
				ndb.setPublished_at(ts.valueOf(responseJson.getString("published_at")));
				ndb.setSource_url(type);
				ndb.setT_language(lang);
			} else if ("youtube_video".equals(type)) {
				ndb.setNews_id(responseJson.getString("id"));
				ndb.setContent(responseJson.getString("content"));
				JSONArray topImageArr = responseJson.getJSONArray("top_images");
				String topImage = "";
				for (int i = 0; i < topImageArr.size(); i++) {
					JSONObject topJson = topImageArr.getJSONObject(i);
					topImage += topJson.getString("origin") + ",";
				}
				topImage = topImage.substring(0, topImage.length() - 1);
				ndb.setTop_image(topImage);
				JSONArray sourceArr = responseJson.getJSONArray("youtube");
				ndb.setSource(sourceArr.getString(0));
				ndb.setType(type);
				ndb.setSource_url(type);
				ndb.setT_language(lang);
			}
		}
		Newsdogsdte isset_new = newsService.getNewsById(id, lang);
		String detail_url = detailUrl + "?id=" + id + "&language=" + language + "&type=" + type;
		if (isset_new == null) {
			newsService.insertnews(ndb);
		} else {
			ndb.setId(isset_new.getId());
			newsService.updatenews(ndb);
		}
		if ("yes".equals(isnight)) {
			request.setAttribute("color", night_color);
			request.setAttribute("background", night_background);
		} else {
			request.setAttribute("color", day_color);
			request.setAttribute("background", day_background);
		}
		
		request.setAttribute("obj", ndb);
		request.setAttribute("from", from);
		request.setAttribute("language", lang);
		request.setAttribute("font", typeface);
		request.setAttribute("nopic", nopic);
		request.setAttribute("touser", touser);
		request.setAttribute("detail_url", detail_url);
		if ("article".equals(type)) {
			return "neo_article";
		} else {
			if ("share".equals(action)) {
				return "neo_video";
			} else {
				return "video";
			}

		}
	}
}
