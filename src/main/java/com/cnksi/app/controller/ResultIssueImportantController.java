package com.cnksi.app.controller;

import java.io.IOException; 
import java.util.Map;

import com.cnksi.app.model.ResultIssue;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException; 
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page; 
import com.cnksi.kcore.web.KWebQueryVO; 
import com.cnksi.kconf.controller.KController; 
import com.cnksi.app.model.ResultIssueImportant; 
/**
 * 
 */
public class ResultIssueImportantController extends KController {

 	public ResultIssueImportantController(){
		super(ResultIssueImportant.class);
	} 

	public void index() {

		KWebQueryVO queryParam = super.doIndex();

		String taskId = getPara("taskId");
		if (taskId != null) {
			queryParam.addFilter("tid", "=", taskId);
			setAttr("taskId", taskId);
		}

		setAttr("page", ResultIssueImportant.me.paginate(queryParam));

		render("listByTaskId.jsp");
	}

	public void viewByTaskId() {

		KWebQueryVO queryParam = super.doIndex();
		String taskId = getPara(0);
		setAttr("taskId", taskId);

		queryParam.addFilter("tid", "=", taskId);

		setAttr("page", ResultIssueImportant.me.paginate(queryParam));


		render("listByTaskId.jsp");
	}



	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		ResultIssueImportant record = null;
		if (idValue != null) {
			record = ResultIssueImportant.me.findById(idValue);
		}else{
			record = new ResultIssueImportant();

		}
		setAttr("record", record);

		render(formJsp);
	}


	public void save() {
		ResultIssueImportant record = getModel(ResultIssueImportant.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.save();
		} 

		bjuiAjax(200, true);
	}



	public void delete() {
		ResultIssueImportant record = ResultIssueImportant.me.findById(getPara());
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<ResultIssueImportant> p = ResultIssueImportant.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					ResultIssueImportant record = new ResultIssueImportant();
					record.put(map);
					record.save();
				}
			} else {
				errorFile = result.getSaveFile().getName();
			}
		} catch (ExcelImportException e) {
			msg = "导入错误：" + e.getMessage();
		}
		bjuiAjax(StrKit.notBlank(msg) ? 300 : 200).put("errorFile", errorFile);

	}


}
