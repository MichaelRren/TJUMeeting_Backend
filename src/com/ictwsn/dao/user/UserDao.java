package com.ictwsn.dao.user;

import com.ictwsn.bean.UserBean;

import java.util.List;

public interface UserDao {
	
	public int addUser(UserBean ub);  //增加
	public int deleteUser(String userNumber);	 	 //删除
	public List<UserBean> searchUser(int number, int size);		 	 //查询all
	public List<UserBean> searchUserASCReturnTime(int number, int size);
	public int updateUser(UserBean ub);		//修改
	public int checkUserNumber(String userNumber);     //判断usernumber是否唯一
	public int getUserConut();
	public UserBean searchUserByUserNumber(String userNumber);
	public List<UserBean> searchUserByCondition(String type,String keyword,int number,int size);
	public int getVIPCount();
	public int getUserCount();	//普通宾客接站组
	public int setUserHotel(UserBean userRoom);


	/**
	 * 更新宾馆
	 */
	public int updateUserHotel(UserBean userBean);

	/**
	 * 为Excel增加条件搜索
	 */
	public List<UserBean> searchUserByCondition(String type, String keyword);
    /**
     * 报到组导出Excel 查询所有用户
     */
    public List<UserBean> searchUser();
}
