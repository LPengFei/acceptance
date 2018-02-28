package com.cnksi.app.service.pms;

import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * <pre>
 * @author pantao
 * @version 创建时间：2016年3月8日 上午10:48:32 
 * 
 * 类说明 ：对接过程中的相关常量配置
 * </pre>
 */
public class PmsConstants {
	public static Logger logger = Logger.getLogger(PmsConstants.class);

	/**
	 * 注销PMS地址
	 */
	public static String PMS_LOGOUT_URL;

	/** 登录地址 */
	public static String PMS_LOGIN_URL;

	/** 数据提交地址 */
	public static String PMS_DATA_POST_URL;

	/** 取得验证 */
	public static String URL_ACCESS_STOKEN;
	
	/**调试模式，会返回自己模拟的数据**/
	public static Boolean PMS_DEBUG;


	static {
		logger.info("初始化常量constants.properties");
		Properties map = PropUtil.use("constants.properties");

		PMS_LOGOUT_URL = map.getProperty("PMS.PMS_LOGOUT_URL");
		logger.info("PMS注销 地址： " + PMS_LOGOUT_URL);

		PMS_LOGIN_URL = map.getProperty("PMS.LOGIN_URL");
		logger.info("登录 地址： " + PMS_LOGIN_URL);

		PMS_DATA_POST_URL = map.getProperty("PMS.DATA_POST_URL");
		logger.info("数据提交地址：" + PMS_DATA_POST_URL);

		URL_ACCESS_STOKEN = map.getProperty(("PMS.URL_ACCESS_STOKEN"));
		URL_ACCESS_STOKEN = PMS_DATA_POST_URL + URL_ACCESS_STOKEN;
		logger.info("验证地址:" + URL_ACCESS_STOKEN);
		
		PMS_DEBUG = Boolean.valueOf(map.getProperty("PMS.DEBUG", "false").trim());

	}
}
