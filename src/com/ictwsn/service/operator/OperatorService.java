package com.ictwsn.service.operator;

import java.util.List;
import java.util.Map;

import com.ictwsn.bean.ClientBean;
 
/**
 * 运营商service层接口类
 * @author YangYanan
 * @desc
 * @date 2017-8-18
 */
public interface OperatorService {
	public int addClient(ClientBean cb);		 //添加终端用户信息
	public boolean deleteClient(int clientId);	 //删除终端用户
	public List<ClientBean> searchClient(int userId,int number,int size,String roleName);//查询终端用户列表
	public ClientBean getClientById(int clientId);	 //查询单个终端用户信息
	public boolean updateClient(ClientBean cb);		 //修改终端用户信息
	public int getClientCount(int userId,String roleName);    //查看用户拥有的设备数量
	public List<ClientBean> searchClientByCondition(String type,String keyword,int userId,String roleName,int number,int size);//条件查询
	public int getClientCountByCondition(String type,String keyword,int userId,String roleName);//条件查询数量
	public Map<Integer,String> query_client(int operatorId);
	public String query_uuidMajor(int operatorId);
}
