package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

/**
 * 导入导出模型
 */
@SuppressWarnings("serial")
public class Iexport extends BaseModel<Iexport> {

	public static final Iexport me = new Iexport();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}
}
