package com.cnksi.app.service.pms;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import com.jfinal.kit.LogKit;

public class PropUtil {
	private static final String encoding = "UTF-8";
	
	public static Properties use(String fileName) {
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);		// properties.load(Prop.class.getResourceAsStream(fileName));
			if (inputStream == null)throw new IllegalArgumentException("在classpath下没有找到文件: " + fileName);
			
			Properties properties = new Properties();
			properties.load(new InputStreamReader(inputStream, encoding));
			
			return properties;
		} catch (IOException e) {
			throw new RuntimeException("读取配置文件出错", e);
		}
		finally {
			if (inputStream != null) try {inputStream.close();} catch (IOException e) {LogKit.error(e.getMessage(), e);}
		}
	}
}
