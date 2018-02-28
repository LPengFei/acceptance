package com.cnksi.kconf.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.cnksi.kconf.controller.vo.ConfModelQuery;
import com.cnksi.kcore.jfinal.model.KModel;
import com.cnksi.kcore.jfinal.model.KModelField;
import com.cnksi.kcore.utils.ApiJson;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.core.Injector;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 系统模型管理Controller
 * 
 * @author admin
 *
 */
public class ModelController extends Controller {

	private static final String tabid = "model-list";

	/**
	 * list页面
	 */
	public void index() {
		ConfModelQuery queryParam = Injector.injectBean(ConfModelQuery.class, null, getRequest(), false);
		setAttr("page", KModel.me.paginate(queryParam));
		keepPara();
		render("kmodel.jsp");
	}

	/**
	 * 新增修改页面
	 */
	public void edit() {
		String idValue = getPara("mid", "");
		KModel model = null;
		if (StrKit.notBlank(idValue)) {
			model = KModel.me.findById(idValue);
		} else {
			model = new KModel();
		}
		setAttr("record", model);

		render("kmodel_form.jsp");
	}

	/**
	 * 保存/更新
	 */
	public void save() {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		KModel model = getModel(KModel.class, "record");
		if (model.get("mid") != null) {
			model.update();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "更新成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "model-list");
		} else {
			model.save();
			resultMap.put("statusCode", "200");
			resultMap.put("message", "保存成功");
			resultMap.put("closeCurrent", true);
			resultMap.put("tabid", "model-list");
		}
		renderJson(resultMap);

	}

	/**
	 * 删除
	 */
	@Before(Tx.class)
	public void delete() {
		KModel model = KModel.me.findById(getPara("mid"));
		if (model != null) {
			model.delete();
			// 删除关联字段配置
			Db.deleteById(KModelField.me.getTableName(), model.getPkName(), model.getPkVal().toString());

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

	/**
	 * 复制一个model
	 */
	public void copy() {
		String id = getPara();
		Objects.requireNonNull("id", "id is null");

		boolean result = KModel.me.copyKModelAndFields(id);
		if (result)
			renderJson(ApiJson.success(false, tabid, ""));
		else
			renderJson(ApiJson.error(false, tabid, ""));
	}
}
