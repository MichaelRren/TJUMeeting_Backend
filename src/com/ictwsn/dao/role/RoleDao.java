package com.ictwsn.dao.role;

import com.ictwsn.bean.RoleBean;

import java.util.List;

public interface RoleDao {
	public List<RoleBean> searchUserRole();

	public RoleBean login(String userNumber);
}
