package com.hd.model;

public class RoomType {
	private String id;
	private String typeName;
	private String dayPrice;
	private String hourPrice;
	private String residence;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getDayPrice() {
		return dayPrice;
	}
	public void setDayPrice(String dayPrice) {
		this.dayPrice = dayPrice;
	}
	public String getHourPrice() {
		return hourPrice;
	}
	public void setHourPrice(String hourPrice) {
		this.hourPrice = hourPrice;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	@Override
	public String toString() {
		return "RoomType [id=" + id + ", typeName=" + typeName + ", dayPrice=" + dayPrice + ", hourPrice=" + hourPrice
				+ ", residence=" + residence + "]";
	}
	
	
}
