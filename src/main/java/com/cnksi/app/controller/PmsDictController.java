package com.cnksi.app.controller;

import java.io.IOException; 
import java.util.Map; 
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException; 
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page; 
import com.cnksi.kcore.web.KWebQueryVO; 
import com.cnksi.kconf.controller.KController; 
import com.cnksi.app.model.PmsDict; 
/**
 * 
 */
public class PmsDictController extends KController {

 	public PmsDictController(){
		super(PmsDict.class);
	} 

	public void index() {

		KWebQueryVO queryParam = super.doIndex();

		setAttr("page", PmsDict.me.paginate(queryParam));

		render(listJsp);
	}



	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		PmsDict record = null;
		if (idValue != null) {
			record = PmsDict.me.findById(idValue);
		}else{
			record = new PmsDict();

		}
		setAttr("record", record);

		render(formJsp);
	}


	public void save() {
		PmsDict record = getModel(PmsDict.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.save();
		} 

		bjuiAjax(200, true);
	}



	public void delete() {
		PmsDict record = PmsDict.me.findById(getPara());
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<PmsDict> p = PmsDict.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					PmsDict record = new PmsDict();
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
