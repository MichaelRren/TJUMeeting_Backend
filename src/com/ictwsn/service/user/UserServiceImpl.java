package com.ictwsn.service.user;

import com.ictwsn.bean.UserBean;
import com.ictwsn.bean.UserChangeBean;
import com.ictwsn.dao.user.UserDao;
import com.ictwsn.dao.userchange.UserChangeDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Resource UserDao dao;
	@Resource UserChangeDao ucdao;
	@Override
	public int addUser(UserBean ub) {
		if(dao.checkUserNumber(ub.getUserNumber())>0)
			return -2;
		return dao.addUser(ub);
	}

	@Override
	public boolean deleteUser(String userNumber) {
		return dao.deleteUser(userNumber);
	}

	@Override
	public int updateUser(UserBean ub) {
		UserBean oldub = dao.searchUserByUserNumber(ub.getUserNumber());

		int result = dao.updateUser(ub);

		if(result == 1) {
			String content = "用户"+ub.getUserName()+" ";
			UserChangeBean ucb = new UserChangeBean();
			ucb.setUserNumber(ub.getUserNumber());
			if(!ub.getUserName().equals(oldub.getUserName())){
				content += "姓名变为"+ub.getUserName()+" ";
			}
			if(!ub.getUserPhoto().equals(oldub.getUserPhoto())){
				content+="重新上传照片 ";
			}
			if(!ub.getSex().equals(oldub.getSex())){
				content+="改变性别为"+ub.getSex()+" ";
			}
//			if(!ub.getWorkerNumber().equals(oldub.getWorkerNumber())){
//				content+="随行人员手机号变为"+ub.getWorkerNumber()+" ";
//			}
			if(!ub.getUserSorts().equals(oldub.getUserSorts())){
				content+="人员类别变为"+ub.getUserSorts();
			}
			if(!ub.getWorkPlace().equals(oldub.getWorkPlace())){
				content+="工作单位变为"+ub.getWorkPlace()+" ";
			}
			if(!ub.getTitle().equals(oldub.getTitle())){
				content+="职称变为"+ub.getTitle()+" ";
			}
			if(!ub.getEmail().equals(oldub.getEmail())){
				content+="邮箱变为"+ub.getEmail()+" ";
			}
			if(!ub.getArrivalDate().equals(oldub.getArrivalDate())){
				content+="抵达日期变为"+ub.getArrivalDate()+" ";
			}
			if(!ub.getArrivalNumber().equals(oldub.getArrivalNumber())){
				content+="抵达车次变为"+ub.getArrivalNumber()+" ";
			}
			if(!ub.getArrivalTime().equals(oldub.getArrivalTime())){
				content+="抵达时间变为"+ub.getArrivalTime()+" ";
			}
			if(!ub.getReturnStation().equals(oldub.getReturnStation())){
				content+="抵达车站变为"+ub.getArrivalStation()+" ";
			}
			if(!ub.getReturnDate().equals(oldub.getReturnDate())){
				content+="返程日期变为"+ub.getReturnDate()+" ";
			}
			if(!ub.getReturnNumber().equals(oldub.getReturnNumber())){
				content+="返程车次变为"+ub.getReturnNumber()+" ";
			}
			if(!ub.getReturnTime().equals(oldub.getReturnTime())){
				content+="返程时间变为"+ub.getReturnTime()+" ";
			}
			if(!ub.getReturnStation().equals(oldub.getReturnStation())){
				content+="返程车站变为"+ub.getReturnStation()+" ";
			}
//			if(!ub.getHname().equals(oldub.getHname())){
//				content+="酒店变为"+ub.getHname()+" ";
//			}
//			if(!ub.getHtype().equals(oldub.getHtype())){
//				content+="房型变为"+ub.getHtype()+" ";
//			}
			if(!ub.getDinner().equals(oldub.getDinner())){
				content+="用餐需求变为"+ub.getDinner();
			}
			if(!ub.getRemark().equals(oldub.getRemark())){
				content+="备注变为"+ub.getRemark();
			}
			if(!ub.getCheckin().equals(oldub.getCheckin())){
				content+="入住时间变为"+ub.getCheckin();
			}
			if(!ub.getCheckout().equals(oldub.getCheckout())){
				content+="离宿时间变为"+ub.getCheckout();
			}
			content=content.trim();
			ucb.setContent(content);
			Date date = new Date();
			Timestamp now = new java.sql.Timestamp(date.getTime());
			ucb.setChangeTime(now);
			ucb.setStatus(false);
			ucdao.addUserChange(ucb);
		}
		return result;


	}

	@Override
	public List<UserBean> searchUser(int number, int size) {
		return dao.searchUser(number,size);
	}

	public List<UserBean> searchUserASCReturnTime(int number, int size){
		return dao.searchUserASCReturnTime(number,size);
	}

	public int getUserConut(){
		return dao.getUserConut();
	}

	@Override
	public int checkUserNumber(String userNumber) {
		return dao.checkUserNumber(userNumber);
	}

	@Override
	public UserBean searchUserByUserNumber(String userNumber) {
		return dao.searchUserByUserNumber(userNumber);
	}

	public List<UserBean> searchUserByCondition(String type, String keyword, int number, int size) {
		return dao.searchUserByCondition(type, keyword, number, size);
	}

	public int setUserHotel(UserBean userRoom){
		return dao.setUserHotel(userRoom);
	}


	@Override
	public int getVIPCount(){return dao.getVIPCount();}

	@Override
	public int getUserCount(){return dao.getUserCount();}
}
