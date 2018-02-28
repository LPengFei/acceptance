package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;

@SuppressWarnings("serial")
public class CardCopy extends KBaseModel<CardCopy> {

	public static final CardCopy me = new CardCopy();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public List<CardCopy> getCardCopysByCardItemId(String id) {
		String sql = "select * from " + tableName + " where enabled = 0 and ciid = ? order by id";
		return find(sql, id);
	}
}
