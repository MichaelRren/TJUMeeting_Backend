package com.ictwsn.service.userdriver;

import com.ictwsn.bean.UserDriverBean;

import java.util.List;

public interface UserDriverService {
	public int addUserDriver(UserDriverBean udb);  //增加
	public boolean deleteUserDriver(int id);	 	 //删除
	public List<UserDriverBean> searchUserDriver();		 	 //查询all
	public int updateUserDriver(UserDriverBean udb);		//修改	
	public int checkUserNumber(String userNumber);     //判断userNnumber是否唯一
	public UserDriverBean searchUserDriverByUserNumber(String userNumber);

}




