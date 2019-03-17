package com.ictwsn.service.client;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.ictwsn.bean.DeviceBean;
import com.ictwsn.dao.client.ClientDao;


@Service
public class ClientServiceImpl implements ClientService {

	@Resource ClientDao dao;

	@Override
	public int addDevice(DeviceBean db) {
		// TODO Auto-generated method stub
		db.setUuid(dao.getClientUuid(db.getClientId()));//查询出uuid
		db.setMajor(dao.getClientMajor(db.getClientId()));//查询出major
		return dao.addDevice(db);
	}

	@Override
	public boolean deleteDevice(int deviceId) {
		// TODO Auto-generated method stub
		
		return dao.deleteDevice(deviceId);
	}

	@Override
	public List<DeviceBean> searchDevice(int userId,int number,int size,String roleName){
		// TODO Auto-generated method stub
		return dao.searchDevice(userId,number,size,roleName);
	}

	@Override
	public DeviceBean getDeviceById(int deviceId) {
		// TODO Auto-generated method stub
		return dao.getDeviceById(deviceId);
	}

	@Override
	public int updateDevice(DeviceBean db) {
		// TODO Auto-generated method stub
		return dao.updateDevice(db);
	}

	@Override
	public int getDeviceCount(int userId,String roleName) {
		// TODO Auto-generated method stub
		return dao.getDeviceCount(userId,roleName);
	}
	@Override
	public int checkMinorRepeat(int clientId,int minor){
		return dao.checkMinorRepeat(clientId, minor);
	}

	@Override
	public List<DeviceBean> searchDeviceByCondition(String type,String keyword,int userId,String roleName,int number,int size){
		// TODO Auto-generated method stub
		return dao.searchDeviceByCondition(type,keyword,userId,roleName,number,size);
	}

	@Override
	public DeviceBean getDeviceDisplayInfo(String uuid, int major, int minor) {
		// TODO Auto-generated method stub
		return dao.getDeviceDisplayInfo(uuid,major,minor);
	}

	@Override
	public int getDeviceCountByCondition(String type, String keyword,
			int userId, String roleName) {
		// TODO Auto-generated method stub
		return dao.getDeviceCountByCondition(type, keyword, userId, roleName);
	}

	@Override
	public DeviceBean test() {
		// TODO Auto-generated method stub
		return dao.test();
	}



}
