package com.cnksi.kconf.model;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.cnksi.kcore.utils.KStrKit;
import com.jfinal.plugin.activerecord.Db;

/**
 * 用户
 * 
 * @author joe
 *
 */
public class User extends BaseModel<User> {

	private static final long serialVersionUID = 1L;

	public static final User me = new User();

	/**
	 * 获取当前用户所在部门
	 * 
	 * @return Department
	 */
	public Department getDepartment() {
		return Department.me.findById(getStr("did"));
	}

	@Override
	protected Class<?> getCls() {
		return this.getClass();
	}
	
	public String getUserRoleId(){
		Object result = Db.queryFirst("select ur.rid from k_user_role ur where enabled = 0 and ur.uid  = ?", KStrKit.toStr(get("uid")));
		return KStrKit.toStr(result);
	}

}
