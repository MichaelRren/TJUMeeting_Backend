package com.ictwsn.service.operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.ictwsn.bean.ClientBean;
import com.ictwsn.dao.operator.OperatorDao;

@Service(value="oService")
@Scope("prototype")
public class OperatorServiceImpl implements OperatorService {
	
	@Resource OperatorDao dao;

	@Override
	public int addClient(ClientBean cb) {
		// TODO Auto-generated method stub
		return dao.addClient(cb);
	}

	@Override
	public boolean deleteClient(int ClientId) {
		// TODO Auto-generated method stub
		return dao.deleteClient(ClientId);
	}

	@Override
	public List<ClientBean> searchClient(int userId,int number,int size,String roleName){
		// TODO Auto-generated method stub
		return dao.searchClient(userId,number,size,roleName);
	}

	@Override
	public ClientBean getClientById(int clientId) {
		// TODO Auto-generated method stub
		return dao.getClientById(clientId);
	}

	@Override
	public boolean updateClient(ClientBean cb) {
		// TODO Auto-generated method stub
		return dao.updateClient(cb);
	}

	@Override
	public int getClientCount(int userId,String roleName) {
		// TODO Auto-generated method stub
		return dao.getClientCount(userId,roleName);
	}

	@Override
	public List<ClientBean> searchClientByCondition(String type,
			String keyword, int userId, String roleName, int number,
			int size) {
		// TODO Auto-generated method stub
		return dao.searchClientByCondition(type, keyword, userId, roleName, number, size);
	}

	@Override
	public int getClientCountByCondition(String type, String keyword,
			int userId, String roleName) {
		// TODO Auto-generated method stub
		return dao.getClientCountByCondition(type, keyword, userId, roleName);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> query_client(int operatorId) {
		List<ClientBean> list = dao.searchClient(operatorId,0,9999,"operator");
		@SuppressWarnings("rawtypes")
		Map map  = new HashMap();
		for(ClientBean bean : list){
			map.put(bean.getId(),bean.getName());
		}
		return map;
	}

	@Override
	public String query_uuidMajor(int operatorId) {
		// TODO Auto-generated method stub
		
		return dao.query_uuidMajor(operatorId);
	}
	 

	
	


}
