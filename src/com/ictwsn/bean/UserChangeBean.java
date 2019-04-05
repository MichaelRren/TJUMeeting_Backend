package com.ictwsn.bean;

import java.util.Date;

public class UserChangeBean {
	private int changeID;
	private String userNumber;
	private String content;
	private Date changeTime;
	private boolean status;
	public int getChangeID() {
		return changeID;
	}
	public void setChangeID(int changeID) {
		this.changeID = changeID;
	}
	public String getUserNumber() {
		return userNumber;
	}
	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	
	
}
