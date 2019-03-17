package com.ictwsn.dao.client;

import java.util.List;

import com.ictwsn.bean.DeviceBean;

public interface ClientDao {
	
	public int addDevice(DeviceBean db);		 //添加设备信息
	public boolean deleteDevice(int deviceId);	 //删除设备
	public List<DeviceBean> searchDevice(int userId,int number,int size,String roleName);		 	 //查询设备列表
	public DeviceBean getDeviceById(int deviceId);//查询单个设备信息
	public int updateDevice(DeviceBean db);		 //修改设备信息
	public int getDeviceCount(int userId,String roleName);         //查看用户拥有的设备数量
	public String getClientUuid(int clientId);
	public int getClientMajor(int clientId);
	public int checkMinorRepeat(int clientId,int minor);
	public List<DeviceBean> searchDeviceByCondition(String type,String keyword,int userId,String roleName,int number,int size);
	public int getDeviceCountByCondition(String type,String keyword,int userId,String roleName);
	public DeviceBean getDeviceDisplayInfo(String uuid,int major,int minor);
	
	public DeviceBean test();
}
