package com.cnksi.kconf.model;

import java.util.List;

import com.cnksi.kcore.jfinal.model.BaseModel;
/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Authority extends BaseModel<Authority> {

	public static final Authority me = new Authority();


	@SuppressWarnings("rawtypes") 
 	@Override 
 	  protected Class getCls() {
		return this.getClass();
	}
	
	/**
	 * 查询所有父级权限
	 * 
	 * @return
	 */
	public List<Authority> findParentResouce() {
		String sql = String.format("select * from %s where paid is null and enabled = 0 ", tableName);
		return me.find(sql);
	}

	/**
	 * 查询当前资源的子集权限
	 * 
	 * @return
	 */
	public List<Authority> getChildAuthority() {
		String sql = String.format("select * from %s where paid = ? and enabled = 0 ", tableName);
		return me.find(sql, String.valueOf(getStr(pkName)));
	}
}
