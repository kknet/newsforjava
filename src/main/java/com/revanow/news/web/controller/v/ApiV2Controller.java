package com.revanow.news.web.controller.v;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.revanow.news.domain.dte.Feedbackdte;
import com.revanow.news.domain.dte.Mapipdte;
import com.revanow.news.service.FeedService;
import com.revanow.news.service.IndiaNewsService;
import com.revanow.news.service.LTNewsService;
import com.revanow.news.service.MapipService;
import com.revanow.news.util.Api;
import com.revanow.news.util.newsHelper;
import com.revanow.news.web.controller.BaseController;

@Controller
@RequestMapping("/v2/api")
public class ApiV2Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ApiV2Controller.class);

	private static final String ENGLISH = "english";

	private static final String NEWSDOG = "newsdog";

	private static final String JC = "JC";

	private static final String LT = "LT";

	@Autowired
	private FeedService feedService;

	@Autowired
	private MapipService mapService;

	@Autowired
	private IndiaNewsService indiaService;

	@Autowired
	private LTNewsService ltnewsService;

	private Timestamp ts;

	@RequestMapping("city")
	public void getCity(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) {
		String lang = (language == null ? ENGLISH : language);
		String ipStr = request.getRemoteAddr();
		logger.info(ipStr);
		long ip = newsHelper.ipToLong(ipStr);
		logger.info(ip + ">>>>");
		Mapipdte ipBean = mapService.getLocationForIp(ip);
		String city = "null";
		if (ipBean != null) {
			city = ipBean.getP_city();
		}
		logger.info(city);
		JSONArray responseJson = indiaService.city(did, ntype, lang);
		JSONArray neoCity = new JSONArray();
		neoCity.add(city);
		for (int i = 0; i < responseJson.size(); i++) {
			neoCity.add(responseJson.get(i));
		}
		logger.info(neoCity.toString());
		try {
			Api.response(neoCity, response, "result_array");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("feedback")
	public void feedback(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) throws IOException {
		String postData = getPostContentBody(request);
		JSONObject requestJson = JSON.parseObject(postData);
		String email = requestJson.getString("email");
		String type = requestJson.getString("type");
		String question = requestJson.getString("question");
		Feedbackdte feed = new Feedbackdte();
		feed.setEmail(email);
		feed.setQuestion(question);
		feed.setType(type);
		feedService.insertFeedBack(feed);
		Api.response(new JSONObject(), response, "result_object");
	}

	@RequestMapping("articles")
	public void getFavour(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype,
			@RequestHeader(value = "source", required = false) String source, @RequestHeader(value = "touser", required = false) String touser)
			throws IOException {
		String lang = (language == null ? ENGLISH : language);
		String cause = (source == null ? NEWSDOG : source);
		String toUsr = (touser == null ? "2c" : touser);
		String postData = getPostContentBody(request);
		JSONObject responseJson = new JSONObject();
		switch (cause) {
		case NEWSDOG:
			responseJson = indiaService.article(did, ntype, lang, postData, toUsr);
			break;
		case JC:
			break;
		case LT:
			responseJson = ltnewsService.article(did, ntype, lang, postData, toUsr);
			break;
		}
		Api.response(responseJson, response, "result_object");
	}
}
