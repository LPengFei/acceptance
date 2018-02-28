package com.cnksi.interceptor;

import com.cnksi.kconf.KConfig;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class LoginInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		String invKey = inv.getActionKey();

		// 必须登录才可以访问的资源
		if (invKey.startsWith("/" + KConfig.appid)) {
			if (inv.getController().getSessionAttr(KConfig.SESSION_USER_KEY) != null) {
				inv.invoke();
			} else {
				inv.getController().redirect("/secure/index");
			}
		} else {
			inv.invoke();
		}
	}

}
