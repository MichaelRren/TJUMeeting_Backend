package com.ictwsn.dao.hotel;

import com.ictwsn.bean.HotelRoomBean;
import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HotelRoomDaoImpl extends MySQLBaseDao implements HotelRoomDao{

	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;
	@Override
	public int addHotelRoom(HotelRoomBean hrb) {
		String sql="select count(0) from HotelRoom where Hname=? and Htype=?";
		int checkResult=this.jt.queryForInt(sql,new Object[]{hrb.getHname(),hrb.getHtype()});
		if(checkResult==0){//等于0代表不重复,可以添加
			sql="insert into HotelRoom(Hname,Htype,Hprice,Hnumber) values(?,?,?,?)";
			return this.jt.update(sql,new Object[]{hrb.getHname(),hrb.getHtype(),hrb.getHprice(),hrb.getHnumber()});
		}else if(checkResult==1){//等于1代表minor重复,不可以添加
			return -1;
		}
		return 0;
	}

	@Override
	public boolean deleteHotelRoom(int hid) {
		int result=this.jt.update("delete from HotelRoom where Hid=?",new Object[]{hid});
		return result>0?true:false;
	}

	@Override
	public int updateHotelRoom(HotelRoomBean hrb) {
		return this.jt.update("update HotelRoom set Hname=?,Htype=?,Hprice=?,Hnumber=? where Hid=?",
				new Object[]{hrb.getHname(),hrb.getHtype(),hrb.getHprice(),hrb.getHnumber(), hrb.getHid()});
	}

	@Override
	public List<HotelRoomBean> searchHotelRoom() {
		List<HotelRoomBean> hrbs=new ArrayList<HotelRoomBean>();
		String sql="select * from HotelRoom";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			rs=pst.executeQuery();
			//第一版使用if
			while(rs.next()){
				HotelRoomBean hrb = new HotelRoomBean();
				hrb.setHid(rs.getInt(1));
				hrb.setHname(rs.getString(2));
				hrb.setHtype(rs.getString(3));
				hrb.setHprice(rs.getInt(4));
				hrb.setHnumber(rs.getInt(5));
				hrbs.add(hrb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return hrbs;
	}

	public List<String> listHtypeByHname(String hname){
		List<String> list = new ArrayList<String>() ;
		String sql = "select Htype,Hnumber from HotelRoom where Hname=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,hname);
			rs=pst.executeQuery();
			while(rs.next()){
				int hnumber = rs.getInt("Hnumber");
				if(hnumber > 0){
					String htype = rs.getString("Htype");
					list.add(htype);
				}
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return list;
	}

	/**
	 *酒店房间减一
	 * @param hname 酒店名称
	 * @param htype 酒店房型
	 * @return 返回-1表示没有房间，返回0表示出错，返回1表示成功。
	 */
	public int consumeHnumber(String hname, String htype){
		String sql = "select Hnumber from HotelRoom where Hname=? and Htype=?";
		int hnumber = 0;
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,hname);
			pst.setString(2,htype);
			rs=pst.executeQuery();

			if(rs.next()){
				hnumber = rs.getInt("Hnumber");
			}
			if (hnumber == 0){
				return -1;
			}else{
				hnumber = hnumber -1;
				sql = "update HotelRoom set Hnumber=? where Hname=? and Htype=?";
				return jt.update(sql, new Object[]{hnumber,hname,htype});
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return 0;
	}

	/**
	 * 酒店房间加一
	 * @param hname
	 * @param htype
	 * @return
	 */
	public int resumeHnumber(String hname,String htype){
		String sql = "select Hnumber from HotelRoom where Hname=? and Htype=?";
		int hnumber = 0;
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,hname);
			pst.setString(2,htype);
			rs=pst.executeQuery();

			if(rs.next()){
				hnumber = rs.getInt("Hnumber");
			}
			hnumber = hnumber +1;
			sql = "update HotelRoom set Hnumber=? where Hname=? and Htype=?";
			return jt.update(sql, new Object[]{hnumber,hname,htype});
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return 0;
	}


	/**
	 * 新增，按页查询
	 * @param number
	 * @param size
	 * @return
	 */
	@Override
	public List<HotelRoomBean> searchHotelRoom(int number, int size) {
		List<HotelRoomBean> hrs=new ArrayList<HotelRoomBean>();
		String sql="select * from HotelRoom limit ?,?";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,number);
			pst.setInt(2,size);
			rs=pst.executeQuery();
			while(rs.next()) {
				HotelRoomBean hrb=new HotelRoomBean();
				hrb.setHid(rs.getInt(1));
				hrb.setHname(rs.getString(2));
				hrb.setHtype(rs.getString(3));
				hrb.setHprice(rs.getInt(4));
				hrb.setHnumber(rs.getInt(5));

				hrs.add(hrb);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return hrs;
	}

	/**
	 * 新增，按id查询房间
	 */
	@Override
	public HotelRoomBean searchHotelRoomById(int hid) {
		HotelRoomBean hrb = new HotelRoomBean();

		String sql="select * from HotelRoom where hid=?";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1, hid);

			rs=pst.executeQuery();
			while(rs.next()) {
				hrb.setHid(rs.getInt(1));
				hrb.setHname(rs.getString(2));
				hrb.setHtype(rs.getString(3));
				hrb.setHprice(rs.getInt(4));
				hrb.setHnumber(rs.getInt(5));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return hrb;
	}

	/**
		新增条件查询
	 */
	public List<HotelRoomBean> searchHotelRoomByCondition(String type, String keyword, int number, int size) {
		List<HotelRoomBean> hrb = new ArrayList<HotelRoomBean>();
		if (type != null) {
			String sql = "select * from HotelRoom where " + type + " = ? limit ?,?";
			try {
				conn = CurrentConn.getInstance().getConn();
				pst = conn.prepareStatement(sql);
				pst.setString(1, keyword);
				pst.setInt(2, number);
				pst.setInt(3, size);

				rs = pst.executeQuery();
				while (rs.next()) {

					HotelRoomBean hb = new HotelRoomBean();

					hb.setHid(rs.getInt(1));
					hb.setHname(rs.getString(2));
					hb.setHtype(rs.getString(3));
					hb.setHprice(rs.getInt(4));
					hb.setHnumber(rs.getInt(5));

					hrb.add(hb);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CurrentConn.getInstance().closeResultSet(rs);
				CurrentConn.getInstance().closePreparedStatement(pst);
				CurrentConn.getInstance().closeConnection(conn);
			}
		}
		return hrb;
	}

	public int searchHotelRoomByConditionCount(String type, String keyword) {
		List<HotelRoomBean> hrb = new ArrayList<HotelRoomBean>();
		if (type != null) {
			String sql = "select * from HotelRoom where " + type + " = ?";
			try {
				conn = CurrentConn.getInstance().getConn();
				pst = conn.prepareStatement(sql);
				pst.setString(1, keyword);

				rs = pst.executeQuery();
				while (rs.next()) {

					HotelRoomBean hb = new HotelRoomBean();

					hb.setHid(rs.getInt(1));
					hb.setHname(rs.getString(2));
					hb.setHtype(rs.getString(3));
					hb.setHprice(rs.getInt(4));
					hb.setHnumber(rs.getInt(5));

					hrb.add(hb);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CurrentConn.getInstance().closeResultSet(rs);
				CurrentConn.getInstance().closePreparedStatement(pst);
				CurrentConn.getInstance().closeConnection(conn);
			}
		}
		return hrb.size();
	}
}
