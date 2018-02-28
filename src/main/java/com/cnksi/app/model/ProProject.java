package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class ProProject extends KBaseModel<ProProject> {

	public static final ProProject me = new ProProject();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
