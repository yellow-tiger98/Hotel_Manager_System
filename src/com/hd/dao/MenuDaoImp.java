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
	 * �������ƣ�getRoleMenus
	 * �������ͣ�String
	 * ����Ӧ��ֵ����¼��ɫid
	 * �������ͣ�List<RoleMenu>����
	 * ����Ӧ��ֵ����Ӧ��ɫ�Ĳ˵�����
	 * ���ã����ݵ�½�ߵĽ�ɫidȥ���ݿ���в�ѯ��Ӧ�Ĳ˵���������һ��list����
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
	 * ��������parseList
	 * �������ͣ�List<RoleMenu>����
	 * ����Ӧ��ֵ����õ��û���ɫ���еĲ˵�����
	 * �������ͣ�Map<String,Menu>����
	 * ����Ӧ��ֵ����Ϊһ���ܲ˵�����id��ֵΪ�ܲ˵������󣨰����˵���id���˵������Լ�������Ӳ˵����ϣ�
	 * ���ã��������ȡ�Ļ��ҵĲ˵����Ͻ���ͳ�����������Ӳ˵�����������Ӳ˵��������˵������set����֮�У�����֮�����
	 */
	private Map<String,Menu>  parseList(List<RoleMenu> list){
		//{ps}����һ���յ�HashMap
		Map<String,Menu> menuMap = new HashMap<String,Menu>();
		//{a}�����б�����������ݲ����
		for (RoleMenu roleMenu : list) {
			//{b}�õ�menuId
			String menuId = roleMenu.getMenuId();
			//{c}����ȥȡMenu��ȥ
			Menu menu = menuMap.get(menuId);
			//{d}����menu�����ڣ��򴴽�֮������Map��ȥ
			if(menu==null) {
				menu = new Menu(menuId,roleMenu.getMenuName());
				//����menuMap��
				menuMap.put(menuId,menu);
			}
			//{e}ȡ���Ӳ˵�������
			MenuItem mi = new MenuItem(roleMenu.getItemId(),roleMenu.getItemName(),
					roleMenu.getUrlLink(),roleMenu.getVisible());
			//{f}׷�ӵ�menu��set���ϵ���
			menu.addMenuItems(mi);
		}
		return menuMap;
	}
	
	/**
	 * ��������getRoleMenuMap
	 * �������ͣ�String����
	 * ����Ӧ��ֵ����¼��ɫid
	 * �������ͣ�Map<String,Menu>����
	 * ����Ӧ��ֵ����Ϊһ���ܲ˵�����id��ֵΪ�ܲ˵������󣨰����˵���id���˵������Լ�������Ӳ˵����ϣ�
	 * ���ã�����ִ�������ò˵����ķ������õ�һ��������Ӧ��ɫ�Ĳ˵�Map����Map��������ǰ��ҳ�����ʾ
	 */
	public Map<String,Menu> getRoleMenuMap(String roleId){
		List<RoleMenu> roleMenus = getMenu(roleId);
		Map<String,Menu> menu = parseList(roleMenus);
		return menu;
	}
}
