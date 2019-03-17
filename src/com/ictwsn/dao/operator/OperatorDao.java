package com.ictwsn.dao.operator;


import java.util.List;

import com.ictwsn.bean.ClientBean;

public interface OperatorDao {
	
	public int addClient(ClientBean cb);		 //添加终端用户信息
	public boolean deleteClient(int clientId);	 	 //删除终端用户
	public List<ClientBean> searchClient(int userId,int number,int size,String roleName);		 	 //查询终端用户列表
	public ClientBean getClientById(int clientId);	 //查询单个终端用户信息
	public boolean updateClient(ClientBean cb);		 //修改终端用户信息
	public int getClientCount(int userId,String roleName);         //查看用户拥有的设备数量
	public List<ClientBean> searchClientByCondition(String type,String keyword,int userId,String roleName,int number,int size);
	public int getClientCountByCondition(String type,String keyword,int userId,String roleName);
	public String query_uuidMajor(int operatorId);
}
