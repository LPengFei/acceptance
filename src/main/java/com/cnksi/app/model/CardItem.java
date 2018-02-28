package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;

@SuppressWarnings("serial")
public class CardItem extends KBaseModel<CardItem> {

	public static final CardItem me = new CardItem();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public List<CardItem> getCardItemsByCardItemTypeId(String id) {
		String sql = "select * from " + tableName + " where enabled = 0 and citid = ? order by id";
		return find(sql, id);
	}
}
