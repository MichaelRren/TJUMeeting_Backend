package com.ictwsn.bean;
/**
 * 终端用户实体类
 * @author YangYanan
 * @desc 继承用户基类,私有邮箱和所属运营商ID
 * @date 2017-8-18
 */
public class ClientBean extends UserBean {
	private String email; //邮箱
	private int operatorId; //所属运营商ID
	private String operatorName;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	

}
