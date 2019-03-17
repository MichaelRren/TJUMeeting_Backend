package com.ictwsn.dao.adminer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import com.ictwsn.bean.OperatorBean;
import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;


@Repository
public class AdminerDaoImpl extends MySQLBaseDao implements AdminerDao {
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;
	
	
	@Override
	public int addOperator(OperatorBean ob) {
		int result=this.jt.queryForInt("select count(0) from operator where operator_name=?",new Object[]{ob.getName()});
		if(result>0)
			return -1;
		String sql="insert into operator(operator_name,operator_password,operator_uuid,operator_major,operator_phone,operator_address,role_id) values(?,?,?,?,?,?,?)";
		return this.jt.update(sql,new Object[]{ob.getName(),ob.getPassword(),ob.getUuid(),ob.getMajor(),ob.getPhone(),ob.getAddress(),ob.getRoleId()}); 
	}

	@Override
	public int deleteOperator(int operatorId) {
		String sql="delete from operator where operator_id=?";
		return this.jt.update(sql,new Object[]{operatorId}); 
	}

	@Override
	public List<OperatorBean> searchOperator(int userId,int number,int size,String roleName) {
		List<OperatorBean> obs=new ArrayList<OperatorBean>();
		if(roleName!=null&&roleName.equals("adminer")){
			String sql="select operator_id,operator_name,operator_password,operator_uuid,operator_major,operator_phone,operator_address from operator order by operator_id desc limit ?,?";
			try{
				conn=CurrentConn.getInstance().getConn();
				pst=conn.prepareStatement(sql);
				pst.setInt(1,number);
				pst.setInt(2,size);
				rs=pst.executeQuery();
				while(rs.next()){
					OperatorBean ob=new OperatorBean();
					ob.setId(rs.getInt(1));
					ob.setName(rs.getString(2));
					ob.setPassword(rs.getString(3));
					ob.setUuid(rs.getString(4));
					ob.setMajor(rs.getInt(5));
					ob.setPhone(rs.getString(6));
					ob.setAddress(rs.getString(7));
					
					obs.add(ob);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				CurrentConn.getInstance().closeResultSet(rs);
				CurrentConn.getInstance().closePreparedStatement(pst);
				CurrentConn.getInstance().closeConnection(conn);
			}
		}
		return obs;
			
		
	}

	@Override
	public OperatorBean getOperatorById(int operatorId) {
		OperatorBean ob=new OperatorBean();
		String sql="select operator_id,operator_name,operator_password,operator_uuid,operator_major,operator_phone,operator_address,role_id from operator where operator_id=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,operatorId);
			rs=pst.executeQuery();
			if(rs.next()){
				ob.setId(rs.getInt(1));
				ob.setName(rs.getString(2));
				ob.setPassword(rs.getString(3));
				ob.setUuid(rs.getString(4));
				ob.setMajor(rs.getInt(5));
				ob.setPhone(rs.getString(6));
				ob.setAddress(rs.getString(7));
				ob.setRoleId(rs.getInt(8));
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return ob;
	}

	@Override
	public int updateOperator(OperatorBean ob) {
		return this.jt.update("update operator set operator_name=?,operator_password=?,operator_uuid=?,operator_major=?,operator_phone=?,operator_address=?,role_id=? where operator_id=?",
				new Object[]{ob.getName(),ob.getPassword(),ob.getUuid(),ob.getMajor(),ob.getPhone(),ob.getAddress(),ob.getRoleId(),ob.getId()});
		
	}

	@Override
	public int getOperatorCount(){
		// TODO Auto-generated method stub
		return this.jt.queryForInt("select count(0) from operator");
	}

	@Override
	public List<OperatorBean> searchDeviceByCondition(String type,String keyword,int userId,String roleName,int number,int size){
		List<OperatorBean> obs=new ArrayList<OperatorBean>();
		if(type!=null&&type.equals("operatorName")){
			String sql="select operator_id,operator_name,operator_password,operator_uuid,operator_major,operator_phone,operator_address from operator where operator_name=? order by operator_id desc limit ?,?";
			try{
				conn=CurrentConn.getInstance().getConn();
				pst=conn.prepareStatement(sql);
				pst.setString(1,keyword);
				pst.setInt(2,number);
				pst.setInt(3,size);
				rs=pst.executeQuery();
				while(rs.next()){
					OperatorBean ob=new OperatorBean();
					ob.setId(rs.getInt(1));
					ob.setName(rs.getString(2));
					ob.setPassword(rs.getString(3));
					ob.setUuid(rs.getString(4));
					ob.setMajor(rs.getInt(5));
					ob.setPhone(rs.getString(6));
					ob.setAddress(rs.getString(7));
					obs.add(ob);
				}

			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				CurrentConn.getInstance().closeResultSet(rs);
				CurrentConn.getInstance().closePreparedStatement(pst);
				CurrentConn.getInstance().closeConnection(conn);
			}
		}
		return obs;
	}

	@Override
	public int getOperatorCountByCondition(String type,String keyword,int userId,String roleName) {
		// TODO Auto-generated method stub
		return this.jt.queryForInt("select count(0) from operator where operator_name=?",new Object[]{keyword});
	}

	@Override
	public int checkOperatorUuid(String uuid) {
		// TODO Auto-generated method stub
	    return this.jt.queryForInt("select count(0) from operator where operator_uuid=?",new Object[]{uuid});
	}

	@Override
	public int checkOperatorMajor(String uuid, int major) {
		// TODO Auto-generated method stub
		return this.jt.queryForInt("select count(0) from operator where operator_uuid=? and operator_major=?",new Object[]{uuid,major});
	}
}