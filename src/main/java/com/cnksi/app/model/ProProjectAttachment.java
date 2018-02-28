package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class ProProjectAttachment extends KBaseModel<ProProjectAttachment> {

	public static final ProProjectAttachment me = new ProProjectAttachment();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
