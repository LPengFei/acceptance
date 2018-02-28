package com.cnksi.app.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.cnksi.kcore.web.KWebQueryVO;
import com.jfinal.plugin.activerecord.Page;

import java.util.LinkedList;

@SuppressWarnings("serial")
public class ResultIssueLog extends BaseModel<ResultIssueLog> {

	public static final ResultIssueLog me = new ResultIssueLog();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}

	public Page<ResultIssueLog> paginate(KWebQueryVO queryParam, boolean _b) {
		LinkedList paramValues = new LinkedList();
		Page page = null;

		try {
			page = paginate(queryParam.getPageNumber(),
					queryParam.getPageSize(),
					"select cit.name citname, ri.ciname, ril.* ",
					"from result_issue_log ril, result_item ri, card_item ci, card_item_type cit where ril.enabled = 0 and ri.enabled = 0 and ci.enabled = 0 and cit.enabled = 0 and ril.op_extra = ri.id and ri.ciid = ci.id and ci.citid = cit.id and ril.tid = ? order by ril.op_time asc",
			queryParam.getFilter().get("tid="));
			return page;
		} catch (Exception var5) {
			var5.printStackTrace();
			throw var5;
		}
	}
}
