package com.cnksi.app.handler;

import com.jfinal.handler.Handler;
import org.jsoup.helper.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * create by lpf in 2017-10-12
 */
public class XssHandler extends Handler {
    // 排除的url，使用的target.startsWith匹配的
    private String excludePattern;

    /**
     * XSS 攻击字符串过滤器
     *
     * @param excludePattern 忽略列表，使用正则表达式
     */
    public XssHandler(String excludePattern) {
        this.excludePattern = excludePattern;
    }

    @Override
    public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
        //方式一
        // 对于非静态文件，和非指定排除的url实现过滤
        //if (target.indexOf(".") == -1&&!target.contains("manager")){
        //request = new HttpServletRequestWrapper(request);
        //}
        //nextHandler.handle(target, request, response, isHandled);

        //方式为二
        Pattern pattern = Pattern.compile(excludePattern);
        //带.表示非action请求(资源文件)，忽略（其实不太严谨，如果是伪静态，比如.html会被错误地排除）；匹配excludePattern的，忽略
        if (!target.contains(".") && !(excludePattern != null && !excludePattern.isEmpty() && pattern.matcher(target).find())) {
            //System.out.println("对请求URL:"+target+" --过滤非法字符串");
            System.out.println("对请求过滤非法字符");
            request = new XssHttpServletRequestWrapper(request);
        }
        /*else{
            System.out.println("对资源文件URL "+target+"--忽略过滤");
        }*/
        //将请求交给下一个handle
        next.handle(target, request, response, isHandled);
    }
}
