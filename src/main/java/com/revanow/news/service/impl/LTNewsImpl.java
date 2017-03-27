package com.revanow.news.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.revanow.news.bean.NewsdogsBean;
import com.revanow.news.bean.NewslikeBean;
import com.revanow.news.domain.dte.Favrecorddte;
import com.revanow.news.domain.dte.Newsdogsdte;
import com.revanow.news.domain.dte.Newslikedte;
import com.revanow.news.service.FavrecordService;
import com.revanow.news.service.LTNewsService;
import com.revanow.news.service.LikeService;
import com.revanow.news.service.NewsService;
import com.revanow.news.util.Configuration;
import com.revanow.news.util.newsHelper;

@Service("ltnewsService")
public class LTNewsImpl implements LTNewsService {

	private static final Logger logger = LoggerFactory.getLogger(LTNewsImpl.class);

	private static final String lturl = Configuration.getInstance().getString("lt.apiurl");

	private static final String ltchannel = Configuration.getInstance().getString("lt.channel");

	private static final String detailUrl = Configuration.getInstance().getString("detail.url");

	private Timestamp ts;

	@Autowired
	private LikeService likeService;

	@Autowired
	private FavrecordService favService;

	@Autowired
	private NewsService newsService;

	@Override
	public JSONArray category(String did, String ntype, String language) {
		// TODO Auto-generated method stub
		String ApiUrl = lturl + "?s=/ApiNews/channel&siteid=1&country=id";

		String result = HttpRequest.get(ApiUrl).body();
		logger.info(result);
		JSONArray resultJson = JSON.parseObject(result).getJSONArray("data");
		JSONArray responseJson = new JSONArray();
		for (int i = 0; i < resultJson.size(); i++) {
			JSONObject index = new JSONObject();
			JSONObject catagory = resultJson.getJSONObject(i);
			index.put("id", i + 1);
			index.put("catagory", catagory.getString("tid"));
			index.put("title", newsHelper.upperCase(catagory.getString("cnname").toLowerCase()));
			responseJson.add(index);
		}
		return responseJson;
	}

	@Override
	public JSONArray list(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		JSONObject requestJson = JSON.parseObject(postData);
		String categories = requestJson.getString("categories");
		String action = requestJson.getString("action");
		String read_tag = requestJson.getString("read_tag");
		String ApiUrl = lturl;
		ApiUrl += "?s=/ApiNews/get";
		String req = "1";
		if (!read_tag.isEmpty()) {
			if ("next".equals(action)) {
				String[] read_arr = read_tag.split("_");
				int refreshTime = Integer.parseInt(read_arr[0]);
				if (refreshTime < 600) {
					req = read_arr[1];
				} else {
					req = "1";
				}
			} else {
				req = read_tag;
			}
		}
		logger.info(req);
		logger.info(ApiUrl + "-->" + postData);
		Map<String, String> postApiData = new HashMap<String, String>();
		postApiData.put("channelid", ltchannel);
		postApiData.put("siteid", "1");
		postApiData.put("tid", categories);
		postApiData.put("country", "ID");
		postApiData.put("Page", req);
		postApiData.put("PageSize", "15");
		String result = HttpRequest.post(ApiUrl, postApiData, true).body();
		logger.info(result);
		JSONArray newsList = new JSONArray();
		if (JSON.parseObject(result).getJSONArray("data") != null) {
			newsList = formatList(JSON.parseObject(result).getJSONArray("data"), String.valueOf((Integer.parseInt(req) + 1)));
		}

		return newsList;
	}

