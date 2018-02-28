package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;

/**
 * 系统模型查询VO
 * 
 * @author joe
 *
 */
@KQuery(sql = "SELECT * FROM k_model_field ")
public class ConfModelFieldQuery extends KWebQueryVO {

	/**
	 * 字段名称
	 */
	@KQueryParam(colName = "field_name", op = "like")
	private String fieldname;

	/**
	 * 字段别名
	 */
	@KQueryParam(colName = "field_alias", op = "like")
	private String fieldalias;

	/**
	 * 模型id
	 */
	@KQueryParam(colName = "mid", op = "=")
	private String mid;

	public ConfModelFieldQuery() {
		addFilter("enabled", "=", "0");
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldalias() {
		return fieldalias;
	}

	public void setFieldalias(String fieldalias) {
		this.fieldalias = fieldalias;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

}
