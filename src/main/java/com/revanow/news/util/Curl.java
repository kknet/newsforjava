package com.revanow.news.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;


public class Curl {
	
	public static String curl_get(String ApiUrl,String method,String token) throws IOException{
		HttpURLConnection httpcon = (HttpURLConnection) ((new URL(ApiUrl).openConnection()));
	    httpcon.setDoOutput(true);
	    httpcon.setRequestProperty("Authorization", "HIN "+token);
	    httpcon.setRequestMethod(method);
	    httpcon.connect();
	    
	    InputStream is;
	    if (httpcon.getResponseCode() >= 400) {
	        is = httpcon.getErrorStream();
	    } else {
	        is = httpcon.getInputStream();
	    }
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String lines;
        StringBuilder  sb = new StringBuilder();
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
       
        reader.close();
        return sb.toString();
	}
	
	
	
	public static String curl_post(JSONObject postJson,String ApiUrl,String method) throws IOException{
		HttpURLConnection httpcon = (HttpURLConnection) ((new URL(ApiUrl).openConnection()));
	    httpcon.setDoOutput(true);
	    
	    httpcon.setRequestProperty("Content-Type", "application/json");
	    httpcon.setRequestProperty("Content-Length", Integer.toString(postJson.toString().length()));
	    httpcon.setRequestMethod(method);
	    httpcon.connect();

	    byte[] outputBytes = postJson.toString().getBytes("UTF-8");
	    OutputStream os = httpcon.getOutputStream();
	    os.write(outputBytes);

	    os.close();
	    
	    InputStream is;
	    if (httpcon.getResponseCode() >= 400) {
	        is = httpcon.getErrorStream();
	    } else {
	        is = httpcon.getInputStream();
	    }
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String lines;
        StringBuilder  sb = new StringBuilder();
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
       
        reader.close();
        return sb.toString();
	}
	
	public static String curl_post(JSONObject postJson,String ApiUrl,String method,String token) throws IOException{
		HttpURLConnection httpcon = (HttpURLConnection) ((new URL(ApiUrl).openConnection()));
	    httpcon.setDoOutput(true);
	    
	    httpcon.setRequestProperty("Content-Type", "application/json");
	    httpcon.setRequestProperty("Content-Length", Integer.toString(postJson.toString().length()));
	    httpcon.setRequestProperty("Authorization", "HIN "+token);
	    httpcon.setRequestMethod(method);
	    httpcon.connect();

	    byte[] outputBytes = postJson.toString().getBytes("UTF-8");
	    OutputStream os = httpcon.getOutputStream();
	    os.write(outputBytes);

	    os.close();
	    
	    InputStream is;
	    if (httpcon.getResponseCode() >= 400) {
	        is = httpcon.getErrorStream();
	    } else {
	        is = httpcon.getInputStream();
	    }
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String lines;
        StringBuilder  sb = new StringBuilder();
        while ((lines = reader.readLine()) != null) {
            lines = new String(lines.getBytes(), "utf-8");
            sb.append(lines);
        }
       
        reader.close();
        return sb.toString();
	}

}
