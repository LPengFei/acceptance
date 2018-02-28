package com.cnksi.kconf.controller;

import com.cnksi.kconf.KConfig;
import com.cnksi.kconf.model.User;
import com.cnksi.kcore.utils.KStrKit;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.StrKit;

import java.util.List;

/**
 * 用户登录Controller
 *
 * @author joe
 */
public class SecureController extends Controller {
    public void index() {
        if (JFinal.me().getConstants().getDevMode()) {
            List<User> users = User.me.findAll();
            if(users.size() < 1 ) throw new RuntimeException("数据库中没有用户，请添加用户！");
            
			User user = users.get(0);
            setSessionAttr(KConfig.SESSION_USER_KEY, user);
            setSessionAttr(KConfig.SESSION_DEPT_KEY, user.getDepartment());
            setSessionAttr("appid", KConfig.appid);
            redirect("/app/index");
        } else {
            renderLoginJsp();
        }
    }

    /**
     * 登录校验
     */
    public void dologin() {
        String error = "";
        // 校验验证码
//        if (!validateCaptcha("captcha")) {
//            error = "登录失败，验证码错误";
//        } else {
            error = "登录失败,您输入的账号/密码不正确";
            String username = getPara("username");
            String password = getPara("password");
            User user = User.me.findByPropertity("uaccount", username);
            password = KStrKit.Encrypt(password);       //加密密码
            if (user != null && password.equalsIgnoreCase(user.getStr("upwd"))) {
                setSessionAttr(KConfig.SESSION_USER_KEY, user);
                setSessionAttr(KConfig.SESSION_DEPT_KEY, user.getDepartment());
                error = null;
            }
//        }

        setSessionAttr("appid", KConfig.appid);

        if (StrKit.isBlank(error)) {
            redirect("/secure/main");
        } else {

            setAttr("error", error);
            renderLoginJsp();
        }
    }

    private void renderLoginJsp() {
        render("login.jsp");
    }

    /**
     * 退出系统并调整到登录界面
     */
    public void logout() {
        removeSessionAttr(KConfig.SESSION_USER_KEY);
        removeSessionAttr(KConfig.SESSION_DEPT_KEY);
        redirect("/secure/index");
    }

    /**
     * 登录成功后跳转到主页
     */
    public void main() {
        redirect("/app/index");
    }

    /**
     * 验证码
     */
    public void captcha() {
        renderCaptcha();
    }
}
