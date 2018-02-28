package com.cnksi.kconf.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.jeecgframework.poi.handler.inter.IExcelDataHandler;

import com.cnksi.kconf.model.Iefield;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;

/**
 * 导入导出数据处理
 * 
 * @author joe
 *
 */
public class KExcelDataHandler implements IExcelDataHandler<Object> {

	String[] needHandlerFields = new String[] {};

	Map<Object, Iefield> exportConfMap = null;

	Map<Object, Iefield> importConfMap = null;

	// 数据缓存
	Map<String, Object> cacheMap = new HashMap<>();

	public KExcelDataHandler(List<Iefield> fields) {

		this.exportConfMap = Iefield.me.toMap(fields, "field_name");
		this.importConfMap = Iefield.me.toMap(fields, "field_alias");

		// 设定需要做数据处理的字段
		List<String> needHandlerFieldsList = new ArrayList<String>();
		fields.forEach(record -> {
			if (StrKit.notBlank(record.getStr("list_sql"))) {
				needHandlerFieldsList.add(record.getStr("field_name"));
				needHandlerFieldsList.add(record.getStr("field_alias"));
			}
		});
		needHandlerFields = needHandlerFieldsList.toArray(needHandlerFields);

		System.out.println(needHandlerFields.length);
	}

	@Override
	public Object exportHandler(Object obj, String name, Object value) {
		Object _value = value;

		Iefield fieldConf = exportConfMap.get(name);
		if (fieldConf != null && StrKit.notBlank(fieldConf.getStr("list_sql"))) {
			Object _val = cacheMap.get(name + "-" + value);
			if (StrKit.notNull(_val)) {
				_value = _val;
			} else {
				List<Object> datas = Db.query(fieldConf.getStr("list_sql"), value);
				if (datas != null && !datas.isEmpty()) {
					_value = datas.get(0);
					cacheMap.put(name + "-" + value, _value);
				}
			}
		}
		return _value;
	}

	@Override
	public String[] getNeedHandlerFields() {
		return needHandlerFields;
	}

	@Override
	public Object importHandler(Object obj, String name, Object value) {
		Object _value = value;

		Iefield fieldConf = importConfMap.get(name);
		if (fieldConf != null && StrKit.notBlank(fieldConf.getStr("list_sql"))) {
			Object _val = cacheMap.get(name + "-" + value);
			if (StrKit.notNull(_val)) {
				_value = _val;
			} else {
				List<Object> datas = Db.query(fieldConf.getStr("list_sql"), value);
				if (datas != null && !datas.isEmpty()) {
					_value = datas.get(0);
					cacheMap.put(name + "-" + value, _value);
				}
			}
		}
		return _value;
	}

	@Override
	public void setNeedHandlerFields(String[] fields) {
		this.needHandlerFields = fields;
	}

	@Override
	public void setMapValue(Map<String, Object> map, String originKey, Object value) {
		// riginKey为excel的表头,通过表头汉字查询出对应的field_name
		Iefield field = importConfMap.get(originKey);
		if (field != null) {
			map.put(field.getStr("field_name"), value);
		} else {
			map.put(originKey, value);
		}
	}

	@Override
	public Hyperlink getHyperlink(CreationHelper creationHelper, Object obj, String name, Object value) {
		return null;
	}

}
