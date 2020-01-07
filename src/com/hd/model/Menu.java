package com.hd.model;

import java.util.HashSet;
import java.util.Set;

/**
 * �ܲ˵���
 * ���ڷ�װ���˵���Ϣ���Ӳ˵���Ϣ
 * @author 94366
 *
 */
public class Menu {
	private String menuId;
	private String menuName;
	private Set<MenuItem> menuItems = new HashSet<MenuItem>();
	public Menu(String menuId, String menuName) {
		super();
		this.menuId = menuId;
		this.menuName = menuName;
	}
	public void addMenuItems(MenuItem ms) {
		menuItems.add(ms);
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Set<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(Set<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	
}
