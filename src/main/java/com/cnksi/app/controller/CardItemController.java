package com.cnksi.app.controller;

import java.io.IOException; 
import java.util.Map;

import com.cnksi.app.model.Card;
import com.cnksi.app.model.CardItemType;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.exception.excel.ExcelImportException; 
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Page; 
import com.cnksi.kcore.web.KWebQueryVO; 
import com.cnksi.kconf.controller.KController; 
import com.cnksi.app.model.CardItem; 
/**
 * 
 */
public class CardItemController extends KController {

 	public CardItemController(){
		super(CardItem.class);
	} 

	public void index() {

		KWebQueryVO queryParam = super.doIndex();

		String citid = getPara("citid");
		queryParam.addFilter("citid", "=", citid);
		setAttr("page", CardItem.me.paginate(queryParam));
		setAttr("citid", citid);

		render("list.jsp");
	}



	public void edit() {

		super.doEdit();

		String idValue = getPara("id", getPara());
		CardItem record;
		String citid;
		if (idValue != null) {
			record = CardItem.me.findById(idValue);
			citid = record.get("citid");
		}else{
			record = new CardItem();
			citid = getPara("citid");
		}
		setAttr("record", record);
		setAttr("cardItemType", CardItemType.me.findById(citid));

		render("form.jsp");
	}


	public void save() {
		CardItem record = getModel(CardItem.class, "record");
		if (record.get("id") != null) {
			record.update();
		} else {
			record.save();
		} 

		bjuiAjax(200, true);
	}



	public void delete() {
		CardItem record = CardItem.me.findById(getPara());
		if (record != null) {
			record.set("enabled", 1).update();
			bjuiAjax(200);
		} else {
			bjuiAjax(300);
		} 

	}



	public void export()  throws IOException {
		KWebQueryVO queryParam = super.doIndex();
		Page<CardItem> p = CardItem.me.paginate(queryParam);
		String xlsid = getPara("xlsid", "-1");
		super.export(xlsid, p.getList());
	}



	public void importxlsed()  {
		String errorFile = "",msg=""; 
		try {
			ExcelImportResult<Map<String, Object>> result = super.importxls(getPara("xlsid"), getFile());
			if (!result.isVerfiyFail()) {
				for (Map<String, Object> map : result.getList()) {
					CardItem record = new CardItem();
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
