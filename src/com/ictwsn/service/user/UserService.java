package com.ictwsn.service.user;

import com.ictwsn.bean.UserBean;

import java.util.List;


public interface UserService {
	public int addUser(UserBean ub);
	public int deleteUser(String userNumber);
	public int updateUser(UserBean ub);
	public int getUserConut();
	public List<UserBean> searchUser(int number, int size);
	public int checkUserNumber(String userNumber);//判断usernumber是否唯一
	public UserBean searchUserByUserNumber(String userNumber);
	public List<UserBean> searchUserByCondition(String type,String keyword,int number,int size); //从number的下一个开始取size条记录
	public int getVIPCount();
	public int getUserCount();

	public int setUserHotel(UserBean userRoom); // 为用户分配酒店房

	/**
	 * 用户分配宾馆
	 */
	public int updateUserHotel(UserBean userBean);
	/**
	 * Excel增加查询功能
	 */
	public List<UserBean> searchUserByCondition(String type, String keyword);

    /**
     *  报到组导出Excel
     */
    public List<UserBean> searchUser();
}
