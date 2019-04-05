package com.ictwsn.bean;

//用户司机对应信息类
public class UserDriverBean {
	private int id;
	private String userNumber;//vip手机号
	private String dplate; //车牌号
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getDplate() {
		return dplate;
	}
	public void setDplate(String dplate) {
		this.dplate = dplate;
	}
}
