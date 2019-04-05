package com.ictwsn.service.userchange;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ictwsn.bean.UserChangeBean;
import com.ictwsn.dao.userchange.UserChangeDao;

@Service
public class UserChangeServiceImpl implements UserChangeService{

	@Resource UserChangeDao dao;
	@Override
	public int addUserChange(UserChangeBean ucb) {
		return dao.addUserChange(ucb);
	}

	@Override
	public List<UserChangeBean> searchUserChange(int number, int size) {
		return dao.searchUserChange(number,size);
	}

	public List<UserChangeBean> getUserChangeByStatus(){
		return dao.getUserChangeByStatus();
	}

	public int updateStatus(int changeID) {
		return dao.updateStatus(changeID);
	}

	public int getUserChangeCount(){
		return dao.getUserChangeCount();
	}
}
