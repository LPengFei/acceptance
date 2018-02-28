package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class Datasource extends BaseModel<Datasource> {

	public static final Datasource me = new Datasource();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
