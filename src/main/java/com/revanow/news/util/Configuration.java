package com.revanow.news.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Configuration {

	private static Configuration configuration = null;
	private static final String PROPERTIES_FILE = "config.properties";
	
	private static final Logger logger = LoggerFactory.getLogger(Configuration.class);

	private Properties properties = null;

	public static Configuration getInstance() {
		if (configuration == null) {
			configuration = new Configuration();
		}

		return configuration;
	}

	private Configuration() {
		InputStream inputStream = null;
		try {
			inputStream = new BufferedInputStream(Configuration.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
			properties = new Properties();
			properties.load(inputStream);
		} catch (IOException loadException) {
			logger.info("IOException",loadException);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException closeException) {
				logger.info("IOException",closeException);
			}
		}
	}

	public String getString(String key) {
		return properties.getProperty(key);
	}

	public int getInt(String key) {
		String str = properties.getProperty(key);
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			logger.info("NumberFormatException",e);
			return 0;
		}
	}
}
