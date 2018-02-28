package com.cnksi.app.service.pms;

import java.util.HashMap;
import java.util.Map;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.log4j.Logger;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.json.JSONException;
import org.json.JSONObject;



/**
 * 从巡检系统Report项目中拷贝的PMSService
 */
@SuppressWarnings("unused")
public class XunjianPMSService{
	private Logger logger = Logger.getLogger(this.getClass());
	
	public static XunjianPMSService service = new XunjianPMSService();


	/**
	 * 登录
	 * 
	 * @param username
	 * @param pwd
	 * @return 登录成功返回：<div id=\"msg\" class=\"success\"><h2>Log In Successful</h2></div>
	 * @throws Exception
	 */
	private String login(String username, String pwd) throws Exception {

		String logoutResult = HttpClientUtils.get(PmsConstants.PMS_LOGOUT_URL, null, true);
		logger.info("登录前先注销PMS，注销结果：" + logoutResult);

		HttpClientUtils.cookieStore.clear();
		HttpClientUtils.cookieStore = new BasicCookieStore();

		// 获取lt
		String html = HttpClientUtils.get(PmsConstants.PMS_LOGIN_URL, null, true);
		String lt = this.analyzeLt(html);

		if (isBlank(lt)) {
			// throw new Exception("连接服务器失败(未获取到lt)");
			logger.info("	【连接服务器失败(未获取到lt)】");
			return "连接服务器失败(未获取到lt)";
		}

		pwd = pwd.trim();
		// 加密处理
		logger.info("	【加密前：】" + pwd);
		pwd = encode(pwd);
		logger.info("	【加密后：】" + pwd);

		// 登录
		Map<String, String> paramaters = new HashMap<String, String>();

		paramaters.put("username", username); // 运维人员账号
		paramaters.put("password", pwd); // 这是加密后的密码
		paramaters.put("lt", lt);
		paramaters.put("execution", "e1s1");
		paramaters.put("_eventId", "submit");

		String loginResult = HttpClientUtils.post(PmsConstants.PMS_LOGIN_URL, paramaters, true);
		logger.info("	【登录结果:】" + loginResult);

		// 宁夏需要这段
		// loginResult = HttpClientUtils.get(PmsConstants.URL_ACCESS_STOKEN_VALIDATE_NX, paramaters, true);
		// logger.info("	【登录结果2:】" + loginResult);

		if (!isSuccessful(loginResult)) {
			long startTime = System.currentTimeMillis();

			loginResult = HttpClientUtils.post(PmsConstants.URL_ACCESS_STOKEN, paramaters, true);
			logger.info(this.analyzeLoginResult(loginResult));
			logger.info("	【登录结果:temp】" + this.isSuccessful(loginResult));
			logger.info("	获取用时：" + (System.currentTimeMillis() - startTime) / 1000);
		}

		if (!isSuccessful(loginResult)) {
			loginResult = this.pmsAccessValidate();
			logger.info("	【验证结果:】" + loginResult);
		}

		return loginResult;
	}

