package com.cnksi.kconf.model;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.cnksi.kcore.jfinal.model.BaseModel;
import com.cnksi.kcore.utils.CollectionKit;
import com.google.common.base.Objects;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Menu extends BaseModel<Menu> {

	public static final Menu me = new Menu();

	@SuppressWarnings("rawtypes")
	@Override
	protected Class getCls() {
		return this.getClass();
	}

	/**
	 * 根据RoleID查询用户的菜单
	 * 
	 * @param roleid
	 * @return
	 */
	public List<Menu> findByRole(String roleid) {
		System.out.println("roleid: "+roleid);
		String sql = "select m.* from k_menu m left join k_role_menu rm on m.menuid = rm.menuid where rm.enabled = 0 and m.enabled = 0 and rm.rid = ? order by m.pmenuid,m.ord";
		return find(sql, roleid);
	}
	
	/**
	 * 查询根菜单及其子菜单
	 * @param @param roleid
	 * @return List<Menu> 返回根菜单，并且每个跟菜单包含子菜单menus
	 * @throws 
	 */
	public List<Menu> findMenuAndChidrenByRole(String roleid){
		//获取角色配置的菜单
		List<Menu> menus = findByRole(roleid);
		if(CollectionKit.empty(menus)) return menus;
		
		//查询根菜单
		List<Menu> parentMenus = findParentMenu();
		
		//将角色的菜单设置到对应根菜单
		Iterator<Menu> iterator = parentMenus.iterator();
		while (iterator.hasNext()) {
			Menu parent = iterator.next();
			//获取子菜单
			List<Menu> childs = menus.stream().filter(mchild -> Objects.equal(parent.getPkVal(), mchild.get("pmenuid"))).collect(Collectors.toList());
			
			//如果子菜单为空，父级菜单不显示
			if(CollectionKit.notEmpty(childs))
				parent.put("menus", childs);
			else
				iterator.remove();
		}
		
		return parentMenus;
	}
	
	
	/**
	 * 查询所有父级菜单
	 * 
	 * @return
	 */
	public List<Menu> findParentMenu() {
		String sql = String.format("select * from %s where pmenuid is null and enabled = 0 ", tableName);
		return me.find(sql);
	}

	/**
	 * 查询当前资源的子集菜单
	 * 
	 * @return
	 */
	public List<Menu> getChildMenu() {
		String sql = String.format("select * from %s where %s = ? and enabled = 0 ", tableName, "pmenuid");
		return me.find(sql, String.valueOf(getStr(pkName)));
	}
	
	/**
	 * 根据RoleID查询用户的菜单
	 * 
	 * @param roleid
	 * @return
	 */
	public List<Menu> findByRoleAndPMenuid(String roleid, String pmenuid) {
		String sql = "select m.* from k_menu m left join k_role_menu rm on m.menuid = rm.menuid where rm.enabled = 0 and m.enabled = 0 and rm.rid = ? and m.pmenuid = ? order by m.pmenuid,m.ord";
		return find(sql, roleid, pmenuid);
	}
	
}
