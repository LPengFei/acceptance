package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;

@SuppressWarnings("serial")
public class ResultIssueImportant extends KBaseModel<ResultIssueImportant> {

	public static final ResultIssueImportant me = new ResultIssueImportant();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public List<ResultIssueImportant> getResultIssueImportantByTaskId(String id) {
		String sql = "select * from " + tableName + " where enabled = 0 and tid = ? order by id";
		return find(sql, id);
	}
	public List<ResultIssueImportant> getImportantIssueByTaskAndTaskCardId(String taskId, String taskCardId) {
		String sql = "select * from " + tableName + " where enabled = 0 and tid = ? and tcid = ? order by id";
		return find(sql, taskId, taskCardId);
	}

	public static List<ResultIssueImportant> getAllByProjectId(String id) {
		String sql = "select rii.* from result_issue_important rii, result_issue ri where rii.enabled = 0 and ri.enabled = 0 and rii.riid = ri.id and ri.pid = ?";
		return me.find(sql, id);
	}
}
