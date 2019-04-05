package com.ictwsn.dao.userdriver;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import com.ictwsn.bean.UserDriverBean;


@Repository
public class UserDriverDaoImpl extends MySQLBaseDao implements UserDriverDao{
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;

	@Override
	public int addUserDriver(UserDriverBean udb) {
		String sql="select count(0) from User_Driver where UserNumber=?";
		int checkResult=this.jt.queryForInt(sql,new Object[]{udb.getUserNumber()});
		if(checkResult==0){//等于0代表不重复,可以添加
			sql="insert into User_Driver(UserNumber,Dplate) values(?,?)";
			return this.jt.update(sql,new Object[]{udb.getUserNumber(),udb.getDplate()}); 
		}else if(checkResult==1){//等于1代表minor重复,不可以添加
			return -1;
		}
		return 0;
	}

	@Override
	public boolean deleteUserDriver(int id) {
		int result=this.jt.update("delete from User_Driver where id=?",new Object[]{id});
		return result>0?true:false;
	}

	@Override
	public List<UserDriverBean> searchUserDriver() {
		List<UserDriverBean> udbs=new ArrayList<UserDriverBean>();
		String sql="select * from User_Driver";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			while(rs.next()) {
				UserDriverBean udb=new UserDriverBean();
				udb.setId(rs.getInt(1));
				udb.setUserNumber(rs.getString(2));
				udb.setDplate(rs.getString(3));
				udbs.add(udb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return udbs;
	}

	@Override
	public int updateUserDriver(UserDriverBean udb) {
		return this.jt.update("update User_Driver set UserNumber=?,Dplate=? where id=?",
				new Object[]{udb.getUserNumber(),udb.getDplate(),udb.getId()});
	}

	@Override
	public int checkUserNumber(String userNumber) {
		return this.jt.queryForInt("select count(0) from UserDriver where UserNumber=?",new Object[]{userNumber});
	}

	@Override
	public UserDriverBean searchUserDriverByUserNumber(String userNumber) {
		UserDriverBean udb=new UserDriverBean();
		String sql="select * from User_Driver where UserNumber=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,userNumber);
			rs=pst.executeQuery();
			if(rs.next()){
				udb.setId(rs.getInt(1));
				udb.setUserNumber(rs.getString(2));
				udb.setDplate(rs.getString(3));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return udb;
	}
}
