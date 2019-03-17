package com.ictwsn.dao.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.ictwsn.bean.RoleBean;
import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;

@Repository
public class LoginDaoImpl extends MySQLBaseDao implements LoginDao {
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;

	/**
	 * 用户登录,判断传递的role决定查询哪个表
	 */
	@Override
	public RoleBean Login(String userName,String password,String roleName){
		final RoleBean rb=new RoleBean();
		rb.setUserName(userName);
		rb.setRoleName(roleName);
		String sql="";
		if(roleName!=null&&roleName.equals("client"))
			sql="select authority,client_id,operator_uuid,operator_major from client,role_authority,operator where  client.role_id=role_authority.role_id and client_name=? and client_password=?";
		else if(roleName!=null&&roleName.equals("adminer"))
			sql="select authority,adminer_id,adminer_name,adminer_id from adminer,role_authority where adminer.role_id=role_authority.role_id and adminer_name=? and adminer_password=?";
		else
			sql="select authority,operator_id,operator_uuid,operator_major from operator,role_authority where operator.role_id=role_authority.role_id and operator_name=? and operator_password=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,userName);
			pst.setString(2,password);
			rs=pst.executeQuery();
			if(rs.next()){
				rb.setAuthroity(rs.getString(1));
				rb.setUserId(rs.getInt(2));
				rb.setUuid(rs.getString(3));
				rb.setMajor(rs.getString(4));
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		if(roleName!=null&&roleName.equals("adminer")){
			rb.setUuid("");
			rb.setMajor("");
		}
	
		return rb;
	
	}

	@Override
	public int getErrorType(String userName, String password, String roleName) {
		int res=0;
		res+=this.jt.queryForInt("select count(0) from client where client.client_name=? and client_password=?",new Object[]{userName,password});
		res+=this.jt.queryForInt("select count(0) from adminer where adminer_name=? and adminer_password=?",new Object[]{userName,password});
		res+=this.jt.queryForInt("select count(0) from operator where operator_name=? and operator_password=?",new Object[]{userName,password});
		
		if(res>0){
			return -2;
		}
		return -1;
	}



}
