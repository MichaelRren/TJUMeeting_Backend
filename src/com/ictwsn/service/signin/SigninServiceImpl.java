package com.ictwsn.service.signin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ictwsn.bean.SigninBean;
import com.ictwsn.dao.signin.SigninDao;

@Service
public class SigninServiceImpl implements SigninService{

	@Resource SigninDao dao;
	@Override
	public int addSignin(SigninBean sb) {
		if(dao.checkSignin(sb.getUserNumber())>0)
			return -2;
		return dao.addSignin(sb);
	}

	@Override
	public boolean deleteSignin(String userNumber) {
		return dao.deleteSignin(userNumber);
	}

	@Override
	public List<SigninBean> searchSignin(int number, int size) {
		return dao.searchSignin(number, size);
	}

	@Override
	public int updateSignin(SigninBean sb) {
		return dao.updateSignin(sb);
	}

	@Override
	public int checkSignin(String userNumber) {
		return dao.checkSignin(userNumber);
	}

	@Override
	public SigninBean searchSigninByuserNumber(String userNumber) {
		return dao.searchSigninByuserNumber(userNumber);
	}

	@Override
	public List<SigninBean> searchSigninByStatus(boolean status) {
		return dao.searchSigninByStatus(status);
	}

	@Override
	public int getSigninCount(){return dao.getSigninCount();}

}
