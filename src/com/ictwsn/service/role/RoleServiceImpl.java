package com.ictwsn.service.role;

import com.ictwsn.bean.RoleBean;
import com.ictwsn.dao.role.RoleDao;
import com.ictwsn.dao.user.UserDao;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

	@Resource RoleDao dao;
	@Resource UserDao udao;
	@Override
	public List<RoleBean> searchUserRole() {
		return dao.searchUserRole();
		
	}
	@Override
	public RoleBean login(String userNumber) {
		if(0 == udao.checkUserNumber(userNumber))
			return null;
		return dao.login(userNumber);
	}
	

}
