package com.ictwsn.service.userchange;

import java.util.List;


import com.ictwsn.bean.UserChangeBean;


public interface UserChangeService {
	public int addUserChange(UserChangeBean ucb);
	public List<UserChangeBean> searchUserChange(int number, int size);
	public List<UserChangeBean> getUserChangeByStatus();
	public int updateStatus(int changeID);
	public int getUserChangeCount();
}
