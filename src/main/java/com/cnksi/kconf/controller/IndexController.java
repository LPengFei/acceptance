package com.cnksi.kconf.controller;

import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.Menu;
import com.cnksi.kconf.model.User;
import com.cnksi.kcore.utils.SystemInfo;
import com.cnksi.kcore.utils.WebUtils;
import com.jfinal.core.Controller;

/**
 * 系统主页
 * 
 * @author joe
 *
 */
public class IndexController extends Controller {

	public void index() {
		User user = getSessionAttr(KConfig.SESSION_USER_KEY);
		
		setAttr("parentMenus", Menu.me.findMenuAndChidrenByRole(user.getUserRoleId()));
		
		render("index.jsp");
	}

	/**
	 * 主页中间部分页面
	 */
	public void index_main() {
		setAttr("ip", WebUtils.getClientIp(getRequest()));
//		SystemInfo.getInstance(getRequest()).PrintInfo();
//		setAttr("sysinfo",SystemInfo.getInstance(getRequest()));
		render("index_main.jsp");
	}
}
