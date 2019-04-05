package com.ictwsn.dao.userdriver;

import java.util.List;
import com.ictwsn.bean.UserDriverBean;

public interface UserDriverDao {
	public int addUserDriver(UserDriverBean udb);  //增加
	public boolean deleteUserDriver(int id);	 	 //删除
	public List<UserDriverBean> searchUserDriver();		 	 //查询all
	public int updateUserDriver(UserDriverBean udb);		//修改	
	public int checkUserNumber(String userNumber);     //判断userNnumber是否唯一
	public UserDriverBean searchUserDriverByUserNumber(String userNumber);

}