	/**
	 * HtmlCleaner xpath解析html获取lt
	 */
	private String analyzeLt(String html) {
		String lt = "";
		try {
			String xpath = "//input[@name='lt']/@value";

			HtmlCleaner hc = new HtmlCleaner();
			TagNode tn = hc.clean(html);
			Object[] objarr = tn.evaluateXPath(xpath);

			if (objarr != null && objarr.length > 0) {
				lt = objarr[0] + "";
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}

		logger.info("	lt获取地址 : " + PmsConstants.PMS_LOGIN_URL);
		logger.info("	lt:" + lt);

		return lt;
	}

	/**
	 * HtmlCleaner xpath解析html获取值
	 */
	private String analyzeLoginResult(String html) {
		String result = "";
		try {
			String xpath = "//div[@class='success']/h2/text()"; // 获取class=success的div下，h2标签的内容
			// 英文结果
			// <div id="msg" class="success">
			// <h2>Log In Successful</h2>
			// <p>You have successfully logged into the Central Authentication Service.</p>
			// <p>For security reasons, please Log Out and Exit your web browser when you are done accessing services that require authentication!</p>
			// </div>

			// 中文结果
			// <div id="msg" class="success">
			// <h2>登录成功</h2>

			HtmlCleaner hc = new HtmlCleaner();
			TagNode tn = hc.clean(html);
			Object[] objarr = tn.evaluateXPath(xpath);

			if (objarr != null && objarr.length > 0) {
				result = objarr[0] + "";
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 加密
	 * 
	 * @param pwd
	 * @return
	 */
	private String encode(String pwd) {
		return JSEncodeUtil.encode(pwd);
	}

	/**
	 * 获取json结果
	 * 
	 * @param url
	 * @param account
	 * @param pwd
	 * @return
	 */
	private String getAndReturnJsonStr(String url, String account, String pwd) {

		String resultStr = null;

		// String temp = HttpClientUtils.get(PmsConstants.URL_ACCESS_STOKEN, null, true); // 获取验证
		//
		// // 宁夏
		// // String temp = HttpClientUtils.get(PmsConstants.URL_ACCESS_STOKEN_VALIDATE_NX, null, true); // 获取验证
		// boolean result = isSuccessful(temp);

		boolean result = this.pmsAccessIsValid();

		if (result) {
			resultStr = HttpClientUtils.get(url, null, true);
			result = isSuccessful(resultStr);
		}
		if (!result) { // 如果验证或者get失败，则需要先登录，再get数据
			try {
				login(account, pwd);

				this.pmsAccessValidate();
				// HttpClientUtils.get(PmsConstants.URL_ACCESS_STOKEN, null, true); // 获取验证
				// 宁夏
				// HttpClientUtils.get(PmsConstants.URL_ACCESS_STOKEN_VALIDATE_NX, null, true); // 获取验证
				//

				resultStr = HttpClientUtils.get(url, null, true); // 提交数据
			} catch (Exception e) {
				System.out.println("登录失败");
				e.printStackTrace();
			}
		}
		return resultStr;
	}

	/**
	 * 登录校验
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	private boolean pmsLogin(String account, String pwd) {
		try {

			String resultStr = login(account, pwd); // 获取登录返回值

			String resultSt = analyzeLoginResult(resultStr); // 解析登录返回值：登录成功会返回到一个jsp页面，且包含内容：<h2>Log In Successful</h2>

			boolean result = resultSt.contains("Successful") || resultSt.contains("登录成功") || isSuccessful(resultStr);

			return result;
		} catch (Exception e) {
			logger.info("登录失败");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 登录后获取授权验证结果
	 * 
	 * @param account
	 * @param pwd
	 * @return
	 */
	public boolean pmsLoginAndAccessValidate(String account, String pwd) {

		try {

			boolean result = this.pmsAccessIsValid();
			if (!result) { // 如果验证或者get失败，则需要先登录，再次验证

				logger.info("	=== 第一次获取验证，失败，进行登录");
				String temp = this.login(account, pwd);
				
				temp = this.pmsAccessValidate();

				result = this.isSuccessful(temp);
				logger.info("	=== 获取登录验证,结果:" + temp);
			}
			return result;
		} catch (Exception e) {
			logger.info("登录失败");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 校验登录是否有效
	 * 
	 * @return
	 */
	private boolean pmsAccessIsValid() {
		return this.isSuccessful(this.pmsAccessValidate());
	}

	/**
	 * 校验登录是否有效
	 * 
	 * @return
	 */
	private String pmsAccessValidate() {
		// 先验证一次，如果已经登录过，可以直接通过验证
		// 四川验证方式
		// logger.info("	=== 第一次获取验证:" + PmsConstants.URL_ACCESS_STOKEN);
		// String temp = HttpClientUtils.get(PmsConstants.URL_ACCESS_STOKEN, null, true); // 第一次获取验证

		// 宁夏验证方式
		String temp = HttpClientUtils.get(PmsConstants.URL_ACCESS_STOKEN, null, true);
		logger.info("	=== 第一次获取验证:" + PmsConstants.URL_ACCESS_STOKEN);
		logger.info("	=== 第一次获取验证,结果:" + temp);

		// temp = HttpClientUtils.get(PmsConstants.URL_ACCESS_STOKEN_VALIDATE_NX, null, true);
		// logger.info("	=== 第二次获取验证:" + PmsConstants.URL_ACCESS_STOKEN_VALIDATE_NX);
		// logger.info("	=== 第二次获取验证,结果:" + temp);

		return temp;
	}

	/**
	 * 是否处理成功
	 * 
	 * @param resultStr
	 * @return
	 */
	private boolean isSuccessful(String resultStr) {

		logger.info("	" + resultStr);

		if (isBlank(resultStr)) {
			return false;
		}
		try {
			JSONObject json = new JSONObject(resultStr); // 这里如果不是返回标准的json格式，则会报错

			boolean result = json.getBoolean("successful");

			return result;
		} catch (JSONException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean isBlank(String str) {
		return str == null || "".equals(str.trim()) || "null".equals(str);
	}
	/**
	 * 获取json结果
	 * 
	 * @param url
	 * @param account
	 * @param pwd
	 * @return
	 */
	private String getJsonResult(String url, String account, String pwd) {

		return getJsonResult(url, account, pwd, false);
	}

	/**
	 * 获取json结果
	 * 
	 * @param url
	 * @param account
	 * @param pwd
	 * @return
	 */
	private String getJsonResult(String url, String account, String pwd, boolean notValidateResult) {

		return getJsonResult(url, account, pwd, null, notValidateResult);
	}

	/**
	 * 获取json结果
	 * 
	 * @param url
	 * @param account
	 * @param pwd
	 * @return
	 */
	private String getJsonResult(String url, String account, String pwd, Map<String, String> paramaters, boolean notValidateResult) {


		String resultStr = null;

		String temp = HttpClientUtils.get(PmsConstants.URL_ACCESS_STOKEN, null, true); // 获取验证
		boolean result = dealSaveResult(temp);
		System.out.println("	验证结果：" + result);
		if (result) {
			resultStr = HttpClientUtils.get(url, paramaters, true);
			if (notValidateResult) {

			} else {
				result = dealSaveResult(resultStr);
			}
		}
		if (!result) { // 如果验证或者post失败，则需要先登录，再post数据
			try {
				login(account, pwd);

				HttpClientUtils.get(PmsConstants.URL_ACCESS_STOKEN, null, true); // 获取验证

				resultStr = HttpClientUtils.get(url, paramaters, true); // 提交数据
			} catch (Exception e) {
				System.out.println("登录失败");
				e.printStackTrace();
			}
		}
		return resultStr;
	}

	/**
	 * 处理返回结果
	 * 
	 * @param resultStr
	 * @return
	 * @throws JSONException
	 */
	private boolean dealSaveResult(String resultStr) {

		try {
			JSONObject json = new JSONObject(resultStr); // 这里如果不是返回标准的json格式，则会报错

			boolean result = json.getBoolean("successful");

			return result;
		} catch (JSONException e) {
			if (e.getMessage() != null && e.getMessage().contains("objId")) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
