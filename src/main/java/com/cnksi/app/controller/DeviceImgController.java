package com.cnksi.app.controller;

import java.io.IOException; 
import java.util.Map; 
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException; 
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page; 
import com.cnksi.kcore.web.KWebQueryVO; 
import com.cnksi.kconf.controller.KController; 
import com.cnksi.app.model.DeviceImg; 
/**
 * 
 */
public class DeviceImgController extends KController {

 	public DeviceImgController(){
		super(DeviceImg.class);
	} 

	public void index() {

		KWebQueryVO queryParam = super.doIndex();

		setAttr("page", DeviceImg.me.paginate(queryParam));

		render(listJsp);
	}



	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		DeviceImg record = null;
		if (idValue != null) {
			record = DeviceImg.me.findById(idValue);
		}else{
			record = new DeviceImg();

		}
		setAttr("record", record);

		render(formJsp);
	}


	public void save() {
		DeviceImg record = getModel(DeviceImg.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.save();
		} 

		bjuiAjax(200, true);
	}



	public void delete() {
		DeviceImg record = DeviceImg.me.findById(getPara());
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<DeviceImg> p = DeviceImg.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					DeviceImg record = new DeviceImg();
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
