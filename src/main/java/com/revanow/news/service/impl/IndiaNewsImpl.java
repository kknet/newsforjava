package com.revanow.news.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;

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
import com.revanow.news.bean.MediaBean;
import com.revanow.news.bean.NewsdogsBean;
import com.revanow.news.bean.NewslikeBean;
import com.revanow.news.bean.UsersubBean;
import com.revanow.news.domain.dte.Favrecorddte;
import com.revanow.news.domain.dte.Mediadte;
import com.revanow.news.domain.dte.Newsdogsdte;
import com.revanow.news.domain.dte.Newslikedte;
import com.revanow.news.domain.dte.Usersubdte;
import com.revanow.news.service.FavrecordService;
import com.revanow.news.service.IndiaNewsService;
import com.revanow.news.service.LikeService;
import com.revanow.news.service.MediaService;
import com.revanow.news.service.NewsService;
import com.revanow.news.service.SubService;
import com.revanow.news.util.Api;
import com.revanow.news.util.Configuration;
import com.revanow.news.util.Curl;
import com.revanow.news.util.newsHelper;

@Service("indiaService")
public class IndiaNewsImpl implements IndiaNewsService {

	private String requestUrl = "http://api.newsdog.today/";

	private static final String blackUrl = Configuration.getInstance().getString("black.list");

	private static final String detailUrl = Configuration.getInstance().getString("detail.url");

	private static final String baseUrl = Configuration.getInstance().getString("base.url");

	private static final String defaultImg = Configuration.getInstance().getString("default.img");

	private static final String engToken = Configuration.getInstance().getString("eng.token");

	private static final String indiaToken = Configuration.getInstance().getString("ind.token");

	private static final Logger logger = LoggerFactory.getLogger(IndiaNewsImpl.class);

	@Autowired
	private NewsService newsService;

	@Autowired
	private LikeService likeService;

	@Autowired
	private FavrecordService favService;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private SubService subService;

	private Timestamp ts;

	protected String choiceApiUrl(String language) {
		switch (language) {
		case "english":
			this.requestUrl = "http://api.newsdog.today/";
			break;
		case "india":
			this.requestUrl = "http://api.hindi.newsdog.today/";
			break;
		default:
			this.requestUrl = "http://api.newsdog.today/";
			break;
		}
		return this.requestUrl;
	}

