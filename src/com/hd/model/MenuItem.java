package com.hd.model;

/**
 * 子菜单类
 * 作用：用于封装子菜单的相关信息
 * @author 94366
 *
 */
public class MenuItem {
	private String itemId;  //子菜单id
	private String itemName; //子菜单名称
	private String urlLink; //映射地址
	private String visible;  //可见性
	
	public MenuItem(String itemId, String itemName, String urlLink, String visible) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.urlLink = urlLink;
		this.visible = visible;
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

	@Override
	public String toString() {
		return "MenuItem [itemId=" + itemId + ", itemName=" + itemName + ", urlLink=" + urlLink + ", visible=" + visible
				+ "]";
	}
	
	
}
