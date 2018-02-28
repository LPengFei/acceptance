package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

@SuppressWarnings("serial")
public class ProProjectAttachmentImage extends BaseModel<ProProjectAttachmentImage> {

	public static final ProProjectAttachmentImage me = new ProProjectAttachmentImage();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
}
