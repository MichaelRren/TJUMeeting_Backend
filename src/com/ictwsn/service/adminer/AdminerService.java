package com.ictwsn.service.adminer;

import java.util.List;

import com.ictwsn.bean.OperatorBean;
/**
 * 超级管理员service层接口类
 * @author YangYanan
 * @desc
 * @date 2017-8-18
 */
public interface AdminerService {
	
	public int addOperator(OperatorBean ob);		 //添加运营商信息
	public int deleteOperator(int operatorId);	 	 //删除运营商
	public OperatorBean getOperatorById(int operatorId); //查询单个运营商信息
	public int updateOperator(OperatorBean ob);		     //修改运营商信息
	public List<OperatorBean> searchOperator(int userId,int number,int size,String roleName);  //查询运营商列表
	public int getOperatorCount();		                 
	public List<OperatorBean> searchDeviceByCondition(String type,String keyword,int userId,String roleName,int number,int size);//条件查询运营商列表
	public int getOperatorCountByCondition(String type,String keyword,int userId,String roleName);//计算条件查询的数量
	public int checkOperatorUuid(String uuid);//判断uuid是否唯一
	public int checkOperatorMajor(String uuid,int major);//判断uuid下major是否唯一
}
