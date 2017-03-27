package com.revanow.news.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;



public class Api {

	
	public static void response(Object result,HttpServletResponse response,String key) throws IOException{
		
		JSONObject responseJSon =new JSONObject();
		responseJSon.put("code", 200);
		responseJSon.put("message", "OK");
		responseJSon.put("animate", 0);
		responseJSon.put(key, result);
		response.setContentType("application/json;charset=utf-8");  
	    PrintWriter out = response.getWriter();  
	    out.write(responseJSon.toString());  
	}
	
	public static void response(int code, String message,Object result,HttpServletResponse response,String key) throws IOException{
		JSONObject responseJSon =new JSONObject();
		responseJSon.put("code", code);
		responseJSon.put("message", message);
		responseJSon.put("animate", 0);
		responseJSon.put("result", result);
		response.setContentType("application/json;charset=utf-8");  
	    PrintWriter out = response.getWriter();  
	    out.write(responseJSon.toString());  
	}
}
