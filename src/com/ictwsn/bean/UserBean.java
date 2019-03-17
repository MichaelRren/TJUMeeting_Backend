package com.ictwsn.bean;
/**
 * 用户抽象基类
 * @author yanan
 *
 */
public abstract class UserBean {
	protected String name;//用户名称(唯一)
	protected int id;//用户ID 自增
	protected String password;//用户密码
	protected String phone;//用户电话
	protected int roleId;//用户角色ID{1,2,3}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


}
