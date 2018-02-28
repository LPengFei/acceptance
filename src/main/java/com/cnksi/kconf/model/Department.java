package com.cnksi.kconf.model;

import java.util.ArrayList;
import java.util.List;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.cnksi.kcore.utils.KStrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

/**
 * 部门
 * 
 * @author joe
 *
 */
public class Department extends BaseModel<Department> {

	private static final long serialVersionUID = 1L;

	public static final Department me = new Department();

	@Override
	protected Class<?> getCls() {
		return this.getClass();
	}

	
	/**
	 * 查询所有的部门(部门树)
	 * 
	 * @return
	 */
	public List<Record> findAllDeptTree() {
		String sql = "select did as id, pid as pId, dname as name from "+tableName+" d order by pid,did";
		return Db.find(sql);
	}
	
	/**
	 * 查询所有的部门
	 * @return
	 */
	public List<Department> findAllDept(){
		String sql = "SELECT did, pid, dname FROM " + tableName + " d where 1=1 and enabled = 0 ORDER BY pid,did";
		return find(sql);
	}
	
	
	/**
	 * 根据父级部门id查询所有子级部门
	 * @param parentId
	 * @return
	 */
	public List<Record> selectChidrenByParentId(String parentId){
		List<Record> records = new ArrayList<Record>();
		Department dept = Department.me.findById(parentId);
		Record root = new Record();
		root.set("id",dept.get("did"));
		root.set("name",dept.get("dname"));
		root.set("pId",dept.get("pid"));
		//查询所有的单位信息
		List<Department> list = Department.me.findAllDept();
		List<Record> children = getChilds(parentId, list);
		if(children.size() > 0){
			root.set("children",children);
		}
		records.add(root);
		return records;
	}
	
	/**
	 * 根据deptid查询下级所有单位
	 * @param deptId
	 * @return
	 */
	private List<Record> getChilds(String deptId,List<Department> allDepts){
		List<Department> list = findByParentId(deptId, allDepts);
		List<Record> result = new ArrayList<Record>();

		for (Department dept : list){
			Record tree = new Record();
			tree.set("id",dept.get("did"));
			tree.set("name",dept.get("dname"));
			tree.set("pId",dept.get("pid"));
			List<Record> children = getChilds(KStrKit.toStr(dept.get("did")),allDepts);
			if(children.size() > 0){
				tree.set("children",children);
			}
			result.add(tree);
		}
		return result;
	}
	
	public List<Department> findByParentId(String deptId, List<Department> allDepts){
		List<Department> result = new ArrayList<Department>();
		for (Department dept : allDepts){
			if (KStrKit.toStr(dept.get("pid")).equals(deptId)){
				result.add(dept);
			}
		}
		return result;
	}
}
