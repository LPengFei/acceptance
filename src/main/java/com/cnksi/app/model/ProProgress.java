package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class ProProgress extends KBaseModel<ProProgress> {

	public static final ProProgress me = new ProProgress();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
