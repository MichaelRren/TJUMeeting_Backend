package com.ictwsn.dao.driver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import com.ictwsn.bean.DriverBean;

@Repository
public class DriverDaoImpl extends MySQLBaseDao implements DriverDao{
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;

	@Override
	public int addDriver(DriverBean db) {
		String sql="select count(0) from Driver where Dplate=?";
		int checkResult=this.jt.queryForInt(sql,new Object[]{db.getDplate()});
		if(checkResult==0){//等于0代表不重复,可以添加
			sql="insert into Driver(Dplate,Dnumber,Dname) values(?,?,?)";
			return this.jt.update(sql,new Object[]{db.getDplate(),db.getDnumber(),db.getDname()}); 
		}else if(checkResult==1){//等于1代表minor重复,不可以添加
			return -1;
		}
		return 0;
	}

	@Override
	public boolean deleteDriver(String dplate) {
		int result=this.jt.update("delete from Driver where Dplate=?",new Object[]{dplate});
		return result>0?true:false;
	}

	@Override
	public List<DriverBean> searchDriver() {
		List<DriverBean> dbs=new ArrayList<DriverBean>();
		String sql="select * from Driver";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			while(rs.next()) {
				DriverBean db=new DriverBean();
				db.setDplate(rs.getString(1));
				db.setDnumber(rs.getString(2));
				db.setDname(rs.getString(3));
				dbs.add(db);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return dbs;
	}

	@Override
	public List<DriverBean> searchDriver(int number, int size) {
		List<DriverBean> dbs=new ArrayList<DriverBean>();
		String sql="select * from Driver limit ?,?";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,number);
			pst.setInt(2,size);
			rs=pst.executeQuery();
			while(rs.next()) {
				DriverBean db=new DriverBean();
				db.setDplate(rs.getString(1));
				db.setDnumber(rs.getString(2));
				db.setDname(rs.getString(3));		
				dbs.add(db);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return dbs;
	}

	@Override
	public int updateDriver(DriverBean db) {
		return this.jt.update("update Driver set Dnumber=?,Dname=? where Dplate=?",
				new Object[]{db.getDnumber(),db.getDname(),db.getDplate()});
	}

	@Override
	public int checkDplate(String dplate) {
		return this.jt.queryForInt("select count(0) from Driver where Dplate=?",new Object[]{dplate});
	}

	@Override
	public DriverBean searchDriverByDplate(String dplate) {
		DriverBean db=new DriverBean();
		String sql="select * from Driver where Dplate=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,dplate);
			rs=pst.executeQuery();
			if(rs.next()){
				db.setDplate(rs.getString(1));
				db.setDnumber(rs.getString(2));
				db.setDname(rs.getString(3));	
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return db;
	}

	@Override
	public DriverBean searchDriverByDname(String dname)
	{
		DriverBean db=new DriverBean();
		String sql="select * from Driver where Dname=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,dname);
			rs=pst.executeQuery();
			if(rs.next()){
				db.setDplate(rs.getString(1));
				db.setDnumber(rs.getString(2));
				db.setDname(rs.getString(3));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return db;
	}

	@Override
	public int getDriverCount(){
		return this.jt.queryForInt("select count(0) from Driver");
	}
}
