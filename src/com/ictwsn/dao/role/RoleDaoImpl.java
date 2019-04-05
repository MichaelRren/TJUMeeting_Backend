package com.ictwsn.dao.role;

import com.ictwsn.bean.RoleBean;
import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleDaoImpl extends MySQLBaseDao implements RoleDao{

	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;
	
	@Override
	public List<RoleBean> searchUserRole() {
		List<RoleBean> urbs = new ArrayList<RoleBean>();
		String sql="select role_id,authority,role_name,UserNumber,UserName from User,Role where User.UserRole=Role.role_name";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			while(rs.next()) {
				RoleBean urb=new RoleBean();
				urb.setRole_id(rs.getInt(1));
				urb.setAuthority(rs.getString(2));
				urb.setRole_name(rs.getString(3));
				urb.setUserNumber(rs.getString(4));
				urb.setUserName(rs.getString(5));
				urbs.add(urb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return urbs;
		
	}

	@Override
	public RoleBean login(String userNumber) {
		RoleBean urb = new RoleBean();
		String sql = "select role_id,authority,role_name,UserNumber,UserName from User,Role where User.UserRole=Role.role_name and UserNumber=?";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1, userNumber);
			rs=pst.executeQuery();
			while(rs.next()) {
				urb.setRole_id(rs.getInt(1));
				urb.setAuthority(rs.getString(2));
				urb.setRole_name(rs.getString(3));
				urb.setUserNumber(rs.getString(4));
				urb.setUserName(rs.getString(5));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		
		return urb;
	}

}
