package com.ictwsn.dao.hotel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ictwsn.bean.HotelRoomBean;
import org.springframework.stereotype.Repository;

import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import com.ictwsn.bean.HotelBean;

@Repository
public class HotelDaoImpl extends MySQLBaseDao implements HotelDao{

	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;
	@Override
	public int addHotel(HotelBean hb) {
		String sql="select count(0) from Hotel where Hname=?";
		int checkResult=this.jt.queryForInt(sql,new Object[]{hb.getHname()});
		if(checkResult==0){//等于0代表不重复,可以添加
			sql="insert into Hotel(Hname,Hurl) values(?,?)";
			return this.jt.update(sql,new Object[]{hb.getHname(),hb.getHurl()}); 
		}else if(checkResult==1){//等于1代表minor重复,不可以添加
			return -1;
		}
		return 0;
	}

	@Override
	public boolean deleteHotel(String hname) {
		int result=this.jt.update("delete from Hotel where Hname=?",new Object[]{hname});
		return result>0?true:false;
	}

	@Override
	public int updateHotel(HotelBean hb) {
		return this.jt.update("update Hotel set Hurl=? where Hname=?",
				new Object[]{hb.getHurl(),hb.getHname()});
	}

	@Override
	public List<HotelBean> searchHotel() {
		List<HotelBean> hbs=new ArrayList<HotelBean>();
		String sql="select * from Hotel";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			//修改成while
			while(rs.next()){
				HotelBean hb = new HotelBean();
				hb.setHname(rs.getString(1));
				hb.setHurl(rs.getString(2));
				hbs.add(hb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return hbs;
	}

	public HotelBean getHurlByHname(String hname){
		HotelBean hb = new HotelBean();
		String sql = "select * from Hotel where Hname=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,hname);
			rs=pst.executeQuery();
			if(rs.next()){
				hb.setHname(rs.getString("Hname"));
				hb.setHurl(rs.getString("Hurl"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return hb;
	}

	/**
	 * 自增
	 * @return
	 */
	public int getHotelCount(){return this.jt.queryForInt("select count(0) from Hotel");}

	@Override
	public List<HotelBean> searchHotel(int number, int size) {
		List<HotelBean> hbs=new ArrayList<HotelBean>();
		String sql="select * from Hotel limit ?,?";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,number);
			pst.setInt(2,size);
			rs=pst.executeQuery();
			while(rs.next()) {
				HotelBean hb=new HotelBean();
				hb.setHname(rs.getString(1));
				hb.setHurl(rs.getString(2));

				hbs.add(hb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return hbs;
	}
}
