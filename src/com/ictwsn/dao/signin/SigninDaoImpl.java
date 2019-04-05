package com.ictwsn.dao.signin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import com.ictwsn.bean.SigninBean;

@Repository
public class SigninDaoImpl extends MySQLBaseDao implements SigninDao{

	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;

	@Override
	public int addSignin(SigninBean sb) {
		String sql="select count(0) from Signin where UserNumber=?";
		int checkResult=this.jt.queryForInt(sql,new Object[]{sb.getUserNumber()});
		if(checkResult==0){//等于0代表不重复,可以添加
			sql="insert into Signin (UserNumber,Status,SigninTime) values(?,?,?)";
			return this.jt.update(sql,new Object[]{sb.getUserNumber(),sb.isStatus(),sb.getSignintime()}); 
		}else if(checkResult==1){//等于1代表minor重复,不可以添加
			return -1;
		}
		return 0;
	}

	@Override
	public boolean deleteSignin(String userNumber) {
		int result=this.jt.update("delete from Signin where UserNumber=?",new Object[]{userNumber});
		return result>0?true:false;
	}

	@Override
	public List<SigninBean> searchSignin(int number, int size) {
		List<SigninBean> sbs=new ArrayList<SigninBean>();
		String sql="select * from Signin limit ?,?";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,number);
			pst.setInt(2,size);
			rs=pst.executeQuery();
			while(rs.next()) {
				SigninBean sb= new SigninBean();
				sb.setUserNumber(rs.getString(1));
				sb.setStatus(rs.getBoolean(2));
				sb.setSignintime(rs.getDate(3));
				sbs.add(sb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return sbs;
	}

	public int getSigninCount(){
		return this.jt.queryForInt("select count(0) from Signin");
	}


	@Override
	public int checkSignin(String userNumber) {
		return this.jt.queryForInt("select count(0) from Signin where UserNumber=?",new Object[]{userNumber});
	}

	@Override
	public SigninBean searchSigninByuserNumber(String userNumber) {
		SigninBean sb= new SigninBean();
		String sql="select * from Signin where UserNumber=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,userNumber);
			rs=pst.executeQuery();
			if(rs.next()){
				sb.setUserNumber(rs.getString(1));
				sb.setStatus(rs.getBoolean(2));
				sb.setSignintime(rs.getTimestamp(3));
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return sb;
	}


	@Override
	public List<SigninBean> searchSigninByStatus(boolean status) {
		List<SigninBean> sbs=new ArrayList<SigninBean>();
		String sql="select * from Signin where status=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setBoolean(1,status);
			rs=pst.executeQuery();
			while(rs.next()){
				SigninBean sb = new SigninBean();
				sb.setUserNumber(rs.getString(1));
				sb.setStatus(rs.getBoolean(2));
				sb.setSignintime(rs.getTimestamp(3));
				sbs.add(sb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return sbs;
	}

	@Override
	public int updateSignin(SigninBean sb) {
		return this.jt.update("update Signin set Status=?,SigninTime=? where UserNumber=?",
				new Object[]{sb.isStatus(),sb.getSignintime(),sb.getUserNumber()});
	}

}
