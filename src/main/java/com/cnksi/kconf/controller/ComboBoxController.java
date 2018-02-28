package com.cnksi.kconf.controller;

import java.util.ArrayList;
import java.util.List;

import com.cnksi.kconf.model.Department;
import com.cnksi.kconf.model.Lookup;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

/**
 * combobox数据源 Created by Joey on 2016/9/18.
 */
public class ComboBoxController extends Controller {

	public void lookup() {
		StringBuilder sb = new StringBuilder();
		List<Object> params = new ArrayList<>();
		sb.append("select  lvalue as id , lkey as text from k_lookup where   enabled= 0 ");
		String type = getPara("type");
		if (StrKit.notBlank(type)) {
			sb.append("and ltid = ? ");
			params.add(type);
		}
		renderJson(Lookup.me.find(sb.toString(), params.toArray()));
	}

	

	/**
	 * 生成部门树
	 * 
	 * @Title: deptTree
	 */
	public void deptTree() {
		String pid = getPara(0, "1");
		// TODO 改为登录人员部门
		List<Record> deptTreeList = Department.me.findAllDeptTree();
		renderJson(deptTreeList);
	}
}
