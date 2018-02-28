package com.cnksi.kconf.controller.vo;

import com.cnksi.kcore.web.KWebQueryVO;
import com.cnksi.kcore.web.api.KQuery;
import com.cnksi.kcore.web.api.KQueryParam;

/**
 * 数据源查询VO
 */
@KQuery(sql = "SELECT * from k_datasource  ")
public class DatasourceQuery extends KWebQueryVO {

	@KQueryParam(colName = "dsname", op = "like")
	private String dsname;

	public DatasourceQuery() {
		addFilter(" enabled=0 ");
	}

	public String getDsname() {
		return dsname;
	}

	public void setDsname(String dsname) {
		this.dsname = dsname;
	}

}
