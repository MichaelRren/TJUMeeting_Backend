package com.ictwsn.dao.userchange;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import com.ictwsn.bean.UserChangeBean;

@Repository
public class UserChangeDaoImpl extends MySQLBaseDao implements UserChangeDao{
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;

	@Override
	public int addUserChange(UserChangeBean ucb) {
		String sql="insert into UserChange(UserNumber,Content,ChangeTime,Status) values(?,?,?,?)";
		return this.jt.update(sql, new Object[]{ucb.getUserNumber(),ucb.getContent(),ucb.getChangeTime(),ucb.isStatus()});
	}

	@Override
	public List<UserChangeBean> searchUserChange(int number, int size) {
		List<UserChangeBean> ucbs = new ArrayList<UserChangeBean>();
		String sql = "select * from UserChange Order By ChangeTime DESC limit ?,?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,number);
			pst.setInt(2,size);
			rs=pst.executeQuery();
			while(rs.next()) {
				UserChangeBean ucb = new UserChangeBean();
				ucb.setChangeID(rs.getInt(1));
				ucb.setUserNumber(rs.getString(2));
				ucb.setContent(rs.getString(3));
				ucb.setChangeTime(rs.getTimestamp(4));
				ucb.setStatus(rs.getBoolean(5));
				ucbs.add(ucb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return ucbs;
	}

	public List<UserChangeBean> getUserChangeByStatus(){
		List<UserChangeBean> ucbs = new ArrayList<UserChangeBean>();
		String sql = "select * from UserChange where Status=? Order By ChangeTime DESC ";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setBoolean(1,false);
			rs=pst.executeQuery();
			while(rs.next()) {
				UserChangeBean ucb = new UserChangeBean();
				ucb.setChangeID(rs.getInt(1));
				ucb.setUserNumber(rs.getString(2));
				ucb.setContent(rs.getString(3));
				ucb.setChangeTime(rs.getTimestamp(4));
				ucb.setStatus(rs.getBoolean(5));
				ucbs.add(ucb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return ucbs;
	}

	public int updateStatus(int changeID) {
		String sql="update UserChange set Status=true where ChangeID=?";
		return this.jt.update(sql,new Object[]{changeID});
	}

	public int getUserChangeCount(){
		return this.jt.queryForInt("select count(0) from UserChange");
	}
}
