package com.ictwsn.service.liaison;

import java.util.List;


import com.ictwsn.bean.LiaisonBean;


public interface LiaisonService {
	
	public int addLiaison(LiaisonBean lb);
	public boolean deleteLiaison(String lnumber);	
	public int updateLiaison(LiaisonBean ob);
	public int getLiaisonCount();
	public List<LiaisonBean> searchLiaison(int number, int size);
	public int checkLnumber(String lnumber);//判断lnumber是否唯一
	public LiaisonBean searchLiaisonByLnumber(String lnumber);
	public LiaisonBean searchLiaisonByUserNumber(String userNumber);
	public List<LiaisonBean> searchLiaisonByCondition(String type, String keyword, int number, int size);
	public List<LiaisonBean> searchLiaisonByCondition(String type, String keyword);
}
