package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class DeviceFieldValue extends KBaseModel<DeviceFieldValue> {

	public static final DeviceFieldValue me = new DeviceFieldValue();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
