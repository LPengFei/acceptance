package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;

@SuppressWarnings("serial")
public class ResultItem extends KBaseModel<ResultItem> {

	public static final ResultItem me = new ResultItem();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public List<ResultItem> getResultItemsByCardItemId(String id) {
		String sql = "select * from " + tableName + " where enabled = 0 and ciid = ? order by id";
		return find(sql, id);
	}

	public List<ResultItem> getResultItemsByTaskAndTaskCardId(String taskId, String taskCardId) {
		String sql = "select * from " + tableName + " where enabled = 0 and tid = ? and tcid = ? order by tid, tcid, ciid";
		return find(sql, taskId, taskCardId);
	}
}
