package com.cnksi.kconf.model;

import java.util.List;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 系统常量Lookup
 */
@SuppressWarnings("serial")
public class Lookup extends BaseModel<Lookup> {

	public static final Lookup me = new Lookup();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	/**
	 * 获取typeid下的key/value
	 * 
	 * @param typeid
	 * @return
	 */
	public List<Record> findByTypeid(String typeid) {
		String sql = "select lkey as name ,lvalue as id from " + tableName + " where ltid= ? and enabled=0 ";
		return Db.find(sql, typeid);
	}
}
