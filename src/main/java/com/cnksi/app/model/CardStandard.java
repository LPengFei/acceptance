package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;

@SuppressWarnings("serial")
public class CardStandard extends KBaseModel<CardStandard> {

	public static final CardStandard me = new CardStandard();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public List<CardStandard> getCardStandardsByCardItemId(String id) {
		String sql = "select * from " + tableName + " where enabled = 0 and ciid = ? order by id";
		return find(sql, id);
	}
}
