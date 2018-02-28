package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class ProProjectScal extends KBaseModel<ProProjectScal> {

	public static final ProProjectScal me = new ProProjectScal();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
