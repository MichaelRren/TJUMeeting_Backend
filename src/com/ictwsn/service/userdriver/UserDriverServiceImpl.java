package com.ictwsn.service.userdriver;

import com.ictwsn.bean.UserDriverBean;
import com.ictwsn.dao.userdriver.UserDriverDao;
import com.ictwsn.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserDriverServiceImpl implements UserDriverService {

	@Resource
	UserDriverDao dao;

	public int addUserDriver(UserDriverBean udb){
		return dao.addUserDriver(udb);
	}
	public boolean deleteUserDriver(int id){
		return dao.deleteUserDriver(id);
	}
	public List<UserDriverBean> searchUserDriver(){
		return  dao.searchUserDriver();
	}
	public int updateUserDriver(UserDriverBean udb){
		return dao.updateUserDriver(udb);
	}
	public int checkUserNumber(String userNumber){
		return dao.checkUserNumber(userNumber);
	}
	public UserDriverBean searchUserDriverByUserNumber(String userNumber){
		return dao.searchUserDriverByUserNumber(userNumber);
	}

}




