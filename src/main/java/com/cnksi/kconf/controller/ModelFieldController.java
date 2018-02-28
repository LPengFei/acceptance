package com.cnksi.kconf.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cnksi.kconf.controller.vo.ConfModelFieldQuery;
import com.cnksi.kconf.model.Datasource;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.kcore.utils.WebUtils;
import com.jfinal.core.Controller;
import com.jfinal.core.Injector;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.TableMapping;

/**
 * 系统模型字段管理Controller
 * 
 * @author admin
 *
 */
public class ModelFieldController extends Controller {
	/**
	 * list页面
	 */
	public void index() {
		ConfModelFieldQuery queryParam = Injector.injectBean(ConfModelFieldQuery.class, null, getRequest(), false);
		if (StrKit.isBlank(queryParam.getOrderField())) {
			queryParam.setOrderField("list_sort");
			queryParam.setOrderDirection("asc");
		}

		setAttr("page", KModelField.me.paginate(queryParam));
		keepPara();
		render("kmodelfield.jsp");
	}

	/**
	 * 新增修改页面
	 */
	public void edit() {
		String idValue = getPara("mfid", "");
		String mid = getPara("mid", "");
		KModelField model = null;
		if (StrKit.notBlank(idValue)) {
			model = KModelField.me.findById(idValue);
		} else {
			model = new KModelField();
			model.set("mid", mid);
		}

		if (StrKit.isBlank(model.getStr("settings")))
			model.set("settings", "null");

		// 数据源配置
		List<Datasource> dsList = Datasource.me.findListByPropertity("enabled", "0");
		setAttr("dsList", dsList);

		setAttr("record", model);

		render("kmodelfield_form.jsp");
	}

	/**
	 * 保存/更新
	 */
	public void save() {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		KModelField model = getModel(KModelField.class, "f");

		Map<String, String> listMap = WebUtils.getParametersStartingWith2(getRequest(), "list", false);
		Map<String, String> formMap = WebUtils.getParametersStartingWith2(getRequest(), "form", false);
		Map<String, String> dataMap = WebUtils.getParametersStartingWith2(getRequest(), "data", false);
		model.set("settings", "{\"formview\":" + JsonKit.toJson(formMap) + ",\"listview\":" + JsonKit.toJson(listMap) + ",\"dataconfig\":" + JsonKit.toJson(dataMap) + "}");

		if (model.get("mfid") != null) {
			model.update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "fields-list");
		} else {
			model.save();

			// 新增字段并实时刷新model
			if (StrKit.notBlank(model.getStr("type"))) {
				KModel m = KModel.me.findById(model.getInt("mid"));
				String alterSql = String.format(" alter table %s add column %s %s comment '%s' ", m.getStr("mtable"), model.getStr("field_name"), model.getStr("type"), model.getStr("remark"));
				Db.update(alterSql);
				TableMapping.me().refreshTable(m.getStr("mtable"));
			}
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "fields-list");
		}
		renderJson(resultMap);

	}

	/**
	 * 更新列表排序
	 */
	public void list_sort_update() {
		String id = getPara("mfid");
		String list_sort = getPara("value");
		if (StrKit.notBlank(id)) {
			KModelField confModelField = KModelField.me.findById(id);
			confModelField.set("list_sort", list_sort);
			confModelField.update();
			confModelField.put("mname", KModel.me.findById(confModelField.get("mid").toString()).get("mname"));
			renderJson(confModelField);
		}
	}

	/**
	 * 删除
	 */
	public void delete() {
		KModelField model = KModelField.me.findById(getPara("mfid"));
		if (model != null) {
			model.set("enabled", 1).update();
			Map<String, Object> apiJson = new HashMap<String, Object>();
			apiJson.put("statusCode", 200);
			apiJson.put("message", "删除成功");
			apiJson.put("closeCurrent", false);
			renderJson(apiJson);
		} else {
			Map<String, Object> apiJson = new HashMap<String, Object>();
			apiJson.put("statusCode", 200);
			apiJson.put("message", "删除失败");
			apiJson.put("closeCurrent", false);
			apiJson.put("tabid", "");
			renderJson(apiJson);
		}
	}
}
