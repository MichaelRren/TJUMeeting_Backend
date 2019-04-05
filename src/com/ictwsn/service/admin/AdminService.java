package com.ictwsn.service.admin;

import java.util.List;

import com.ictwsn.bean.AdminBean;

public interface AdminService {
	public int addAdmin(AdminBean ab);  //增加
	public boolean deleteAdmin(String adminName);	 	 //删除
	public List<AdminBean> searchAdmin();		 	 //查询all
	public int updateAdmin(AdminBean ab);		//修改	
	public int checkAdminName(String adminName);     //判断是否唯一
	public AdminBean searchAdminByAdminName(String adminName);
	
	public AdminBean AdminLogin(String adminName,String password);

}
