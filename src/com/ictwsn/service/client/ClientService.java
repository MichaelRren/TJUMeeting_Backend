package com.ictwsn.service.client;

import java.util.List;

import com.ictwsn.bean.DeviceBean;
/**
 * 运营商service层接口类
 * @author YangYanan
 * @desc
 * @date 2017-8-18
 */
public interface ClientService{
	
	public int addDevice(DeviceBean db);	    //添加设备信息
	public boolean deleteDevice(int deviceId);	//删除设备
	public List<DeviceBean> searchDevice(int userId,int number,int size,String roleName); //查询设备列表
	public DeviceBean getDeviceById(int deviceId);//查询单个设备信息
	public int updateDevice(DeviceBean db);		  //修改设备信息
	public int getDeviceCount(int userId,String roleName);  //查看用户拥有的设备数量
	public int checkMinorRepeat(int clientId,int minor);    //添加设备时检查minor是否重复
	public List<DeviceBean> searchDeviceByCondition(String type,String keyword,int userId,String roleName,int number,int size);//条件查询
	public int getDeviceCountByCondition(String type,String keyword,int userId,String roleName);//条件查询数量
	public DeviceBean getDeviceDisplayInfo(String uuid,int major,int minor);//获取展示信息,游客接口
	
	public DeviceBean test();
}
