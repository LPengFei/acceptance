package com.cnksi.app.controller;

import java.io.IOException; 
import java.util.Map; 
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException; 
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page; 
import com.cnksi.kcore.web.KWebQueryVO; 
import com.cnksi.kconf.controller.KController; 
import com.cnksi.app.model.ResultIssueLog; 
/**
 * 
 */
public class ResultIssueLogController extends KController {

 	public ResultIssueLogController(){
		super(ResultIssueLog.class);
	} 

	public void index() {

		KWebQueryVO queryParam = super.doIndex();
		queryParam.setOrderField("op_time");
		queryParam.setOrderDirection("desc");
		if (getPara("riid") != null) {
			queryParam.addFilter("riid", "=", getPara("riid"));
			setAttr("riid", getPara("riid"));
			setAttr("page", ResultIssueLog.me.paginate(queryParam));
		} else if (getPara("tid") != null) {
			queryParam.addFilter("tid", "=", getPara("tid"));
			setAttr("tid", getPara("tid"));
			setAttr("isTask", true);
			setAttr("page", ResultIssueLog.me.paginate(queryParam, true));
		}
		render("list.jsp");
	}



	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		ResultIssueLog record = null;
		if (idValue != null) {
			record = ResultIssueLog.me.findById(idValue);
		}else{
			record = new ResultIssueLog();

		}
		setAttr("record", record);

		render(formJsp);
	}


	public void save() {
		ResultIssueLog record = getModel(ResultIssueLog.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.save();
		} 

		bjuiAjax(200, true);
	}



	public void delete() {
		ResultIssueLog record = ResultIssueLog.me.findById(getPara());
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<ResultIssueLog> p = ResultIssueLog.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					ResultIssueLog record = new ResultIssueLog();
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
