package com.cnksi.kconf.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

import com.cnksi.kconf.model.Iefield;
import com.jfinal.kit.StrKit;

public class KVerifyHanlder implements IExcelVerifyHandler<Object> {

	String[] needHandlerFields = new String[] {};

	Map<Object, Iefield> confMap = null;

	// 数据缓存
	Map<String, Object> cacheMap = new HashMap<>();

	public KVerifyHanlder(List<Iefield> fields) {

		this.confMap = Iefield.me.toMap(fields, "field_name");

		// 设定需要做数据处理的字段
		List<String> needHandlerFieldsList = new ArrayList<String>();
		fields.forEach(record -> {
			if (StrKit.notBlank(record.getStr("list_sql"))) {
				needHandlerFieldsList.add(record.getStr("field_name"));
			}
		});
		needHandlerFields = needHandlerFieldsList.toArray(needHandlerFields);

		System.out.println(needHandlerFields.length);
	}

	@Override
	public ExcelVerifyHanlderResult verifyHandler(Object obj) {

		ExcelVerifyHanlderResult result = new ExcelVerifyHanlderResult(true);
		boolean success = true;
		StringBuilder sb = new StringBuilder(256);
		if (obj instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> _objMap = (Map<String, Object>) obj;
			for (Entry<String, Object> entry : _objMap.entrySet()) {
				Iefield field = confMap.get(entry.getKey());
				if (field != null && 1 == field.getInt("allow_blank")) {
					if (entry.getValue() == null || StrKit.isBlank(String.valueOf(entry.getValue()))) {
						success = false;
						sb.append(field.getStr("field_alias") + " 字段为必填字段").append("    ");
					}
				}
			}
		}
		result.setSuccess(success);
		result.setMsg(sb.toString());
		return result;
	}

}
