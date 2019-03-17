package com.ictwsn.dao.adminer;

import java.util.List;
import com.ictwsn.bean.OperatorBean;

public interface AdminerDao {
	
	public int addOperator(OperatorBean ob);		 //添加运营商信息
	public int deleteOperator(int operatorId);	 	 //删除运营商
	public List<OperatorBean> searchOperator(int userId,int number,int size,String roleName);  //查询运营商列表
	public OperatorBean getOperatorById(int operatorId); //查询单个运营商信息
	public int updateOperator(OperatorBean ob);		 //修改运营商信息
	public int getOperatorCount();		 //查询运营商数量
	public List<OperatorBean> searchDeviceByCondition(String type,String keyword,int userId,String roleName,int number,int size);
	public int getOperatorCountByCondition(String type,String keyword,int userId,String roleName);
	public int checkOperatorUuid(String uuid);//判断uuid是否唯一
	public int checkOperatorMajor(String uuid,int major);//判断uuid下major是否唯一
}
