package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
/**
 * 导出/导入 字段配置
 */
@SuppressWarnings("serial")
public class Iefield extends BaseModel<Iefield> {

	public static final Iefield me = new Iefield();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
