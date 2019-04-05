package com.ictwsn.service.role;

import com.ictwsn.bean.RoleBean;

import java.util.List;

public interface RoleService {
	public List<RoleBean> searchUserRole();
	public RoleBean login(String userNumber);

}
