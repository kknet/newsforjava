package com.revanow.news.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


@Controller
public class BaseController {

	private String requestUrl = "http://api.newsdog.today/";
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

	protected String getPostContentBody(HttpServletRequest request) {

		InputStream is = null;
		String contentStr = "";
		try {
			is = request.getInputStream();
			contentStr = IOUtils.toString(is, "utf-8");
		} catch (IOException e) {
			logger.info("get post Param",e);
		}
		return contentStr;
	}

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

	protected String http_build_query(HashMap<String, String> data) {
		String buildQuery = "";
		for (Map.Entry<String, String> entry : data.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			buildQuery += key + "=" + value + "&";
		}
		buildQuery = buildQuery.substring(0, buildQuery.length() - 1);
		return buildQuery;
	}

	protected String switch_language(String language) {
		String lang;
		switch (language) {
		case "english":
			lang = "en";
			break;
		case "indonesian":
			lang = "id";
			break;
		default:
			lang = "en";
			break;
		}
		return lang;

	}

	public String upperCase(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}
}
