package com.hd.model;

public class User {
	//��¼�û���id
	private String id;
	//�û���¼�˺�
	private String account;
	//�û�����
	private String username;
	//�û���¼����
	private String password;
	//�û��Ա�
	private String sex;
	//�û���ݱ�ǩ
	private String roleid;
	//�û�����ʱ��
	private String createDate;
	
	private String roleName;

	public User(){
		System.out.println("{User} �������޲ι�����...");
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
