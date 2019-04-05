package com.ictwsn.service.driver;

import java.util.List;

import com.ictwsn.bean.DriverBean;

public interface DriverService {
	public int addDriver(DriverBean db);  //增加
	public boolean deleteDriver(String dplate);	 	 //删除
	public List<DriverBean> searchDriver(int number, int size);		 	 //查询all
	public int updateDriver(DriverBean db);		//修改	
	public int checkDplate(String dplate);     //判断dplate车牌号是否唯一
	public DriverBean searchDriverByDplate(String dplate);
	public DriverBean searchDriverByDname(String dname);
	public int getDriverCount();
	public List<DriverBean> searchDriver();
}
