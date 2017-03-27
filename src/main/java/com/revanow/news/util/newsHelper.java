package com.revanow.news.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class newsHelper {

	private static final Logger logger = LoggerFactory.getLogger(newsHelper.class);
	
	public static String upperCase(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	public static String buildHttpRequest(String did, String ntype, String language) {
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("ntype", ntype);
		request.put("company", "android:lewa");
		String lang;
		switch (language) {
		case "english":
			lang = "en";
			break;
		case "india":
			lang = "hi";
			break;
		default:
			lang = "en";
			break;
		}
		request.put("lang", lang);
		request.put("region", "IN");
		request.put("long", "121.48");
		request.put("lat", "31.22");
		request.put("did", did);
		return http_build_query(request);
	}

	public static String http_build_query(HashMap<String, String> data) {
		String buildQuery = "";
		for (Map.Entry<String, String> entry : data.entrySet()) {
			buildQuery += entry.getKey() + "=" + entry.getValue() + "&";
		}
		buildQuery = buildQuery.substring(0, buildQuery.length() - 1);
		return buildQuery;
	}

	public static long ipToLong(String strIp) {
		long[] ip = new long[4];
		// 先找到IP地址字符串中.的位置
		int position1 = strIp.indexOf('.');
		int position2 = strIp.indexOf('.', position1 + 1);
		int position3 = strIp.indexOf('.', position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
	}
	
	
	public static String getRealIP(HttpServletRequest request) {
		String forward = request.getHeader("X-Real-IP");
		String ip = forward;
		if (ip != null) {
			logger.info("=========X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 截断只取最后一个ip
		if (ip != null) {
			if (ip.indexOf(',') >= 0) {
				ip = ip.substring(ip.lastIndexOf(',') + 2, ip.length());
			}
		}
		return ip;
	}

}
