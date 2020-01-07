package com.hd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hd.model.Menu;
import com.hd.model.MenuItem;
import com.hd.model.RoleMenu;
import com.hd.utils.JdbcUtils;


public class MenuDaoImp {
	
	/**
	 * 方法名称：getRoleMenus
	 * 参数类型：String
	 * 参数应传值：登录角色id
	 * 返回类型：List<RoleMenu>集合
	 * 返回应传值：对应角色的菜单集合
	 * 作用：根据登陆者的角色id去数据库进行查询对应的菜单栏，返回一个list集合
	 */
	private List<RoleMenu> getMenu(String roleId) {
		Connection conn = JdbcUtils.getConnection();
		String sql = "select * from view_role_menu where roleid=?";
		PreparedStatement pstmt = null;
		List<RoleMenu> list = new ArrayList();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, roleId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				RoleMenu rm = new RoleMenu();
				rm.setItemId(rs.getString("itemid"));
				rm.setItemName(rs.getString("itemname"));
				rm.setMenuId(rs.getString("menuid"));
				rm.setMenuName(rs.getString("menuname"));
				rm.setRoleId(rs.getString("roleId"));
				rm.setUrlLink(rs.getString("urlLink"));
				rm.setVisible(rs.getString("visible"));
				list.add(rm);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtils.closeConnection(conn);
		}
		return list;
		
	}
	
	/**
	 * 方法名：parseList
	 * 参数类型：List<RoleMenu>对象
	 * 参数应传值：获得的用户角色所有的菜单集合
	 * 返回类型：Map<String,Menu>对象
	 * 返回应传值：键为一个总菜单栏的id，值为总菜单栏对象（包括菜单栏id，菜单栏名以及下面的子菜单集合）
	 * 作用：将上面获取的混乱的菜单集合进行统计整理，将主从菜单分清楚，将从菜单存入主菜单对象的set集合之中，用于之后遍历
	 */
	private Map<String,Menu>  parseList(List<RoleMenu> list){
		//{ps}创建一个空的HashMap
		Map<String,Menu> menuMap = new HashMap<String,Menu>();
		//{a}迭代列表，把里面的数据拆出来
		for (RoleMenu roleMenu : list) {
			//{b}得到menuId
			String menuId = roleMenu.getMenuId();
			//{c}尝试去取Menu出去
			Menu menu = menuMap.get(menuId);
			//{d}若果menu不存在，则创建之，放入Map中去
			if(menu==null) {
				menu = new Menu(menuId,roleMenu.getMenuName());
				//放入menuMap中
				menuMap.put(menuId,menu);
			}
			//{e}取出子菜单的数据
			MenuItem mi = new MenuItem(roleMenu.getItemId(),roleMenu.getItemName(),
					roleMenu.getUrlLink(),roleMenu.getVisible());
			//{f}追加到menu的set集合当中
			menu.addMenuItems(mi);
		}
		return menuMap;
	}
	
	/**
	 * 方法名：getRoleMenuMap
	 * 参数类型：String对象
	 * 参数应传值：登录角色id
	 * 返回类型：Map<String,Menu>对象
	 * 返回应传值：键为一个总菜单栏的id，值为总菜单栏对象（包括菜单栏id，菜单栏名以及下面的子菜单集合）
	 * 作用：负责执行上面获得菜单栏的方法，得到一个包含对应角色的菜单Map，此Map将被用于前端页面的显示
	 */
	public Map<String,Menu> getRoleMenuMap(String roleId){
		List<RoleMenu> roleMenus = getMenu(roleId);
		Map<String,Menu> menu = parseList(roleMenus);
		return menu;
	}
}
