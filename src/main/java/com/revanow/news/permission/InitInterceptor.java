package com.revanow.news.permission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.revanow.news.domain.dte.Regipdte;
import com.revanow.news.service.RegipService;
import com.revanow.news.util.newsHelper;

public class InitInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(InitInterceptor.class);
	
	@Autowired
	private RegipService regipService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String language = request.getParameter("language");
		String ipStr = newsHelper.getRealIP(request);
		long ip = newsHelper.ipToLong(ipStr);
		LOG.info("db search start" + System.currentTimeMillis());
		Regipdte reg = regipService.getRegForIp(ip);
		LOG.info("db search end" + System.currentTimeMillis());
		String location = "us";
		if(reg != null && !reg.getReg().isEmpty()){
			location = reg.getReg();
		}
		request.setAttribute("location", location);
		String neoSource = "IN".equals(location) ? "newsdog" : "google";
		request.setAttribute("neoSource", neoSource);
		String neoLanguage;
		if("english".equals(language)) {
			neoLanguage = "en";
		}else if("india".equals(language)){
			neoLanguage = "in";
		}else{
			neoLanguage = language;
		}
		request.setAttribute("neolanguage", neoLanguage);
		
		return true;
	}

}