	@Override
	public JSONObject article(String did, String ntype, String language, String postData, String toUser) {
		// TODO Auto-generated method stub
		JSONObject requestJson = JSON.parseObject(postData);
		String token = requestJson.getString("token");
		String id = requestJson.getString("id");
		String type = requestJson.getString("type");
		String newsDetailJson = HttpRequest.get(id).body();
		logger.info("-->" + postData);
		logger.info(newsDetailJson);
		JSONObject newsDetail = JSON.parseObject(newsDetailJson).getJSONObject("data");
		JSONObject responseObj = new JSONObject();
		String newsContent = newsDetail.getString("content");
		Document doc = Jsoup.parse(newsContent);

		Elements aTags = doc.getElementsByTag("a");
		for (int i = 0; i < aTags.size(); i++) {
			aTags.get(i).attr("href", "javascript:void(0)");
		}
		Elements parentTags = doc.getElementsByTag("img");
		for (int i = 0; i < parentTags.size(); i++) {
			parentTags.get(i).parent().attr("style", "max-width:100%;");
		}

		Elements divTags = doc.getElementsByTag("div");
		for (int i = 0; i < divTags.size(); i++) {
			String heightStyle = "margin:0px auto;width:120px;height:600px;";
			if (divTags.get(i).attr("style").equals(heightStyle)) {
				divTags.get(i).remove();
			}
			divTags.get(i).attr("style", "width:100%;");
		}

		URL source = null;
		try {
			source = new URL(newsDetail.getString("source"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.info(e.toString());
		}
		responseObj.put("share_url", detailUrl + "?id=" + newsDetail.getString("nid") + "&language=indonesian&type=" + type + "&action=share&touser=" + toUser);
		responseObj.put("detail_url", detailUrl + "?id=" + newsDetail.getString("nid") + "&language=indonesian&type=" + type);
		responseObj.put("ad_type", "fb");
		responseObj.put("source", newsDetail.getString("copyfrom"));
		responseObj.put("title", newsDetail.getString("title"));
		responseObj.put("load_ad", true);
		responseObj.put("type", "article");
		responseObj.put("source_url", newsDetail.getString("source"));
		responseObj.put("content", doc.html());

		responseObj.put("subscribed", false);
		responseObj.put("related_images", new JSONArray());
		responseObj.put("id", newsDetail.getString("nid"));
		responseObj.put("published_at", newsDetail.getString("update_time"));
		responseObj.put("partner_switch", true);
		responseObj.put("site_url", source.getHost());

		Newslikedte nlb = likeService.getNewsLikeRecord(id);
		if (nlb != null) {
			responseObj.put("like_count", nlb.getLike_count());
			responseObj.put("unlike_count", nlb.getUnlike_count());
		} else {
			responseObj.put("like_count", 0);
			responseObj.put("unlike_count", 0);
		}

		Favrecorddte userlike = favService.getFavourLikeRecord(id, token);
		if (userlike == null) {
			responseObj.put("liked", 0);
		} else if ("true".equals(userlike.getLike())) {
			responseObj.put("liked", 1);
		} else if ("true".equals(userlike.getUnlike())) {
			responseObj.put("liked", 2);
		}
		Elements imgTags = doc.getElementsByTag("img");
		for (int i = 0; i < imgTags.size(); i++) {
			imgTags.get(i).attr("width", "100%");
		}
		Newsdogsdte ndb = new Newsdogsdte();
		ndb.setContent(doc.html());
		ndb.setNews_id(newsDetail.getString("nid"));
		ndb.setSource(newsDetail.getString("copyfrom"));
		ndb.setSource_url(newsDetail.getString("source"));
		ndb.setT_language("indonesian");
		ndb.setTitle(newsDetail.getString("title"));
		ndb.setType("article");
		JSONArray picList = newsDetail.getJSONArray("pic") == null ? new JSONArray() : newsDetail.getJSONArray("pic");
		String imgList = "";
		for (int i = 0; i < picList.size(); i++) {
			imgList += picList.getJSONObject(i).getString("subphoto");
		}
		ndb.setRelated_images(imgList);
		ndb.setTop_image(imgList);
		Newsdogsdte issetNews = newsService.getNewsById(newsDetail.getString("nid"), "indonesian");
		if (issetNews != null) {
			ndb.setId(issetNews.getId());
			newsService.updatenews(ndb);
		} else {
			newsService.insertnews(ndb);
		}
		String relatedApiUrl = lturl + "?s=/News/getRef&channelid=" + ltchannel + "&nid=" + newsDetail.getString("nid");
		String relatedNewsJson = HttpRequest.get(relatedApiUrl).body();
		JSONArray relatedNewsList = new JSONArray();
		if (JSON.parseObject(relatedNewsJson).getJSONArray("data") != null) {
			relatedNewsList = formatList(JSON.parseObject(relatedNewsJson).getJSONArray("data"), "0");
		}
		responseObj.put("favour", relatedNewsList);
		return responseObj;
	}

	@Override
	public JSONArray search(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub

		JSONObject requestJson = JSON.parseObject(postData);
		// String token = requestJson.getString("token");
		String keyword = requestJson.getString("keyword");
		try {
			keyword = URLEncoder.encode(keyword, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.info("UnsupportedEncodingException", e);
		}

		int from = requestJson.containsKey("from") ? requestJson.getIntValue("from") : 1;
		// int size = requestJson.containsKey("size") ?
		// requestJson.getIntValue("size") : 10;
		logger.info(postData);
		String ApiUrl = lturl + "?s=/Search/getSearch&channelid=" + ltchannel + "&Page=" + from + "&content=" + keyword;
		logger.info(ApiUrl);
		String searchNews = HttpRequest.get(ApiUrl).body();
		JSONArray relatedNewsList = new JSONArray();
		if (JSON.parseObject(searchNews).containsKey("data")) {
			relatedNewsList = formatList(JSON.parseObject(searchNews).getJSONArray("data"), "0");
		}

		JSONArray searchList = new JSONArray();
		for (int i = 0; i < relatedNewsList.size(); i++) {
			JSONObject index = relatedNewsList.getJSONObject(i);
			String title = index.getString("title").replaceAll("(?i)" + keyword, "<font color=\"red\">" + keyword + "</font>");
			index.put("title", title);
			searchList.add(index);
		}

		return searchList;
	}

	public JSONArray formatList(JSONArray list, String reqId) {
		JSONArray newsList = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			JSONObject data = list.getJSONObject(i);
			JSONObject index = new JSONObject();

			JSONArray imageJson = data.getJSONArray("imgs");

			index.put("detail_url", detailUrl + "?id=" + data.getString("nid") + "&language=indonesian&type=article");
			index.put("like_count", 0);
			index.put("source", data.getString("copyfrom"));
			index.put("title", data.getString("title"));
			index.put("type", "article");
			index.put("source_url", data.getString("url"));
			index.put("op_recommend", false);
			index.put("is_hot", 0);
			index.put("comments_count", 0);
			index.put("isjump", 0);
			index.put("seq_id", reqId);
			index.put("id", data.getString("json_url"));
			index.put("published_at", data.getString("update_time"));

			JSONArray imageInfo = new JSONArray();
			JSONArray imageInfo2 = new JSONArray();
			for (int j = 0; j < imageJson.size(); j++) {
				JSONObject imageIndex = new JSONObject();
				imageIndex.put("origin", imageJson.get(j));
				imageIndex.put("height", 100);
				imageIndex.put("thumb", imageJson.get(j));
				imageIndex.put("thumb_height", 100);
				imageIndex.put("thumb_width", 100);
				imageIndex.put("width", 100);
				imageIndex.put("source", imageJson.get(j));

				JSONObject imageIndex2 = new JSONObject();
				imageIndex2.put("origin", imageJson.get(j));
				imageIndex2.put("height", 100);
				imageIndex2.put("thumb", imageJson.get(j));
				imageIndex2.put("thumb_height", 100);
				imageIndex2.put("thumb_width", 100);
				imageIndex2.put("width", 100);
				imageIndex2.put("source", imageJson.get(j));

				imageInfo.add(imageIndex);
				imageInfo2.add(imageIndex2);
			}

			index.put("top_images", imageInfo);
			index.put("related_images", imageInfo2);
			newsList.add(index);
		}
		return newsList;
	}

	@Override
	public JSONObject like(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		JSONObject requestJson = JSON.parseObject(postData);
		String token = requestJson.getString("token");
		String id = requestJson.getString("id");
		Newslikedte likeRecord = likeService.getNewsLikeRecord(id);
		Favrecorddte fav = favService.getFavourLikeRecord(id, token);
		Favrecorddte updateFav = new Favrecorddte();
		if (likeRecord != null) {
			if (fav != null) {
				if ("true".equals(fav.getLike())) {
					updateFav.setLike("false");
					updateFav.setUnlike("false");
					updateFav.setNews_id(id);
					updateFav.setToken(token);
					updateFav.setId(fav.getId());
					favService.updateUserlike(updateFav);
					likeService.MinUserFavour(id);
					JSONObject json = new JSONObject();
					json.put("status", "OK");

					return json;
				} else {
					updateFav.setLike("true");
					updateFav.setUnlike("false");
					updateFav.setNews_id(id);
					updateFav.setToken(token);
					updateFav.setId(fav.getId());
					if ("true".equals(fav.getUnlike())) {
						likeService.MinUserUnFavour(id);
					}

					favService.updateUserlike(updateFav);
				}
			} else {
				updateFav.setLike("true");
				updateFav.setUnlike("false");
				updateFav.setNews_id(id);
				updateFav.setToken(token);
				favService.insertUserlike(updateFav);
			}
			likeService.AddUserFavour(id);
		} else {
			updateFav.setLike("true");
			updateFav.setUnlike("false");
			updateFav.setNews_id(id);
			updateFav.setToken(token);
			favService.insertUserlike(updateFav);
			likeService.InsertUserFavour(id);
		}
		JSONObject json = new JSONObject();
		json.put("status", "OK");
		return json;
	}

	@Override
	public JSONObject unlike(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		JSONObject requestJson = JSON.parseObject(postData);
		String token = requestJson.getString("token");
		String id = requestJson.getString("id");
		Newslikedte likeRecord = likeService.getNewsLikeRecord(id);
		Favrecorddte userFav = favService.getFavourLikeRecord(id, token);
		Favrecorddte updateFav = new Favrecorddte();
		if (likeRecord != null) {
			if (userFav != null) {
				if ("true".equals(userFav.getUnlike())) {
					updateFav.setLike("false");
					updateFav.setUnlike("false");
					updateFav.setNews_id(id);
					updateFav.setToken(token);
					updateFav.setId(userFav.getId());
					favService.updateUserlike(updateFav);
					likeService.MinUserUnFavour(id);
					JSONObject json = new JSONObject();
					json.put("status", "OK");
					return json;
				} else {
					
					updateFav.setLike("false");
					updateFav.setUnlike("true");
					updateFav.setNews_id(id);
					updateFav.setToken(token);
					updateFav.setId(userFav.getId());
					if ("true".equals(userFav.getLike())) {
						likeService.MinUserFavour(id);
					}
					favService.updateUserlike(updateFav);
				}
			} else {
				
				updateFav.setLike("false");
				updateFav.setUnlike("true");
				updateFav.setNews_id(id);
				updateFav.setToken(token);
				favService.insertUserlike(updateFav);
			}
			likeService.AddUserUnFavour(id);
		} else {
			
			updateFav.setLike("false");
			updateFav.setUnlike("true");
			updateFav.setNews_id(id);
			updateFav.setToken(token);
			favService.insertUserlike(updateFav);
			likeService.InsertUserUnFavour(id);
		}
		JSONObject json = new JSONObject();
		json.put("status", "OK");
		return json;
	}

}
