package com.revanow.news.web.controller.v;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.revanow.news.domain.dte.BlackListdte;
import com.revanow.news.service.BlackListService;
import com.revanow.news.service.GoogleNewsService;
import com.revanow.news.service.IndiaNewsService;
import com.revanow.news.service.JCNewsService;
import com.revanow.news.service.LTNewsService;
import com.revanow.news.util.Api;
import com.revanow.news.util.Configuration;
import com.revanow.news.util.newsHelper;
import com.revanow.news.web.controller.BaseController;

@Controller
@RequestMapping("/v3/api")
public class ApiV3Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ApiV3Controller.class);

	private static final String BLACK_COUNTRY = Configuration.getInstance().getString("lw.black.country");

	private static final String ENGLISH = "english";

	private static final String SOURCE_NEWSDOG = "newsdog";

	private static final String SOURCE_GOOGLE = "google";

	private static final String SOURCE_JC = "JC";

	private static final String SOURCE_LT = "LT";

	@Autowired
	private IndiaNewsService indiaService;

	@Autowired
	private LTNewsService ltnewsService;

	@Autowired
	private GoogleNewsService googlenewsService;
	
	@Autowired
	private JCNewsService jcnewsService;

	@Autowired
	private BlackListService blackService;

	@RequestMapping("catagory")
	private void getCatagoryInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype,
			@RequestHeader(value = "source", required = false) String source) {

		logger.info((String) request.getAttribute("location"));
		logger.info((String) request.getAttribute("neolanguage"));
		logger.info((String) request.getAttribute("neoSource"));

		String lang = (String) request.getAttribute("neolanguage");
		String cause = source == null ? SOURCE_NEWSDOG : source;
		String location = (String) request.getAttribute("location");
		JSONArray responseJson = new JSONArray();
		switch (cause) {
		case SOURCE_NEWSDOG:
			lang = "hi".equals(lang) ? "india" : "english";
			responseJson = indiaService.category(did, ntype, lang);
			break;
		case SOURCE_JC:
			responseJson = jcnewsService.category(did, ntype, language, location);
			break;
		case SOURCE_LT:
			responseJson = ltnewsService.category(did, ntype, lang);
			break;
		case SOURCE_GOOGLE:
			responseJson = googlenewsService.category(did, ntype, lang, location);
			break;
		}
		try {
			Api.response(responseJson, response, "result_array");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("102")
	private void getNewsList(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype,
			@RequestHeader(value = "source", required = false) String source, @RequestHeader(value = "country", required = false,defaultValue="") String country)
			throws UnsupportedEncodingException {

		String lang = (String) request.getAttribute("neolanguage");
		String location = (String) request.getAttribute("location");
		String cause = source == null ? SOURCE_NEWSDOG : source;
		BlackListdte blackSun = null;
		if (country != null && !country.isEmpty()) {
			String blackCode = country.toLowerCase();
			blackSun = blackService.checkBlackList(blackCode);
		}
		String postData = getPostContentBody(request);
		JSONArray responseJson = new JSONArray();
		String ipStr = newsHelper.getRealIP(request);
		logger.info(ipStr);
		long ip = newsHelper.ipToLong(ipStr);
		logger.info(ip + "");
		switch (cause) {
		case SOURCE_NEWSDOG:
			lang = "hi".equals(lang) ? "india" : "english";
			responseJson = indiaService.list(did, ntype, lang, postData);
			break;
		case SOURCE_JC:
			responseJson = jcnewsService.list(did, ntype, language, postData, location);
			break;
		case SOURCE_LT:
			responseJson = ltnewsService.list(did, ntype, lang, postData);
			break;
		case SOURCE_GOOGLE:
			language = blackSun != null ? blackSun.getFinalval() : language;
			responseJson = googlenewsService.list(did, ntype, language, postData, location);
			break;
		}

		try {
			Api.response(responseJson, response, "result_array");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}
	
	@RequestMapping("articles")
	public void getFavour(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype,
			@RequestHeader(value = "source", required = false) String source, @RequestHeader(value = "touser", required = false) String touser)
			throws IOException {
		
		String lang = (String) request.getAttribute("neolanguage");
		String cause = source == null ? SOURCE_NEWSDOG : source;
		String toUsr = (touser == null ? "2c" : touser);
		String postData = getPostContentBody(request);
		JSONObject responseJson = new JSONObject();
		switch (cause) {
		case SOURCE_NEWSDOG:
			lang = "hi".equals(lang) ? "india" : "english";
			responseJson = indiaService.article(did, ntype, lang, postData,toUsr);
			break;
		case SOURCE_JC:
			break;
		case SOURCE_LT:
			responseJson = ltnewsService.article(did, ntype, lang, postData,toUsr);
			break;
		}
		Api.response(responseJson, response, "result_object");

	}
}
