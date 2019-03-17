package com.ictwsn.dao.login;

import com.ictwsn.bean.RoleBean;


public interface LoginDao {
	
	public RoleBean Login(String userName,String password,String role);  //登陆
	public int getErrorType(String userName,String password,String roleName);  //用户名密码不匹配时判断用户错误类型

}
