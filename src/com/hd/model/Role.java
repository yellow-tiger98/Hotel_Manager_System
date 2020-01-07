package com.hd.model;

public class Role {
	private String roleid;
	private String roleName;
	private String desc;
	
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "Role [roleid=" + roleid + ", roleName=" + roleName + ", desc=" + desc + "]";
	}
	
	
	
}
