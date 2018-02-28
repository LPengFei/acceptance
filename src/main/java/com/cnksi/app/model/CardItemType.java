package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;

@SuppressWarnings("serial")
public class CardItemType extends KBaseModel<CardItemType> {

	public static final CardItemType me = new CardItemType();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public List<CardItemType> getCardItemTypeByCardId(String key) {
		String sql = "select * from " + tableName + " where enabled = 0 and cid = ? order by id";
		return find(sql, key);
	}
}