	@Override
	public JSONArray category(String did, String ntype, String language) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);
		ApiUrl = ApiUrl + "v4/categories/sorted/?" + newsHelper.buildHttpRequest(did, ntype, language);

		String result = HttpRequest.get(ApiUrl).body();
		logger.info(result);
		JSONArray resultJson = JSON.parseArray(result);
		JSONArray responseJson = new JSONArray();
		for (int i = 0; i < resultJson.size(); i++) {
			JSONObject index = resultJson.getJSONObject(i);
			JSONObject index2 = new JSONObject();
			index2.put("id", i);
			index2.put("catagory", index.getString("name"));
			index2.put("title", newsHelper.upperCase(index.getString("title")));
			responseJson.add(index2);
		}
		return responseJson;
	}

	@Override
	public JSONArray search(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);
		JSONObject requestJson = JSON.parseObject(postData);
		String token = requestJson.getString("token");
		String keyword = requestJson.getString("keyword");
		try {
			keyword = URLEncoder.encode(keyword, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			logger.info("UnsupportedEncodingException", e);
		}
		int from = requestJson.containsKey("from") ? requestJson.getIntValue("from") : 0;
		int size = requestJson.containsKey("size") ? requestJson.getIntValue("size") : 10;
		ApiUrl = ApiUrl + "v1/thesaurus/words/?words=" + keyword + "&from=" + from + "&size=" + size + "&" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
		String result;
		JSONArray responseJson = new JSONArray();
		try {
			String[] blackList = blackUrl.split(",");
			result = Curl.curl_get(ApiUrl, "GET", token);
			logger.info(result);
			JSONObject obj = JSON.parseObject(result);
			JSONArray resultJson = obj.getJSONArray("articles");

			for (int i = 0; i < resultJson.size(); i++) {
				JSONObject r = resultJson.getJSONObject(i);
				if (r.containsKey("type")) {
					String type = r.getString("type");
					if ("article".equals(type)) {
						if (Arrays.asList(blackList).contains(type)) {
							r.remove(i);
						}
					}
				}
				JSONArray titleArr = r.getJSONArray("title");
				String title = titleArr.get(0).toString();
				title = title.replaceAll("<em>", "<font color=\"red\">");
				title = title.replaceAll("</em>", "</font>");
				r.put("title", title);
				String detail_url = detailUrl + "?id=" + r.getString("id") + "&language=" + language + "&type=" + r.getString("type");
				r.put("detail_url", detail_url);
				responseJson.add(r);
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong", e1);
		}
		return responseJson;
	}

	@Override
	public JSONArray medias(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);
		JSONObject requestJson = JSON.parseObject(postData);

		String token = requestJson.getString("token");
		String read_tag = requestJson.getString("read_tag");
		ApiUrl = ApiUrl + "v1/medias/?" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
		if (!read_tag.isEmpty()) {
			ApiUrl += "&read_tag=" + read_tag;
		}
		JSONArray resultJson = new JSONArray();
		try {
			String result = Curl.curl_get(ApiUrl, "GET", token);
			logger.info(result);
			resultJson = JSON.parseArray(result);
			String[] blackList = blackUrl.split(",");
			for (int i = 0; i < resultJson.size(); i++) {
				JSONObject r = resultJson.getJSONObject(i);
				if (r.containsKey("type")) {
					String type = r.getString("type");
					if ("article".equals(type)) {
						if (Arrays.asList(blackList).contains(type)) {
							r.remove(i);
						}
					}
				}
				if ("{}".equals(r.get("avatar").toString()) || r.getString("avatar").isEmpty()) {
					JSONObject avatarObj = new JSONObject();
					avatarObj.put("origin", defaultImg);
					avatarObj.put("width", 140);
					avatarObj.put("height", 140);
					r.put("avatar", avatarObj);
				} else {
					JSONObject avatar = r.getJSONObject("avatar");
					if (avatar.getString("origin") == null) {
						JSONObject avatarObj = new JSONObject();
						avatarObj.put("origin", defaultImg);
						avatarObj.put("width", 140);
						avatarObj.put("height", 140);
						r.put("avatar", avatarObj);
					}
				}

				Mediadte media = new Mediadte();
				media.setSource_id(r.getString("id"));
				media.setTitle(r.getString("title"));
				media.setSite_url(r.getString("site_url"));
				media.setLanguage(language);
				Mediadte issetMedia = mediaService.getMediaBySourceId(r.getString("id"));
				if (issetMedia == null) {
					mediaService.insertMedia(media);
				} else {
					media.setId(issetMedia.getId());
					mediaService.updateMedia(media);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		return resultJson;
	}

	@Override
	public JSONArray subscribe(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);

		JSONObject requestJson = JSON.parseObject(postData);

		String token = requestJson.getString("token");

		ApiUrl = ApiUrl + "v1/medias/subscribed/?" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
		JSONArray resultJson = new JSONArray();
		try {
			String result = Curl.curl_get(ApiUrl, "GET", token);
			resultJson = JSON.parseArray(result);
			logger.info(result);
			String[] blackList = blackUrl.split(",");
			for (int i = 0; i < resultJson.size(); i++) {
				JSONObject r = resultJson.getJSONObject(i);
				if (r.containsKey("type")) {
					String type = r.getString("type");
					if ("article".equals(type)) {
						if (Arrays.asList(blackList).contains(type)) {
							r.remove(i);
						}
					}
				}

				if ("{}".equals(r.get("avatar").toString()) || r.getString("avatar").isEmpty()) {
					JSONObject avatarObj = new JSONObject();
					avatarObj.put("origin", defaultImg);
					avatarObj.put("width", 140);
					avatarObj.put("height", 140);
					r.put("avatar", avatarObj);
				} else {
					JSONObject avatar = r.getJSONObject("avatar");
					if (avatar.getString("origin") == null) {
						JSONObject avatarObj = new JSONObject();
						avatarObj.put("origin", defaultImg);
						avatarObj.put("width", 140);
						avatarObj.put("height", 140);
						r.put("avatar", avatarObj);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		return resultJson;
	}

	@Override
	public JSONObject putsubscribe(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);

		JSONObject requestJson = JSON.parseObject(postData);

		String token = requestJson.getString("token");
		String site_url = requestJson.getString("site_url");
		ApiUrl = ApiUrl + "v1/medias/subscribing/?" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
		JSONObject postJson = new JSONObject();
		postJson.put("site_url", site_url);
		Usersubdte subscribe = new Usersubdte();
		subscribe.setSite_url(site_url);
		if (did == null) {
			subscribe.setDid(token);
		} else {
			subscribe.setDid(did);
		}
		String result = "";
		try {
			result = Curl.curl_post(postJson, ApiUrl, "POST", token);
			subService.recordUserSubscribe(subscribe);
			logger.info(result);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		return JSON.parseObject(result);
	}

	@Override
	public JSONObject delsubscribe(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);

		JSONObject requestJson = JSON.parseObject(postData);

		String token = requestJson.getString("token");
		String site_url = requestJson.getString("site_url");
		ApiUrl = ApiUrl + "v1/medias/subscribing/?site_url=" + site_url + "&" + newsHelper.buildHttpRequest(did, ntype, language);
		;
		logger.info(ApiUrl + " param:" + postData);
		String result = "";
		try {
			result = Curl.curl_get(ApiUrl, "DELETE", token);
			logger.info(result);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		return JSON.parseObject(result);
	}

	@Override
	public JSONArray city(String did, String ntype, String language) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);

		ApiUrl = ApiUrl + "v1/categories/cities/?" + newsHelper.buildHttpRequest(did, ntype, language);
		String final_token;
		switch (language) {
		case "english":
			final_token = engToken;
			break;
		case "india":
			final_token = indiaToken;
			break;
		default:
			final_token = engToken;
			break;
		}
		String result = "";
		try {
			result = Curl.curl_get(ApiUrl, "GET", final_token);
			logger.info(result);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		return JSON.parseArray(result);
	}

	@Override
	public JSONObject feedback(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);

		JSONObject requestJson = JSON.parseObject(postData);
		String token = requestJson.getString("token");
		String id = requestJson.getString("id");
		String reason = requestJson.containsKey("reason") ? requestJson.getString("reason") : "";
		JSONObject postJSON = new JSONObject();
		postJSON.put("reason", reason);

		ApiUrl = ApiUrl + "v1/articles/" + id + "/unlike/?" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
		String responseJson = "";
		try {
			responseJson = Curl.curl_post(postJSON, ApiUrl, "POST", token);
			logger.info(responseJson);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		return JSON.parseObject(responseJson);
	}

	@Override
	public JSONArray medialist(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);

		JSONObject requestJson = JSON.parseObject(postData);
		String token = requestJson.getString("token");
		String id = requestJson.getString("id");

		ApiUrl = ApiUrl + "v1/medias/" + id + "/articles/?" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
		JSONArray resultArr = new JSONArray();
		try {
			String responseJson = Curl.curl_get(ApiUrl, "GET", token);
			logger.info(responseJson);
			JSONObject resultJson = JSON.parseObject(responseJson);
			resultArr = JSON.parseArray(resultJson.getString("articles"));
			for (int i = 0; i < resultArr.size(); i++) {
				JSONObject index = resultArr.getJSONObject(i);
				String detail_url = detailUrl + "?id=" + index.getString("id") + "&language=" + language + "&type=" + index.getString("type");
				index.put("detail_url", detail_url);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		return resultArr;
	}

	@Override
	public JSONObject like(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);
		JSONObject requestJson = JSON.parseObject(postData);
		String token = requestJson.getString("token");
		String id = requestJson.getString("id");
		ApiUrl = ApiUrl + "v1/articles/" + id + "/likes/?" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
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
		String responseJson = "";
		try {
			responseJson = Curl.curl_get(ApiUrl, "POST", token);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		logger.info(responseJson);
		return JSON.parseObject(responseJson);
		// Api.response(JSON.parseObject(responseJson), response,
		// "result_object");
	}

	@Override
	public JSONObject unlike(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);

		JSONObject requestJson = JSON.parseObject(postData);
		String token = requestJson.getString("token");
		String id = requestJson.getString("id");
		ApiUrl = ApiUrl + "v1/articles/" + id + "/likes/?" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
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
		String responseJson = "";
		try {
			responseJson = Curl.curl_get(ApiUrl, "DELETE", token);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		logger.info(responseJson);
		return JSON.parseObject(responseJson);

	}

	@Override
	public JSONObject log(String language, String getData) {
		// TODO Auto-generated method stub
		JSONObject requestJson = JSON.parseObject(getData);
		HashMap<String, String> postData = new HashMap<String, String>();
		JSONArray mAllDataBean = requestJson.getJSONArray("mAllDataBean");
		String result = "";
		for (int i = 0; i < mAllDataBean.size(); i++) {
			JSONObject index = mAllDataBean.getJSONObject(i);
			postData.put("ip", index.getString("ip"));
			postData.put("size", index.getString("size"));
			postData.put("resolution", index.getString("resolution"));
			postData.put("model", index.getString("model"));
			postData.put("lang", "india".equals(language) ? "hi" : "en");
			postData.put("os", index.getString("os"));
			postData.put("os_v", index.getString("os_v"));
			postData.put("app_v", index.getString("app_v"));
			postData.put("channel", "lewa");
			postData.put("net", index.getString("net"));
			postData.put("u_type", "lewa");
			postData.put("did", index.getString("did"));
			postData.put("imei", index.getString("imei"));
			postData.put("mac", index.getString("mac"));
			postData.put("android_id", index.getString("android_id"));
			postData.put("ts", String.valueOf(System.currentTimeMillis()));
			postData.put("lv", "1");
			String requestParam = newsHelper.http_build_query(postData);
			String logUrl = "http://log.newsdog.today/v1/lewa/logs/?" + requestParam;
			if (index.containsKey("detailUtils")) {
				JSONObject postBody = new JSONObject();
				String click = index.getString("detailUtils");
				postBody.put("data", click);
				try {
					result = Curl.curl_post(postBody, logUrl, "POST");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.info("Curl is wrong:", e);
				}
			}
			if (index.containsKey("appUtil")) {
				JSONObject postBody2 = new JSONObject();
				String duration = index.getString("appUtil");
				postBody2.put("data", duration);
				try {
					result = Curl.curl_post(postBody2, logUrl, "POST");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					logger.info("Curl is wrong:", e);
				}
			}
		}
		return JSON.parseObject(result);
	}

	@Override
	public JSONObject collection(String language, String postData) {
		// TODO Auto-generated method stub
		JSONObject requestJson = JSON.parseObject(postData);
		// String type = requestJson.getString("type");
		String id = requestJson.getString("id");
		Newsdogsdte news = newsService.getNewsById(id, language);
		JSONObject reponseJson = new JSONObject();
		JSONArray topImage = new JSONArray();
		if (news.getTop_image() != null) {
			String[] top_image = news.getTop_image().split(",");
			if (top_image.length > 0) {
				for (String top : top_image) {
					JSONObject topindex = new JSONObject();
					topindex.put("origin", top);
					topindex.put("source", top);
					topindex.put("thumb", top);
					topindex.put("thumb_height", news.getTop_image_height());
					topindex.put("thumb_width", news.getTop_image_width());
					topindex.put("width", news.getTop_image_width());
					topindex.put("height", news.getTop_image_height());
					topImage.add(topindex);
				}
			}
		}
		reponseJson.put("top_images", topImage);

		if ("youtube_video".equals(news.getType())) {
			String url = detailUrl + "?id=" + news.getNews_id() + "&language=" + language + "&type=youtube_video";
			reponseJson.put("category", null);
			reponseJson.put("seq_id", 0);
			reponseJson.put("liked", false);
			reponseJson.put("favoured", false);
			reponseJson.put("share_url", url);
			reponseJson.put("shared_count", 0);
			reponseJson.put("content", news.getContent());
			reponseJson.put("source", "test");
			reponseJson.put("type", "youtube_video");
			reponseJson.put("id", news.getNews_id());
			reponseJson.put("detail_url", url);
			reponseJson.put("like_count", 0);
			reponseJson.put("comments_count", 0);
			JSONArray youtube = new JSONArray();
			youtube.add(news.getSource());
			reponseJson.put("youtube", youtube);
		} else {
			String url = detailUrl + "?id=" + news.getNews_id() + "&language=" + language + "&type=article";
			JSONArray relImage = new JSONArray();
			if (news.getRelated_images() != null) {
				String[] related_image = news.getRelated_images().split(",");
				if (related_image.length > 0) {
					for (String related : related_image) {
						JSONObject relatedindex = new JSONObject();
						relatedindex.put("origin", related);
						relatedindex.put("source", related);
						relatedindex.put("thumb", related);
						relatedindex.put("thumb_height", news.getRelated_image_height());
						relatedindex.put("thumb_width", news.getRelated_image_width());
						relatedindex.put("width", news.getRelated_image_width());
						relatedindex.put("height", news.getRelated_image_height());
						relImage.add(relatedindex);
					}
				}
			}
			String site_url = news.getSource_url().replace("http://", "");
			String[] site_url2 = site_url.split("/");
			Timestamp ts = news.getPublished_at();
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			reponseJson.put("related_images", relImage);
			reponseJson.put("title", news.getTitle());
			reponseJson.put("seq_id", 0);
			reponseJson.put("is_hot", 0);
			reponseJson.put("source", news.getSource());
			reponseJson.put("published_at", news.getPublished_at());
			reponseJson.put("site_url", site_url2[0]);
			reponseJson.put("type", "article");
			reponseJson.put("id", sdf.format(ts));
			reponseJson.put("detail_url", url);
			reponseJson.put("op_recommend", false);
			reponseJson.put("comments_count", 0);
		}

		return reponseJson;
	}

	@Override
	public JSONObject register(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);

		JSONObject requestJson = JSON.parseObject(postData);

		String source_id = requestJson.getString("source_id");
		String phone_type = requestJson.getString("phone_type");
		String resolution = requestJson.getString("resolution");
		JSONObject post2Json = new JSONObject();
		post2Json.put("source_uid", source_id);
		post2Json.put("phone_type", phone_type);
		post2Json.put("resolution", resolution);
		post2Json.put("source", "android:lewa");
		post2Json.put("from", "android:lewa");
		post2Json.put("avatar", "");
		post2Json.put("name", "");
		post2Json.put("oauth_token", "");
		post2Json.put("gender", "");
		post2Json.put("age", "");
		JSONObject postJson = new JSONObject();
		postJson.put("user_info", post2Json);
		ApiUrl = ApiUrl + "v1/users/?" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
		try {
			String result = Curl.curl_post(postJson, ApiUrl, "POST");
			JSONObject user = JSON.parseObject(result);
			user.put("avatar", new JSONArray());

			return user;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		return null;
	}

	@Override
	public JSONArray list(String did, String ntype, String language, String postData) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);
		JSONObject requestJson = JSON.parseObject(postData);
		String token = requestJson.getString("token");
		String categories = requestJson.getString("categories");
		try {
			categories = URLEncoder.encode(categories, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			logger.info("UnsupportedEncodingException:", e1);
		}
		String action = requestJson.getString("action");
		String read_tag = requestJson.getString("read_tag");

		ApiUrl = ApiUrl + "v4/categories/" + categories + "/articles/" + action + "/?" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
		if (!read_tag.isEmpty() && "prev".equals(action)) {
			ApiUrl += "&read_tag=" + read_tag;
		}

		try {
			if ("subscribed".equals(categories)) {
				String ApiUrl2 = this.choiceApiUrl(language) + "v1/medias/subscribed/?" + newsHelper.buildHttpRequest(did, ntype, language);
				String subscribed = Curl.curl_get(ApiUrl2, "GET", token);
				return JSON.parseArray(subscribed);
			}

			String result = Curl.curl_get(ApiUrl, "GET", token);
			logger.info(result);
			JSONObject resultJson = JSON.parseObject(result);
			JSONArray articles = resultJson.getJSONArray("articles");

			if (resultJson.containsKey("galary")) {
				JSONObject video = resultJson.getJSONObject("galary");
				int loc = video.getIntValue("loc");
				video.remove("loc");
				if (video.containsKey("data")) {
					String data = video.getString("data");
					if (data != null && !"{}".equals(data)) {
						articles.add(loc, video.getJSONObject("data"));
					}
				}
			}
			if (resultJson.containsKey("video")) {
				JSONObject video = resultJson.getJSONObject("video");
				int loc = video.getIntValue("loc");
				video.remove("loc");
				if (video.containsKey("data")) {
					String data = video.getString("data");
					if (data != null && !"{}".equals(data)) {
						articles.add(loc, video.getJSONObject("data"));
					}
				}
			}
			String[] blackList = blackUrl.split(",");
			for (int i = 0; i < articles.size(); i++) {
				JSONObject jsonIndex = articles.getJSONObject(i);
				if (jsonIndex != null) {
					if (jsonIndex.containsKey("type")) {
						String type = jsonIndex.getString("type");
						if ("article".equals(type)) {
							jsonIndex.put("is_hot", jsonIndex.getBoolean("is_hot") ? 1 : 0);
							if (Arrays.asList(blackList).contains(type)) {
								articles.remove(i);
							}
						}
					}
					String detail_url = detailUrl + "?id=" + jsonIndex.getString("id") + "&language=" + language + "&type=" + jsonIndex.getString("type");
					jsonIndex.put("detail_url", detail_url);
					jsonIndex.put("isjump", 0);
					jsonIndex.remove("bigger");
				}
			}
			return articles;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		return null;
	}

	@Override
	public JSONObject article(String did, String ntype, String language, String postData,String toUser) {
		// TODO Auto-generated method stub
		String ApiUrl = this.choiceApiUrl(language);
		JSONObject requestJson = JSON.parseObject(postData);
		String token = requestJson.getString("token");
		String id = requestJson.getString("id");
		String type = requestJson.getString("type");
		String ApiUrl1 = ApiUrl + "v2/articles/" + type + "/" + id + "/?" + newsHelper.buildHttpRequest(did, ntype, language);
		logger.info(ApiUrl + " param:" + postData);
		ts = new Timestamp(System.currentTimeMillis());
		String responseStr = "";
		try {
			responseStr = Curl.curl_get(ApiUrl1, "GET", token);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		logger.info(responseStr);
		JSONObject responseJson = JSON.parseObject(responseStr);
		Newsdogsdte newsDog = new Newsdogsdte();
		if ("article".equals(type)) {
			newsDog.setNews_id(responseJson.getString("id"));
			newsDog.setTitle(responseJson.getString("title"));
			// newsDog.setContent();
			if (responseJson.containsKey("top_images")) {
				JSONArray topImageArr = responseJson.getJSONArray("top_images");
				if (topImageArr.size() > 0) {
					String topImage = "";
					for (int i = 0; i < topImageArr.size(); i++) {
						JSONObject topJson = topImageArr.getJSONObject(i);
						topImage += topJson.getString("origin") + ",";
						if (i == 0) {
							newsDog.setTop_image_width(topJson.getIntValue("width"));
							newsDog.setTop_image_height(topJson.getIntValue("height"));
						}
					}
					topImage = topImage.substring(0, topImage.length() - 1);
					newsDog.setTop_image(topImage);

				}
			}
			String relatedImage = "";
			JSONArray relImageArr = responseJson.getJSONArray("related_images");
			if (relImageArr.size() > 0) {
				for (int i = 0; i < relImageArr.size(); i++) {
					JSONObject relJson = relImageArr.getJSONObject(i);
					relatedImage += relJson.getString("origin") + ",";
					if (i == 0) {
						newsDog.setRelated_image_width(relJson.getIntValue("width"));
						newsDog.setRelated_image_height(relJson.getIntValue("height"));
					}
				}
				relatedImage = relatedImage.substring(0, relatedImage.length() - 1);
			}
			String content = responseJson.getString("content");
			String regex = "<a target=\"_blank\".*src=\"http:\\/\\/static.newsdog.today\\/app_download_banner.png\".*<\\/a>";
			content = content.replaceAll(regex, "");
			Document doc = Jsoup.parse(content);
			Elements aTags = doc.getElementsByClass("image");
			if (relImageArr.size() > 0) {
				for (int i = 0; i < relImageArr.size(); i++) {
					JSONObject relJson = (JSONObject) relImageArr.get(i);
					String imageHtml = "<img src='" + relJson.getString("origin") + "'>";
					aTags.get(i).html(imageHtml);
				}
			}
			newsDog.setContent(doc.html());
			responseJson.put("content", doc.html());
			newsDog.setRelated_images(relatedImage);
			newsDog.setSource(responseJson.getString("source"));
			Timestamp published_at = ts.valueOf(responseJson.getString("published_at"));
			published_at.setTime(published_at.getTime() + 5 * 60 * 60 * 1000 + 30 * 60 * 1000);
			newsDog.setPublished_at(published_at);
			newsDog.setSource_url(responseJson.getString("source_url"));
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			responseJson.put("published_at", sdf.format(published_at));
			Boolean partner_switch = responseJson.getBoolean("partner_switch");
			if (partner_switch == null || partner_switch == false) {
				responseJson.put("partner_switch", false);
			}else{
				responseJson.put("partner_switch", true);
			}
		} else if ("youtube_video".equals(type)) {
			newsDog.setNews_id(responseJson.getString("id"));
			newsDog.setContent(responseJson.getString("content"));
			JSONArray topImageArr = responseJson.getJSONArray("top_images");
			String topImage = "";
			for (int i = 0; i < topImageArr.size(); i++) {
				JSONObject topJson = topImageArr.getJSONObject(i);
				topImage += topJson.getString("origin") + ",";
			}
			topImage = topImage.substring(0, topImage.length() - 1);
			newsDog.setTop_image(topImage);
			JSONArray sourceArr = responseJson.getJSONArray("youtube");
			newsDog.setSource(sourceArr.getString(0));
			newsDog.setSource_url(type);
			
		}
		newsDog.setType(type);
		newsDog.setT_language(language.equals("india") ? "hi" : "en");
		newsDog.setT_location("in");
		String search_lang = language.equals("india") ? "hi" : "en";
		
		Newsdogsdte isset_new = newsService.getNewsById(responseJson.getString("id"), search_lang);

		if (isset_new == null) {
			newsService.insertnews(newsDog);
		} else {
			newsDog.setId(isset_new.getId());

			newsService.updatenews(newsDog);
		}
		responseJson.remove("has_commented");
		responseJson.remove("media");
		responseJson.remove("op_recommend");
		responseJson.remove("related_articles");
		responseJson.remove("seq_id");
		responseJson.remove("share_url");
		responseJson.remove("key_words");
		responseJson.remove("shared_count");
		responseJson.remove("comments_count");
		responseJson.remove("liked");
		responseJson.remove("is_hot");
		responseJson.remove("favored");
		responseJson.remove("bigger");
		responseJson.remove("top_images");
		Newslikedte nlb = likeService.getNewsLikeRecord(responseJson.getString("id"));
		if (nlb != null) {
			responseJson.put("like_count", nlb.getLike_count());
			responseJson.put("unlike_count", nlb.getUnlike_count());
		} else {
			responseJson.put("like_count", 0);
			responseJson.put("unlike_count", 0);
		}

		Favrecorddte userlike = favService.getFavourLikeRecord(responseJson.getString("id"), token);
		if (userlike == null) {
			responseJson.put("liked", 0);
		} else if ("true".equals(userlike.getLike())) {
			responseJson.put("liked", 1);
		} else if ("true".equals(userlike.getUnlike())) {
			responseJson.put("liked", 2);
		}
		responseJson.put("share_url", detailUrl + "?id=" + id + "&language=" + language + "&type=" + type + "&action=share&touser=" + toUser);
		responseJson.put("detail_url", detailUrl + "?id=" + id + "&language=" + language + "&type=" + type);
		String ApiUrl2 = ApiUrl + "v2/articles/" + type + "/" + id + "/similar_articles/";
		String responseStr2 = null;
		try {
			responseStr2 = Curl.curl_get(ApiUrl2, "GET", token);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("Curl is wrong:", e);
		}
		JSONObject responseJson2 = JSON.parseObject(responseStr2);

		JSONArray favourNews = responseJson2.getJSONArray("articles");
		String[] blackList = blackUrl.split(",");
		if ("article".equals(type)) {
			for (int i = 0; i < favourNews.size(); i++) {
				JSONObject fnews = favourNews.getJSONObject(i);
				if (fnews.containsKey("type")) {
					String fType = fnews.getString("type");
					if ("article".equals(fType)) {
						fnews.put("is_hot", fnews.getBoolean("is_hot") ? 1 : 0);
					}
					if (Arrays.asList(blackList).contains(type)) {
						favourNews.remove(i);
					}
				}
				fnews.put("detail_url", detailUrl + "?id=" + fnews.getString("id") + "&language=" + language + "&type=" + fnews.getString("type"));
			}
		} else if ("youtube_video".equals(type)) {
			for (int i = 0; i < favourNews.size(); i++) {
				JSONObject fnews = favourNews.getJSONObject(i);
				fnews.put("detail_url", detailUrl + "?id=" + fnews.getString("id") + "&language=" + language + "&type=" + fnews.getString("type"));
			}
			responseJson.put("subscribed", false);
			responseJson.put("related_images", new JSONArray());
			responseJson.put("source", "");
			responseJson.put("title", "");
			responseJson.put("published_at", "");
			responseJson.put("site_url", "");
			responseJson.put("source_url", "http://www.youtube.com/embed/" + responseJson.getJSONArray("youtube").get(0));

		}
		responseJson.put("favour", favourNews);
		return responseJson;
	}
}
