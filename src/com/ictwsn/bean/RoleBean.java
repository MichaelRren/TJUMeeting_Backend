package com.ictwsn.bean;
/**
 * 角色实体类
 * @author YangYanan
 * @desc 用于权限控制
 * @date 2017-8-18
 */
public class RoleBean {
	private String userName;//用户名称(唯一,登录使用)
	private int userId;//用户id,自增
	private String authroity;//用户权限
	private String roleName;//角色名称 {adminer,operator,client}
	private String uuid;//uuid
	private String major;//major注:放在此处不合理,但是为了不同角色添加设备信息时方便,免去查询数据库的环节
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getAuthroity() {
		return authroity;
	}
	public void setAuthroity(String authroity) {
		this.authroity = authroity;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	

}
