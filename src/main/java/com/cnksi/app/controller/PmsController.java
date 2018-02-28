package com.cnksi.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;

/**
 * 模拟pms的数据
 *
 */
@Clear
public class PmsController extends Controller {
	private final static String encoding = "UTF-8";
	
	
	public void download() throws IOException{
		
		//格式如下：/sgpms/xsgl/rest/zwxsjl/?rnd=0.7165595868136734&params=columns
		String url = getRequest().getQueryString().replace("%27", "'");	

		if(url.startsWith("/sgpms/xsgl/rest/zwxsjl/")){
			
			renderJsonFromFile("token.json");
		}else if(url.startsWith("/sgpms/com.sgcc.pms.ywjx.pwgg/rest/commsvc/getRyxx")){
			
			renderJsonFromFile("dept.json");
		}else if(url.indexOf("sszf") > -1){
			
			renderJsonFromFile("spacing.json");
		}else if(url.indexOf("jgdy") > -1){
			
			renderJsonFromFile("device.json");
		}else if(url.indexOf("WHBZ") > -1){
			
			renderJsonFromFile("bdz.json");
		}else{
			
			renderText("找不到数据");
		}
	}
	
	public void renderJsonFromFile(String name) throws IOException{
		StringBuilder result = new StringBuilder();
		
		
		String filePath = PathKit.getWebRootPath()+"/upload/"+name;
		
		
		FileInputStream fis = new FileInputStream(new File(filePath));
		InputStreamReader isr = new InputStreamReader(fis, encoding);  
		
		char[] chars = new char[1024];
		try {
			
			int length = 0;
			while((length = isr.read(chars)) != -1){
				for(int i = 0;i<length && i < chars.length;i++){
					
					result.append(chars[i]);
				}
			}
			
			renderJson(result.toString());
		} finally {
			isr.close();
			fis.close();
		}
	}
	
}
