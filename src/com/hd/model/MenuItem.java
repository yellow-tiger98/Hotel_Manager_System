package com.hd.model;

/**
 * �Ӳ˵���
 * ���ã����ڷ�װ�Ӳ˵��������Ϣ
 * @author 94366
 *
 */
public class MenuItem {
	private String itemId;  //�Ӳ˵�id
	private String itemName; //�Ӳ˵�����
	private String urlLink; //ӳ���ַ
	private String visible;  //�ɼ���
	
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
