package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;

@SuppressWarnings("serial")
public class ResultItemCopy extends KBaseModel<ResultItemCopy> {

	public static final ResultItemCopy me = new ResultItemCopy();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public List<ResultItemCopy> getResultItemsByTaskAndTaskCardId(String taskId, String taskCardId) {
		String sql = "select * from " + tableName + " where enabled = 0 and tid = ? and tcid = ? order by tid, tcid, ccid,last_modify_time";
		return find(sql, taskId, taskCardId);
	}
}
