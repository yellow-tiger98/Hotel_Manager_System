package com.hd.model;

public class Room {
	private String id;
	private String typeId;
	private String typeName;
	private int floor;
	private String status;	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Room [id=" + id + ", typeId=" + typeId + ", typeName=" + typeName + ", floor=" + floor + ", status="
				+ status + "]";
	}
	
	
}
