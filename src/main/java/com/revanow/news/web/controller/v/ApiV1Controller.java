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
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.revanow.news.service.IndiaNewsService;
import com.revanow.news.service.LTNewsService;
import com.revanow.news.util.Api;
import com.revanow.news.web.controller.BaseController;

@Controller
@RequestMapping("/v1/api")
public class ApiV1Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ApiV1Controller.class);

	private static final String ENGLISH = "english";

	private static final String NEWSDOG = "newsdog";

	private static final String JC = "JC";

	private static final String LT = "LT";

	@Autowired
	private IndiaNewsService indiaService;

	@Autowired
	private LTNewsService ltnewsService;

	@RequestMapping("register")
	private void userRegister(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) {

		String lang = (language == null ? ENGLISH : language);
		String postData = getPostContentBody(request);
		JSONObject responseJson = indiaService.register(did, ntype, lang, postData);
		try {
			Api.response(responseJson, response, "result_object");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("catagory")
	private void getCatagoryInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype,
			@RequestHeader(value = "source", required = false) String source) {
		String lang = (language == null ? ENGLISH : language);
		String cause = (source == null ? NEWSDOG : source);
		JSONArray responseJson = new JSONArray();
		switch (cause) {
		case NEWSDOG:
			responseJson = indiaService.category(did, ntype, lang);
			break;
		case JC:
			break;
		case LT:
			responseJson = ltnewsService.category(did, ntype, lang);
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
			@RequestHeader(value = "source", required = false) String source) throws UnsupportedEncodingException {
		String lang = (language == null ? ENGLISH : language);
		String cause = (source == null ? NEWSDOG : source);
		String postData = getPostContentBody(request);
		JSONArray responseJson = new JSONArray();
		switch (cause) {
		case NEWSDOG:
			responseJson = indiaService.list(did, ntype, lang, postData);
			break;
		case JC:
			break;
		case LT:
			responseJson = ltnewsService.list(did, ntype, lang, postData);
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
		String lang = (language == null ? ENGLISH : language);
		String cause = (source == null ? NEWSDOG : source);
		String toUsr = (touser == null ? "2c" : touser);
		String postData = getPostContentBody(request);
		JSONObject responseJson = new JSONObject();
		switch (cause) {
		case NEWSDOG:
			responseJson = indiaService.article(did, ntype, lang, postData,toUsr);
			break;
		case JC:
			break;
		case LT:
			responseJson = ltnewsService.article(did, ntype, lang, postData,toUsr);
			break;
		}
		Api.response(responseJson, response, "result_object");

	}

	@RequestMapping("search")
	private void search(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype,
			@RequestHeader(value = "source", required = false) String source) throws UnsupportedEncodingException, JSONException {

		String lang = (language == null ? ENGLISH : language);
		String cause = (source == null ? NEWSDOG : source);
		String postData = getPostContentBody(request);
		JSONArray responseJson = new JSONArray();
		switch (cause) {
		case NEWSDOG:
			responseJson = indiaService.search(did, ntype, lang, postData);
			break;
		case JC:
			break;
		case LT:
			responseJson = ltnewsService.search(did, ntype, lang, postData);
			break;
		}
		try {
			Api.response(responseJson, response, "result_array");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("medias")
	private void getMedias(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) {

		String lang = (language == null ? ENGLISH : language);
		String postData = getPostContentBody(request);
		JSONArray responseJson = indiaService.medias(did, ntype, lang, postData);
		try {
			Api.response(responseJson, response, "result_array");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("subscribe")
	private void getSubList(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) {

		String lang = (language == null ? ENGLISH : language);
		String postData = getPostContentBody(request);
		JSONArray responseJson = indiaService.subscribe(did, ntype, lang, postData);
		try {
			Api.response(responseJson, response, "result_array");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("putsubscribe")
	private void editSub(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) {

		String lang = (language == null ? ENGLISH : language);
		String postData = getPostContentBody(request);
		JSONObject responseJson = indiaService.putsubscribe(did, ntype, lang, postData);
		try {
			Api.response(responseJson, response, "result_object");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("delsubscribe")
	private void delSub(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) {

		String lang = (language == null ? ENGLISH : language);
		String postData = getPostContentBody(request);
		JSONObject responseJson = indiaService.delsubscribe(did, ntype, lang, postData);
		try {
			Api.response(responseJson, response, "result_object");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("city")
	public void getCity(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) {
		String lang = (language == null ? ENGLISH : language);
		JSONArray responseJson = indiaService.city(did, ntype, lang);
		try {
			Api.response(responseJson, response, "result_array");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("feedback")
	public void SubFeed(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) {
		String lang = (language == null ? ENGLISH : language);
		String postData = getPostContentBody(request);
		JSONObject responseJson = indiaService.feedback(did, ntype, lang, postData);
		try {
			Api.response(responseJson, response, "result_object");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("medialist")
	public void getMediasList(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) {
		String lang = (language == null ? ENGLISH : language);
		String postData = getPostContentBody(request);
		JSONArray responseJson = indiaService.medialist(did, ntype, lang, postData);
		try {
			Api.response(responseJson, response, "result_array");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("like")
	public void likeNews(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype,
			@RequestHeader(value = "source", required = false) String source) {
		String lang = (language == null ? ENGLISH : language);
		String cause = (source == null ? NEWSDOG : source);
		String postData = getPostContentBody(request);
		JSONObject responseJson = new JSONObject();
		switch (cause) {
		case "newsdog":
			responseJson = indiaService.like(did, ntype, lang, postData);
			break;
		case "JC":
			break;
		case "LT":
			responseJson = ltnewsService.like(did, ntype, lang, postData);
			break;
		}

		try {
			Api.response(responseJson, response, "result_object");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("unlike")
	public void unlikeNews(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype,
			@RequestHeader(value = "source", required = false) String source) {
		String lang = (language == null ? ENGLISH : language);
		String cause = (source == null ? NEWSDOG : source);
		String postData = getPostContentBody(request);
		JSONObject responseJson = new JSONObject();
		switch (cause) {
		case "newsdog":
			responseJson = indiaService.unlike(did, ntype, lang, postData);
			break;
		case "JC":
			break;
		case "LT":
			responseJson = ltnewsService.unlike(did, ntype, lang, postData);
			break;
		}
		try {
			Api.response(responseJson, response, "result_object");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.info("API class is wrong:", e);
		}
	}

	@RequestMapping("log")
	public void PostLog(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) throws IOException {
		String lang = (language == null ? ENGLISH : language);
		String getData = getPostContentBody(request);
		JSONObject result = indiaService.log(lang, getData);
		Api.response(result, response, "result_object");
	}

	@RequestMapping("collection")
	public void getCollection(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "language", required = false) String language,
			@RequestHeader(value = "did", required = false) String did, @RequestHeader(value = "ntype", required = false) String ntype) throws IOException {
		String lang = (language == null ? ENGLISH : language);
		String getData = getPostContentBody(request);
		JSONObject reponseJson = indiaService.collection(lang, getData);
		Api.response(reponseJson, response, "result_object");
	}

}
