package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;

@KQuery(sql = "SELECT * from k_iefield  ")
public class ConfIefieldQuery extends KWebQueryVO {

	@KQueryParam(colName = "ieid", op = "=")
	private String ieid;

	/**
	 * 字段名称
	 */
	@KQueryParam(colName = "field_name", op = " like")
	private String name;

	/**
	 * 字段别名
	 */
	@KQueryParam(colName = "field_alias", op = " like ")
	private String alias;

	public ConfIefieldQuery() {
		//addFilter(" enabled=0 ");
	}

	public String getIeid() {
		return ieid;
	}

	public void setIeid(String ieid) {
		this.ieid = ieid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
