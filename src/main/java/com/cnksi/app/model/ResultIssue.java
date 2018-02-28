package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;

import java.util.List;
import com.jfinal.plugin.activerecord.Db;
@SuppressWarnings("serial")
public class ResultIssue extends KBaseModel<ResultIssue> {

	public static final ResultIssue me = new ResultIssue();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public Long getProblemNumbersByCardItemId(String id) {
		return Db.queryLong("select count(*) from " + tableName + " where enabled = 0 and ciid = ? and status = 'normal' order by id", id);
	}
	public List<ResultIssue> getProblemsByCardItemId(String id) {
		String sql = "select * from " + tableName + " where enabled = 0 and ciid = ? order by id";
		return find(sql, id);
	}
	public List<ResultIssue> getResultIssueByTaskId(String id) {
		String sql = "select * from " + tableName + " where enabled = 0 and tid = ? order by id";
		return find(sql, id);
	}
	public List<ResultIssue> getIssueByTaskAndTaskCardId(String taskId, String taskCardId) {
		String sql = "select * from " + tableName + " where enabled = 0 and tid = ? and tcid = ? order by id";
		return find(sql, taskId, taskCardId);
	}
	public static List<ResultIssue> getRemainIssuesByProjectId(String id) {
		String sql = "select ri.* from result_issue ri, task t where t.enabled = 0 and ri.enabled = 0 and ri.tid = t.id and ri.isremain = 1 and t.pid = ?";
		return me.find(sql, id);
	}

	public static List<ResultIssue> getIssuesByProjectId(String id) {
		String sql = "SELECT dt.name AS _devicename, ri.* FROM result_issue ri LEFT JOIN device_type dt ON dt.id = ri.dtid AND dt.enabled = 0 WHERE ri.enabled = 0  AND ri.pid = ?";
		return me.find(sql, id);
	}
	public static List<ResultIssue> getIssuesImportantByProjectId(String id) {
		String sql = "select * from result_issue ri where ri.enabled = 0 and level = 4 and ri.pid = ?";
		return me.find(sql, id);
	}
}
