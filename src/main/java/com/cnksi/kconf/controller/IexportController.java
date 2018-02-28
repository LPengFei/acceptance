package com.cnksi.kconf.controller;

import java.util.HashMap;

import com.cnksi.kconf.controller.vo.ConfIefieldQuery;
import com.cnksi.kconf.controller.vo.ConfIexportQuery;
import com.cnksi.kconf.model.Iefield;
import com.cnksi.kconf.model.Iexport;
import com.cnksi.kcore.jfinal.model.KModel;
import com.jfinal.core.Injector;

/**
 * Excel导入管理
 */
public class IexportController extends KController {
	public IexportController() {
		super(Iexport.class);
	}

	public void index() {
		KModel m = KModel.me.findByTableName("k_iexport");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}
		ConfIexportQuery queryParam = Injector.injectBean(ConfIexportQuery.class, null, getRequest(), false);

		setAttr("page", Iexport.me.paginate(queryParam));
		setAttr("query", queryParam);
		render("iexport.jsp");
	}

	public void edit() {
		KModel m = KModel.me.findByTableName("k_iexport");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}
		String idValue = getPara("id", null);

		Iexport record = null;
		if (idValue != null) {
			record = Iexport.me.findById(idValue);
		} else {
			record = new Iexport();
		}

		setAttr("record", record);
		render("iexport_form.jsp");
	}

	public void save() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Iexport record = getModel(Iexport.class, "record");
		if (record.get("ieid") != null) {
			record.update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "iexport-list");
		} else {
			record.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "iexport-list");
		}

		renderJson(resultMap);
	}

	public void delete() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Iexport record = Iexport.me.findById(getPara("id"));
		if (record != null) {
			record.set("enabled", 1).update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "删除成功");

			resultMap.put("closeCurrent", false);
			renderJson(resultMap);
		} else {
			resultMap.put("statusCode", "300");
			resultMap.put("message", "删除失败");
			resultMap.put("closeCurrent", false);
			resultMap.put("tabid", "");
			renderJson(resultMap);
		}

	}

	public void fields() {
		KModel m = KModel.me.findByTableName("k_iefield");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}

		ConfIefieldQuery queryParam = Injector.injectBean(ConfIefieldQuery.class, null, getRequest(), false);
		setAttr("iexport", Iexport.me.findById(queryParam.getIeid()));
		setAttr("page", Iefield.me.paginate(queryParam));
		setAttr("query", queryParam);
		render("iefield.jsp");
	}

	public void fieldEdit() {
		KModel m = KModel.me.findByTableName("k_iefield");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}
		String idValue = getPara("id", null);

		Iefield record = null;
		if (idValue != null) {
			record = Iefield.me.findById(idValue);
		} else {
			record = new Iefield();
			record.set("ieid", getParaToInt("ieid"));
		}
		setAttr("iexport", Iexport.me.findById(record.getInt("ieid")));
		setAttr("record", record);
		render("iefield_form.jsp");
	}

	public void fieldSave() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Iefield record = getModel(Iefield.class, "record");
		if (record.get("iefid") != null) {
			record.update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "fields-list");
		} else {
			record.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "fields-list");
		}

		renderJson(resultMap);
	}

	public void fieldDelete() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Iefield record = Iefield.me.findById(getPara("id"));
		if (record != null) {
			if (record.getInt("enabled") == 0) {
				record.set("enabled", 1).update();
			} else {
				record.set("enabled", 0).update();
			}
			resultMap.put("statusCode", "200");
			resultMap.put("message", "操作成功");
			resultMap.put("closeCurrent", false);
			renderJson(resultMap);
		} else {
			resultMap.put("statusCode", "300");
			resultMap.put("message", "操作失败");
			resultMap.put("closeCurrent", false);
			resultMap.put("tabid", "");
			renderJson(resultMap);
		}

	}
}
