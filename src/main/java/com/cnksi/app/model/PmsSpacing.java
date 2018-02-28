package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class PmsSpacing extends KBaseModel<PmsSpacing> {

	public static final PmsSpacing me = new PmsSpacing();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
