package com.cnksi.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.jfinal.kit.PropKit;

/**
 * 页面获取系统配置文件Tag
 * 
 * @author joe
 *
 */
public class KPropTag extends TagSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(KValueTag.class);

	// 数据库表名
	private String key;

	@Override
	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {

		JspWriter out = this.pageContext.getOut();

		try {
			out.write(PropKit.get(key, ""));
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return super.doStartTag();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
