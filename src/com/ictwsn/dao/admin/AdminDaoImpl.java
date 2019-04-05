package com.ictwsn.dao.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import com.ictwsn.bean.AdminBean;

@Repository
public class AdminDaoImpl extends MySQLBaseDao implements AdminDao{

	
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;
	
	@Override
	public int addAdmin(AdminBean ab) {
		String sql="select count(0) from Admin where AdminName=?";
		int checkResult=this.jt.queryForInt(sql,new Object[]{ab.getAdminName()});
		if(checkResult==0){//等于0代表不重复,可以添加
			sql="insert into Driver(AdminName,Password) values(?,?)";
			return this.jt.update(sql,new Object[]{ab.getAdminName(),ab.getPassword()}); 
		}else if(checkResult==1){//等于1代表minor重复,不可以添加
			return -1;
		}
		return 0;
	}

	@Override
	public boolean deleteAdmin(String adminName) {
		int result=this.jt.update("delete from Admin where AdminName=? ",new Object[]{adminName});
		return result>0?true:false;
	}

	@Override
	public List<AdminBean> searchAdmin() {
		List<AdminBean> abs=new ArrayList<AdminBean>();
		String sql="select * from Admin";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			while(rs.next()) {
				AdminBean ab=new AdminBean();
				ab.setAdminID(rs.getInt(1));
				ab.setAdminName(rs.getString(2));
				ab.setPassword(rs.getString(3));
				abs.add(ab);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return abs;
	}

	@Override
	public int updateAdmin(AdminBean ab) {
		return this.jt.update("update Admin set AdminName=?,Password=? where AdminID=?",
				new Object[]{ab.getAdminName(),ab.getPassword(),ab.getAdminID()});
	}

	@Override
	public int checkAdminName(String adminName) {
		return this.jt.queryForInt("select count(0) from Admin where AdminName=?",new Object[]{adminName});
	}

	@Override
	public AdminBean searchAdminByAdminName(String adminName) {
		AdminBean ab=new AdminBean();
		String sql="select * from Admin where UserNumber=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,adminName);
			rs=pst.executeQuery();
			if(rs.next()){
				ab.setAdminID(rs.getInt(1));
				ab.setAdminName(rs.getString(2));
				ab.setPassword(rs.getString(3));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return ab;
	}
	
	
	public AdminBean AdminLogin(String adminName,String password){
		AdminBean ab = new AdminBean();
		String sql = "select * from Admin where AdminName=? and Password=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,adminName);
			pst.setString(2, password);
			rs=pst.executeQuery();
			if(rs.next()){
				ab.setAdminID(rs.getInt(1));
				ab.setAdminName(rs.getString(2));
				ab.setPassword(rs.getString(3));	
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return ab;
	}

}
