package com.ictwsn.dao.signin;

import java.util.List;

import com.ictwsn.bean.SigninBean;

public interface SigninDao {
	public int addSignin(SigninBean sb);  //增加
	public boolean deleteSignin(String userNumber);	 	 //删除
	public List<SigninBean> searchSignin(int number, int size);		 	 //查询all
	public int updateSignin(SigninBean sb);		//用户签到status字段由false变为true
	public int checkSignin(String userNumber);     //判断usernumber是否唯一	
	public SigninBean searchSigninByuserNumber(String userNumber);
	public List<SigninBean> searchSigninByStatus(boolean status);
	public int getSigninCount();
}
