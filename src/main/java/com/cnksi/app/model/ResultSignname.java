package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;

@SuppressWarnings("serial")
public class ResultSignname extends KBaseModel<ResultSignname> {

	public static final ResultSignname me = new ResultSignname();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public List<ResultSignname> getSignnameByCardItemTypeId(String id) {
		String sql = "select * from " + tableName + " where enabled = 0 and citid = ? order by id";
		return find(sql, id);
	}

	public List<ResultSignname> getSignByTaskAndTaskCardId(String taskId, String taskCardId) {
		String sql = "select * from " + tableName + " where enabled = 0 and tid = ? and tcid = ? order by id";
		return find(sql, taskId, taskCardId);
	}
}
