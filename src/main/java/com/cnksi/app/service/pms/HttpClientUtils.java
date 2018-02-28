package com.cnksi.app.service.pms;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * HttpClient请求
 * 
 * @author java
 * 
 */
public class HttpClientUtils {
	/**
	 * 默认字符集 UTF-8
	 */
	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 默认用户授权
	 */
	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36";

	/**
	 * url重定向次数
	 */
	public static int count = 0;

	public static CookieStore cookieStore;

	static {
		if (cookieStore == null) {
			cookieStore = new BasicCookieStore();
		}
	}

	/**
	 * get方式发送请求
	 * 
	 * @param url
	 * @param paramaters
	 * @param showResult
	 * @return
	 */
	public static String get(String url, Map<String, String> paramaters, boolean showResult) {

		DefaultHttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(HttpMethodParams.USER_AGENT, DEFAULT_USER_AGENT);
		client.setCookieStore(cookieStore);

		String param = "1=1";
		if (paramaters != null) { // 封装参数
			// 如果参数有中文，需要进行URLEncoding
			// URLEncoder.encode("中国", DEFAULT_CHARSET);
			for (String key : paramaters.keySet()) {
				param += "&" + key + "=" + paramaters.get(key);
			}
		}
		HttpGet httpGet = new HttpGet(url + "?" + param); // 设置请求的路径

		String returnResult = ""; // 请求结果
		try {

			// 创建一个本地上下文信息，并传递给服务器
			HttpContext localContext = new BasicHttpContext();
			HttpResponse response = client.execute(httpGet, localContext);

			cookieStore = client.getCookieStore();
			returnResult = EntityUtils.toString(response.getEntity()); // 获取返回值

			getLocation(response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnResult;
	}

	/**
	 * POST json格式的数据
	 * 
	 * @param url
	 * @param jsonParam
	 * @param cookieStore
	 * @param showResult
	 * @return
	 */
	public static String postJsonData(String url, String jsonParam, boolean showResult) {
		String result = "";

		DefaultHttpClient client = new DefaultHttpClient(); // 获取Cookie的信息
		client.getParams().setParameter(HttpMethodParams.USER_AGENT, DEFAULT_USER_AGENT);
		client.setCookieStore(cookieStore);

		HttpPost httpPost = new HttpPost(url);

		// 提交json格式的数据
		StringEntity entity = null;
		try {
			entity = new StringEntity(jsonParam, DEFAULT_CHARSET);
			entity.setContentEncoding(DEFAULT_CHARSET); // 解决中文乱码问题
			entity.setContentType("application/json");

			Header[] headerss = { new BasicHeader("X-Requested-With", "XMLHttpRequest") }; // 请求头信息X-Requested-With=XMLHttpRequest，表示提交ajax的数据
			httpPost.setHeaders(headerss);

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try {

			httpPost.setEntity(entity);

			// 创建一个本地上下文信息，并传递给服务器
			HttpContext localContext = new BasicHttpContext();
			HttpResponse response = client.execute(httpPost, localContext);
			cookieStore = client.getCookieStore();

			result = EntityUtils.toString(response.getEntity());// 获取本地信息

			System.out.println("" + result);

			String location = getLocation(response);
			if (location != null && !"".equals(location) && (count++) < 5) {
				get(location, null, showResult);
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * POST方式的请求
	 * 
	 * @param url
	 * @param paramaters
	 * @param cookieStore
	 * @param showResult
	 * @return
	 */

	public static String post(String url, Map<String, String> paramaters, boolean showResult) {
		String result = "";

		DefaultHttpClient client = new DefaultHttpClient(); // 获取Cookie的信息
		client.setCookieStore(cookieStore);
		client.getParams().setParameter(HttpMethodParams.USER_AGENT, "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");

		HttpPost httpPost = new HttpPost(url); // 设置请求的路径

		// 提交form表单格式的数据
		UrlEncodedFormEntity entity = null;
		try {
			List<NameValuePair> list = new ArrayList<NameValuePair>(); // 设置参数
			for (String key : paramaters.keySet()) {
				list.add(new BasicNameValuePair(key, paramaters.get(key)));
			}
			entity = new UrlEncodedFormEntity(list, DEFAULT_CHARSET);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		try {
			httpPost.setEntity(entity);

			// 创建一个本地上下文信息，并传递给服务器
			HttpContext localContext = new BasicHttpContext();
			HttpResponse response = client.execute(httpPost, localContext);
			cookieStore = client.getCookieStore();

			result = EntityUtils.toString(response.getEntity());// 获取本地信息
			String location = getLocation(response);
			if (location != null && !"".equals(location) && (count++) < 5) {
				post(location, paramaters, showResult);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * httpclient模拟上传文件
	 * 
	 * @param file
	 * @param url
	 */
	public boolean uploadFile(File file, String url) {
		if (!file.exists()) {
			return false;
		}
		PostMethod postMethod = new PostMethod(url);
		try {
			// 提交包含文件的form表单
			// FilePart：用来上传文件的类
			// 提交文件和参数
			Part[] parts = { new FilePart("file1", file), new StringPart("test", "中文参数", "UTF-8") };

			// 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
			MultipartRequestEntity multiEntity = new MultipartRequestEntity(parts, postMethod.getParams());
			postMethod.setRequestEntity(multiEntity);

			HttpClient client = new HttpClient();

			// 设置连接时间
			client.getHttpConnectionManager().getParams().setConnectionTimeout(50000);

			// 执行
			int status = client.executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				System.out.println("上传成功");
				System.out.println(postMethod.getResponseBodyAsString());
				return true;
			} else {
				System.out.println("fail");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 释放连接
			postMethod.releaseConnection();
		}
		return false;
	}

	/**
	 * 获取response中header的location信息
	 * 
	 * @param response
	 * @return
	 */
	private static String getLocation(HttpResponse response) {
		String location = null;
		int responseCode = response.getStatusLine().getStatusCode();
		if (responseCode == 302) {
			Header locationHeader = response.getFirstHeader("Location");
			if (locationHeader != null) {
				location = locationHeader.getValue();
			}
		}
		return location;
	}

	/**
	 * 处理返回结果
	 * 
	 * @param resultStr
	 * @return
	 */
	public static Map<String, Object> getItemsValue(String resultStr) {
		Map<String, Object> map = new HashMap<String, Object>();

		ArrayList<Map<String, Object>> items = turnJsonStrToList(resultStr, "items");

		if (items != null) {
			map.put("items", items);
		}
		return map;
	}
	
	/**
	 * 处理返回结果
	 * 
	 * @param resultStr
	 * @return
	 */
	public static JSONArray getItems(String resultStr) {
		try {
			JSONObject json = new JSONObject(resultStr);
			JSONObject child = (JSONObject) json.get("resultValue");
			JSONArray devices = (JSONArray) child.get("items");

			return devices;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 处理返回结果
	 * 
	 * @param resultStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Map<String, Object>> turnJsonStrToList(String resultStr, String colums) {
		try {
			Map<String, Object> gsonMap = turnJsonStrToMap(resultStr);

			Object temp = gsonMap.get("resultValue");
			if (temp == null || "".equals(temp)) {
				return null;
			}
			Map<String, Object> resultValue = (Map<String, Object>) temp;
			ArrayList<Map<String, Object>> items = (ArrayList<Map<String, Object>>) resultValue.get(colums);
			return items;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 处理返回结果
	 * 
	 * @param resultStr
	 * @return
	 */
	public static Map<String, Object> turnJsonStrToMap(String resultStr) {
		Map<String, Object> gsonMap = new HashMap<String, Object>();
		try {
			GsonBuilder gb = new GsonBuilder();
			Gson gson = gb.create();
			gsonMap = gson.fromJson(resultStr, new TypeToken<Map<String, Object>>() {
			}.getType());

		} catch (Exception e) {
			e.printStackTrace();
			return gsonMap;
		}
		return gsonMap;
	}

	public static void main(String[] args) {
		new HttpClientUtils().uploadFile(new File("D:/sign_key.key"), "http://localhost:9301/sjjc/save1?name=ok");
	}

}