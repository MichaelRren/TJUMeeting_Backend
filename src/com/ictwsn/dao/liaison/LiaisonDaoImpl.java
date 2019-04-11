package com.ictwsn.dao.liaison;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ictwsn.bean.LiaisonBean;
import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;

@Repository
public class LiaisonDaoImpl extends MySQLBaseDao implements LiaisonDao{
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;

	@Override
	public int addLiaison(LiaisonBean lb) {
		String sql="select count(0) from Liaison where Lnumber=?";
		int checkResult=this.jt.queryForInt(sql,new Object[]{lb.getLnumber()});
		if(checkResult==0){//等于0代表不重复,可以添加
			sql="insert into Liaison(Lnumber,Lname,UserNumber) values(?,?,?)";
			int result = this.jt.update(sql,new Object[]{lb.getLnumber(),lb.getLname(),lb.getUserNumber()});
			return result;
		}else if(checkResult==1){//等于1代表minor重复,不可以添加
			return -1;
		}
		return 0;
	}

	@Override
	public boolean deleteLiaison(String lnumber) {
		int result=this.jt.update("delete from Liaison where Lnumber=?",new Object[]{lnumber});
		return result>0?true:false;
	}

	public int getLiaisonCount(){
		return this.jt.queryForInt("select count(0) from Liaison");
	}

	@Override
	public List<LiaisonBean> searchLiaison(int number, int size) {
		List<LiaisonBean> lbs=new ArrayList<LiaisonBean>();
		String sql="select * from Liaison limit ?,?";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,number);
			pst.setInt(2,size);
			rs=pst.executeQuery();
			while(rs.next()) {
				LiaisonBean lb=new LiaisonBean();
				lb.setLnumber(rs.getString(1));
				lb.setLname(rs.getString(2));
				lb.setUserNumber(rs.getString(3));
				lbs.add(lb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return lbs;
	}

	@Override
	public int updateLiaison(LiaisonBean lb) {
		int result = this.jt.update("update Liaison set Lname=?,UserNumber=? where Lnumber=?",
				new Object[]{lb.getLname(),lb.getUserNumber(),lb.getLnumber()});
		return result;
	}

	@Override
	public int checkLnumber(String lnumber) {
		return this.jt.queryForInt("select count(0) from Liaison where Lnumber=?",new Object[]{lnumber});
	}

	@Override
	public LiaisonBean searchLiaisonByLnumber(String lnumber) {
		LiaisonBean lb=new LiaisonBean();
		String sql="select * from Liaison where Lnumber=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,lnumber);
			rs=pst.executeQuery();
			if(rs.next()){
				lb.setLnumber(rs.getString(1));
				lb.setLname(rs.getString(2));
				lb.setUserNumber(rs.getString(3));
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return lb;
	}

	public LiaisonBean searchLiaisonByUserNumber(String userNumber){
		LiaisonBean lb=new LiaisonBean();
		String sql="select * from Liaison where UserNumber=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,userNumber);
			rs=pst.executeQuery();
			if(rs.next()){
				lb.setLnumber(rs.getString(1));
				lb.setLname(rs.getString(2));
				lb.setUserNumber(rs.getString(3));
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return lb;
	}

	public List<LiaisonBean> searchLiaisonByCondition(String type, String keyword, int number, int size) {
		List<LiaisonBean> lbs = new ArrayList<LiaisonBean>();
		if (type != null) {
			String sql = "select * from Liaison where " + type + " = ? limit ?,?";
			try {
				conn = CurrentConn.getInstance().getConn();
				pst = conn.prepareStatement(sql);
				pst.setString(1, keyword);
				pst.setInt(2, number);
				pst.setInt(3, size);
				rs = pst.executeQuery();
				while (rs.next()) {
					LiaisonBean lb = new LiaisonBean();
					lb.setLnumber(rs.getString(1));
					lb.setLname(rs.getString(2));
					lb.setUserNumber(rs.getString(3));

					lbs.add(lb);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CurrentConn.getInstance().closeResultSet(rs);
				CurrentConn.getInstance().closePreparedStatement(pst);
				CurrentConn.getInstance().closeConnection(conn);
			}
		}
		return lbs;
	}

	public List<LiaisonBean> searchLiaisonByCondition(String type, String keyword) {
		List<LiaisonBean> lbs = new ArrayList<LiaisonBean>();
		if (type != null) {
			String sql = "select * from Liaison where " + type + " = ?";
			try {
				conn = CurrentConn.getInstance().getConn();
				pst = conn.prepareStatement(sql);
				pst.setString(1, keyword);

				rs = pst.executeQuery();
				while (rs.next()) {
					LiaisonBean lb = new LiaisonBean();
					lb.setLnumber(rs.getString(1));
					lb.setLname(rs.getString(2));
					lb.setUserNumber(rs.getString(3));

					lbs.add(lb);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CurrentConn.getInstance().closeResultSet(rs);
				CurrentConn.getInstance().closePreparedStatement(pst);
				CurrentConn.getInstance().closeConnection(conn);
			}
		}
		return lbs;
	}
}
