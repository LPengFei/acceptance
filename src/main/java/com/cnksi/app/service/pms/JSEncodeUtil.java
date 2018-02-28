package com.cnksi.app.service.pms;

import java.io.FileReader;
import java.net.URL;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.apache.log4j.Logger;

public class JSEncodeUtil {
	public static Logger logger = Logger.getLogger(JSEncodeUtil.class);
	
	private static Invocable invoke;
	
	static{

		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");

		URL resource = JSEncodeUtil.class.getResource("pms_encode.js");
		String jsFilePath = resource.getPath().toString();
		logger.info("	【加密文件位置】" + jsFilePath);

		try {
			FileReader reader = new FileReader(jsFilePath);
			engine.eval(reader);

			invoke = (Invocable) engine;
			invoke.invokeFunction("encode", "abc");
		} catch (Exception e) {

			throw new RuntimeException("加载pms_encode.js文件出错！");
		}
	}

	
	
	public static String encode(String pwd) {
		try {
			
			return (String) invoke.invokeFunction("encode", pwd);
		} catch (Exception e) {
			throw new RuntimeException("调用pms_encode.js文件中的encode方法出错！");
		}
	}
}
