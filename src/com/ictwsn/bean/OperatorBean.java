package com.ictwsn.bean;
/**
 * 运营商实体类
 * @author YangYanan
 * @desc 基础用户基类,私有地址,uuid,major
 * @date 2017-8-18
 */
public class OperatorBean extends UserBean {
	private String address;//地址
	private String uuid;
	private int major;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getMajor() {
		return major;
	}
	public void setMajor(int major) {
		this.major = major;
	}

	
	

}
