package com.ictwsn.service.driver;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ictwsn.bean.DriverBean;
import com.ictwsn.dao.driver.DriverDao;

@Service
public class DriverServiceImpl implements DriverService{

	@Resource DriverDao dao;
	@Override
	public int addDriver(DriverBean db) {
		if(dao.checkDplate(db.getDplate())>0)
			return -2;
		return dao.addDriver(db);
	}


	@Override
	public List<DriverBean> searchDriver(int number, int size) {
		return dao.searchDriver(number, size);
	}

	@Override
	public int updateDriver(DriverBean db) {
		return dao.updateDriver(db);
	}


	@Override
	public boolean deleteDriver(String dplate) {
		return dao.deleteDriver(dplate);
	}


	@Override
	public int checkDplate(String dplate) {
		return dao.checkDplate(dplate);
	}


	@Override
	public DriverBean searchDriverByDplate(String dplate) {
		return dao.searchDriverByDplate(dplate);
	}

	@Override
	public DriverBean searchDriverByDname(String dname){return dao.searchDriverByDname(dname);}

	@Override
	public int getDriverCount(){return dao.getDriverCount();}

	@Override
	public List<DriverBean> searchDriver(){return dao.searchDriver();}
}
