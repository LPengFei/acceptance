package com.cnksi.kconf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.cnksi.kconf.controller.vo.LookupQuery;
import com.cnksi.kconf.controller.vo.LookupTypeQuery;
import com.cnksi.kconf.model.Datasource;
import com.cnksi.kconf.model.Lookup;
import com.cnksi.kconf.model.LookupType;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.jfinal.core.Controller;
import com.jfinal.core.Injector;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Record;

/**
 * 常量配置
 */
public class LookupController extends Controller {
	public void index() {
		KModel m = KModel.me.findByTableName("k_lookup");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}
		LookupQuery queryParam = Injector.injectBean(LookupQuery.class, null, getRequest(), false);

		setAttr("page", Lookup.me.paginate(queryParam));
		setAttr("query", queryParam);
		render("lookup.jsp");
	}

	public void edit() {
		KModel m = KModel.me.findByTableName("k_lookup");
		if (m != null) {
			setAttr("model", m);
			if (m != null) {
				setAttr("model", m);
				List<KModelField> fields = m.getField();
				fields.forEach(f -> f.set("settings", f.getSettings()));
				setAttr("fields", fields);
			}
		}

		String idValue = getPara("id", null);

		Lookup record = null;
		if (idValue != null) {
			record = Lookup.me.findById(idValue);
		} else {
			record = new Lookup();
			record.set("ltid", getPara("ltid"));
		}

		setAttr("record", record);
		render("lookup_form.jsp");
	}

	public void save() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Lookup record = getModel(Lookup.class, "record");
		if (record.get("lkid") != null) {
			record.update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");

			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "lookup-list");
		} else {
			// record.set(record.getPkName(), UUID.randomUUID().toString());
			record.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "lookup-list");
		}

		renderJson(resultMap);
	}

	public void delete() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Lookup record = Lookup.me.findById(getPara("id"));
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

	/**
	 * 获取typeid下的kv
	 */
	public void json() {
		String typeid = getPara("typeid");
		List<Record> lookups = Lookup.me.findByTypeid(typeid);
		if ( getParaToBoolean("isFilter", false) ) {
			lookups.add(0, new Record().set("name", "无").set("id", ""));
		}
		renderJson(lookups);
	}

	/**
	 * 获取所有Lookup类型
	 */
	public void types() {
		KModel m = KModel.me.findByTableName("k_lookup_type");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}
		LookupTypeQuery queryParam = Injector.injectBean(LookupTypeQuery.class, null, getRequest(), false);

		setAttr("page", LookupType.me.paginate(queryParam));
		setAttr("query", queryParam);
		render("lookuptype.jsp");
	}

	/**
	 * 编辑新增类型
	 */
	public void typeEdit() {
		KModel m = KModel.me.findByTableName("k_lookup_type");
		if (m != null) {
			setAttr("model", m);
			setAttr("fields", m.getField());
		}
		String idValue = getPara("id", null);

		LookupType record = null;
		if (idValue != null) {
			record = LookupType.me.findById(idValue);
		} else {
			record = new LookupType();
		}

		setAttr("record", record);
		render("lookuptype_form.jsp");
	}

	/**
	 * 保存、更新类型
	 */
	public void typeSave() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		LookupType record = getModel(LookupType.class, "record");
		if (record.getStr("ltid") != null) {
			LookupType _type = LookupType.me.findById(record.getStr("ltid"));
			if (_type != null) {
				record.update();
			} else {

				Datasource ds = new Datasource();
				ds.set("dsname", record.getStr("tname"));
				ds.set("dataurl", "/kconf/lookup/json?typeid=" + record.getStr("ltid"));
				ds.save();
				record.save();
			}
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");

			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "lktype-list");
		} else {
			record.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "lktype-list");
		}

		renderJson(resultMap);
	}

	/**
	 * 删除类型
	 */
	public void typeDelete() {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		LookupType record = LookupType.me.findById(getPara("id"));
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

	/**
	 * 类型JSON数据
	 */
	public void typeJson() {
		List<Record> types = LookupType.me.findJson();
		renderJson(types);
	}
}
