package com.ictwsn.bean;

import java.util.Date;

//签到信息类
public class SigninBean {
	private String userNumber;
	private boolean status;
	private Date signintime;
	
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getSignintime() {
		return signintime;
	}
	public void setSignintime(Date signintime) {
		this.signintime = signintime;
	}
	
	

}
