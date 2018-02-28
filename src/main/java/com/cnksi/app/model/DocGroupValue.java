package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class DocGroupValue extends KBaseModel<DocGroupValue> {

	public static final DocGroupValue me = new DocGroupValue();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
