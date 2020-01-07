package com.hd.model;

/**
 * 角色全部菜单信息类
 * 作用：用于存取从数据库中获取的全部菜单信息
 * @author 94366
 *
 */
public class RoleMenu {
	private	String roleId;
	private	String itemId;
	private String itemName;
	private String urlLink;
	private String visible;
	private String menuId;
	private String menuName;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUrlLink() {
		return urlLink;
	}
	public void setUrlLink(String urlLink) {
		this.urlLink = urlLink;
	}
	public String getVisible() {
		return visible;
	}
	public void setVisible(String visible) {
		this.visible = visible;
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
	
	@Override
	public String toString() {
		return "RoleMenu [roleId=" + roleId + ", itemId=" + itemId + ", itemName=" + itemName + ", urlLink=" + urlLink
				+ ", visible=" + visible + ", menuId=" + menuId + ", menuName=" + menuName + "]";
	}
	
	
}
