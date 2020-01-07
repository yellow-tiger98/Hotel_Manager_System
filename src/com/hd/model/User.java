package com.hd.model;

public class User {
	//记录用户的id
	private String id;
	//用户登录账号
	private String account;
	//用户名字
	private String username;
	//用户登录密码
	private String password;
	//用户性别
	private String sex;
	//用户身份标签
	private String roleid;
	//用户创建时间
	private String createDate;
	
	private String roleName;

	public User(){
		System.out.println("{User} 调用了无参构造器...");
	}

	
	
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", account=" + account + ", username=" + username + ", password=" + password
				+ ", sex=" + sex + ", roleid=" + roleid + ", createDate=" + createDate + "]";
	}

	
	
	
}
