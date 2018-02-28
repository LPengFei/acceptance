package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;

@SuppressWarnings("serial")
public class Supervise extends KBaseModel<Supervise> {

	public static final Supervise me = new Supervise();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
