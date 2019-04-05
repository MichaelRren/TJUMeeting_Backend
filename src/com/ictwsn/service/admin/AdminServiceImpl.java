package com.ictwsn.service.admin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ictwsn.bean.AdminBean;
import com.ictwsn.dao.admin.AdminDao;
@Service
public class AdminServiceImpl implements AdminService{

	@Resource AdminDao dao;
	@Override
	public int addAdmin(AdminBean ab) {
		return dao.addAdmin(ab);
	}

	@Override
	public boolean deleteAdmin(String adminName) {
		return dao.deleteAdmin(adminName);
	}

	@Override
	public List<AdminBean> searchAdmin() {
		return dao.searchAdmin();
	}

	@Override
	public int updateAdmin(AdminBean ab) {
		return dao.updateAdmin(ab);
	}

	@Override
	public int checkAdminName(String adminName) {
		return dao.checkAdminName(adminName);
	}

	@Override
	public AdminBean searchAdminByAdminName(String adminName) {
		return dao.searchAdminByAdminName(adminName);
	}

	@Override
	public AdminBean AdminLogin(String adminName, String password) {
		return dao.AdminLogin(adminName, password);
	}

}
