package com.cnksi.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.cnksi.app.model.SysConfig;
import com.cnksi.kcore.jfinal.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException; 
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page; 
import com.cnksi.kcore.web.KWebQueryVO; 
import com.cnksi.kconf.controller.KController; 
import com.cnksi.app.model.ProMilestone; 
/**
 * 
 */
public class ProMilestoneController extends KController {

 	public ProMilestoneController(){
		super(ProMilestone.class);
	} 

	public void index() {
		String projectId = getPara("projectId");

		KWebQueryVO queryParam = super.doIndex();
		queryParam.addFilter("pid", "=", projectId);

		setAttr("projectId", projectId);
		setAttr("checkStages", ProMilestone.me.findMilestonesBy(projectId));

		render("list.jsp");
	}



	public void edit() {

		super.doEdit();

		String projectId = getPara("projectId");
		setAttr("flow", getPara("flow"));


		String idValue = getPara("id", getPara());
		ProMilestone record = null;
		if (idValue != null) {
			record = ProMilestone.me.findById(idValue);
		}else{
			record = new ProMilestone();
			record.set("pid", projectId);
		}
		setAttr("record", record);

		render("form.jsp");
	}


	public void save() {
		ProMilestone record = getModel(ProMilestone.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			ProMilestone proMilestone;
			String[] cols;
			Object[] para;

			if (ProMilestone.isAssocWithDevice(record.getStr("checktype"))) {
				cols = new String[]{"pid", "checktype", "devicetype", "enabled"};
				para = new Object[]{record.<String>get("pid"), record.<String>get("checktype"), record.<String>get("devicetype"), 0};
			} else {
				cols = new String[]{"pid", "checktype", "enabled"};
				para = new Object[]{record.<String>get("pid"), record.<String>get("checktype"), 0};
				record.set("devicetype", null);
			}
			proMilestone = ProMilestone.me.findByPropertity(cols, para, ProMilestone.Logical.AND);
			if (proMilestone == null) {
				record.save();
			} else {
				proMilestone
						.set("devicetype", record.<String>get("devicetype"))
						.set("plantime", record.get("plantime"))
						.update();
			}
		}
		bjuiAjax(200, true);
	}



	public void delete() {
		ProMilestone record = ProMilestone.me.findById(getPara());
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<ProMilestone> p = ProMilestone.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					ProMilestone record = new ProMilestone();
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
