package com.ictwsn.dao.client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import com.ictwsn.bean.DeviceBean;
import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import com.ictwsn.util.RandomString;
import com.ictwsn.util.format.PublicDay;

@Repository
public class ClientDaoImpl extends MySQLBaseDao implements ClientDao {
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;

	/**
	 * 终端用户添加设备信息
	 * 添加成功返回1
	 * 添加失败返回0
	 * minor重复返回-1
	 */
	@Override
	public int addDevice(DeviceBean db){
		String sql="select count(0) from device where client_id=? and minor=?";
		int checkResult=this.jt.queryForInt(sql,new Object[]{db.getClientId(),db.getMinor()});
		if(checkResult==0){//等于0代表minor不重复,可以添加
			sql="insert into device(client_id,device_name,uuid,major,minor,title,content,update_time,video_url,image_url,device_info)" +
					"values(?,?,?,?,?,?,?,?,?,?,?)";
			return this.jt.update(sql,new Object[]{db.getClientId(),db.getDeviceName(),db.getUuid(),db.getMajor(),
					db.getMinor(),db.getTitle(),db.getContent(),db.getUpdateTime(),db.getVideoUrl(),db.getImageUrl(),db.getDeviceInfo()}); 
		}else if(checkResult==1){//等于1代表minor重复,不可以添加
			return -1;
		}
		return 0;
	}
	@Override
	public int checkMinorRepeat(int clientId,int minor){
		String sql="select count(0) from device where client_id=? and minor=?";
		return this.jt.queryForInt(sql,new Object[]{clientId,minor});
	}

	@Override
	public boolean deleteDevice(int deviceId){
		int result=this.jt.update("delete from device where device_id=?",new Object[]{deviceId});
		return result>0?true:false;
	}

	@Override
	public List<DeviceBean> searchDevice(int userId,int number,int size,String roleName){
		if(roleName!=null&&roleName.equals("client")){
			return this.clientSearchDevice(userId,number,size);
		}else if(roleName!=null&&roleName.equals("operator")){
			return this.operatorSearchDevice(userId,number,size);
		}else{
			return this.adminerSearchDevice(userId,number,size);
		}
	}
	private List<DeviceBean> adminerSearchDevice(int adminerId,int number,int size){
		List<DeviceBean> dbs=new ArrayList<DeviceBean>();
		String sql="select device_name,device_id,image_url,video_url,uuid,major,minor,update_time,device_info,client_name,operator_name from device,client,operator where client.operator_id=operator.operator_id and device.client_id=client.client_id order by device_id desc limit ?,?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,number);
			pst.setInt(2,size);
			rs=pst.executeQuery();
			while(rs.next()){
				DeviceBean db=new DeviceBean();
				db.setDeviceName(rs.getString(1));
				db.setDeviceId(rs.getInt(2));
				db.setImageUrl(rs.getString(3));
				db.setVideoUrl(rs.getString(4));
				db.setUuid(rs.getString(5));
				db.setMajor(rs.getInt(6));
				db.setMinor(rs.getInt(7));
				db.setUpdateTime(rs.getString(8));
				db.setDeviceInfo(rs.getString(9));
				db.setClientName(rs.getString(10));
				db.setOperatorName(rs.getString(11));
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
	private List<DeviceBean> operatorSearchDevice(int operatorId,int number,int size){
		List<DeviceBean> dbs=new ArrayList<DeviceBean>();
		String sql="select device_name,device_id,image_url,video_url,uuid,major,minor,update_time,device_info,client_name,operator_name from device,operator,client where operator.operator_id=client.operator_id and client.client_id=device.client_id and operator.operator_id=? order by device_id desc limit ?,?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,operatorId);
			pst.setInt(2,number);
			pst.setInt(3,size);
			rs=pst.executeQuery();
			while(rs.next()){
				DeviceBean db=new DeviceBean();
				db.setDeviceName(rs.getString(1));
				db.setDeviceId(rs.getInt(2));
				db.setImageUrl(rs.getString(3));
				db.setVideoUrl(rs.getString(4));
				db.setUuid(rs.getString(5));
				db.setMajor(rs.getInt(6));
				db.setMinor(rs.getInt(7));
				db.setUpdateTime(rs.getString(8));
				db.setDeviceInfo(rs.getString(9));
				db.setClientName(rs.getString(10));
				db.setOperatorName(rs.getString(11));
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
	private List<DeviceBean> clientSearchDevice(int clientId,int number,int size){
		List<DeviceBean> dbs=new ArrayList<DeviceBean>();
		String sql="select device_name,device_id,image_url,video_url,uuid,major,minor,update_time,device_info,client_name,operator_name from device,client,operator where client.operator_id=operator.operator_id and device.client_id=client.client_id and client.client_id=? order by device_id desc limit ?,? ";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,clientId);
			pst.setInt(2,number);
			pst.setInt(3,size);
			rs=pst.executeQuery();
			while(rs.next()){
				DeviceBean db=new DeviceBean();
				db.setDeviceName(rs.getString(1));
				db.setDeviceId(rs.getInt(2));
				db.setImageUrl(rs.getString(3));
				db.setVideoUrl(rs.getString(4));
				db.setUuid(rs.getString(5));
				db.setMajor(rs.getInt(6));
				db.setMinor(rs.getInt(7));
				db.setUpdateTime(rs.getString(8));
				db.setDeviceInfo(rs.getString(9));
				db.setClientName(rs.getString(10));
				db.setOperatorName(rs.getString(11));
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
	public int getDeviceCount(int userId,String roleName){
		if(roleName!=null&&roleName.equals("client")){
			return this.jt.queryForInt("select count(0) from device where client_id=?",new Object[]{userId});
		}else if(roleName!=null&&roleName.equals("operator")){
			return this.jt.queryForInt("select count(0) from device,operator,client where operator.operator_id=client.operator_id and client.client_id=device.client_id and operator.operator_id=?",new Object[]{userId});
		}else{
			return this.jt.queryForInt("select count(0) from device");
		}
	}
	@Override
	public DeviceBean getDeviceById(int deviceId){
		DeviceBean db=new DeviceBean();
		String sql="select device_name,uuid,major,minor,device_info,update_time,title,content,image_url,video_url,client_id,device_id from device where device_id=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,deviceId);
			rs=pst.executeQuery();
			if(rs.next()){
				db.setDeviceName(rs.getString(1));
				db.setUuid(rs.getString(2));
				db.setMajor(rs.getInt(3));
				db.setMinor(rs.getInt(4));
				db.setDeviceInfo(rs.getString(5));
				db.setUpdateTime(rs.getString(6));
				db.setTitle(rs.getString(7));
				db.setContent(rs.getString(8));
				db.setImageUrl(rs.getString(9));
				db.setVideoUrl(rs.getString(10));
				db.setClientId(rs.getInt(11));
				db.setDeviceId(rs.getInt(12));
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
	public int updateDevice(DeviceBean db){
		return this.jt.update("update device set device_name=?,uuid=?,major=?,minor=?,device_info=?,title=?,content=?,video_url=?,image_url=?,update_time=? where device_id=?",
				new Object[]{db.getDeviceName(),db.getUuid(),db.getMajor(),db.getMinor(),db.getDeviceInfo(),db.getTitle(),db.getContent(),db.getVideoUrl(),db.getImageUrl(),db.getUpdateTime(),db.getDeviceId()});
	}

	@Override
	public String getClientUuid(int clientId){
		return (String)this.jt.queryForObject("select operator_uuid from operator,client where client.operator_id=operator.operator_id and client.client_id=?",new Object[]{clientId},String.class);
	}

	@Override
	public int getClientMajor(int clientId){
		return this.jt.queryForInt("select operator_major from operator,client where client.operator_id=operator.operator_id and client.client_id=?",new Object[]{clientId});
	}
	@Override
	public List<DeviceBean> searchDeviceByCondition(String type,String keyword,int userId,String roleName,int number,int size){
		if(roleName!=null&&roleName.equals("client")){
			return this.clientSearchDeviceByCondition(type,keyword,userId,number,size);
		}else if(roleName!=null&&roleName.equals("operator")){
			return this.operatorSearchDeviceByCondition(type,keyword,userId,number,size);
		}else{
			return this.adminerSearchDeviceByCondition(type,keyword,userId,number,size);
		}
	}
	private List<DeviceBean> clientSearchDeviceByCondition(String type,String keyword,int clientId,int number,int size){
		List<DeviceBean> dbs=new ArrayList<DeviceBean>();
		if(type!=null&&type.equals("deviceName")){
			String sql="select device_name,device_id,image_url,video_url,uuid,major,minor,update_time,device_info,client_name,operator_name from device,client,operator where client.operator_id=operator.operator_id and device.client_id=client.client_id and client_id=? and device_name=? limit ?,?";
			try{
				conn=CurrentConn.getInstance().getConn();
				pst=conn.prepareStatement(sql);
				pst.setInt(1,clientId);
				pst.setString(2,keyword);
				pst.setInt(3,number);
				pst.setInt(4,size);
				rs=pst.executeQuery();
				while(rs.next()){
					DeviceBean db=new DeviceBean();
					db.setDeviceName(rs.getString(1));
					db.setDeviceId(rs.getInt(2));
					db.setImageUrl(rs.getString(3));
					db.setVideoUrl(rs.getString(4));
					db.setUuid(rs.getString(5));
					db.setMajor(rs.getInt(6));
					db.setMinor(rs.getInt(7));
					db.setUpdateTime(rs.getString(8));
					db.setDeviceInfo(rs.getString(9));
					db.setClientName(rs.getString(10));
					db.setOperatorName(rs.getString(11));
					dbs.add(db);
				}

			}catch(SQLException e){
				e.printStackTrace();
			}finally{
				CurrentConn.getInstance().closeResultSet(rs);
				CurrentConn.getInstance().closePreparedStatement(pst);
				CurrentConn.getInstance().closeConnection(conn);
			}
		}
		return dbs;
	}
	private List<DeviceBean> operatorSearchDeviceByCondition(String type,String keyword,int operatorId,int number,int size){
		List<DeviceBean> dbs=new ArrayList<DeviceBean>();
		try{
			conn=CurrentConn.getInstance().getConn();
			if(type!=null&&type.equals("deviceName")){
				String sql="select device_name,device_id,image_url,video_url,uuid,major,minor,update_time,device_info,client_name,operator_name from device,operator,client where device.client_id=client.client_id and client.operator_id=operator.operator_id and operator.operator_id=? and device.device_name=? limit ?,?";
				pst=conn.prepareStatement(sql);
				pst.setInt(1,operatorId);
				pst.setString(2,keyword);
			}else if(type!=null&&type.equals("clientName")){
				String sql="select device_name,device_id,image_url,video_url,uuid,major,minor,update_time,device_info,client_name,operator_name from device,operator,client where device.client_id=client.client_id and client.operator_id=operator.operator_id and operator.operator_id=? and client.client_name=? limit ?,?";
				pst=conn.prepareStatement(sql);
				pst.setInt(1,operatorId);
				pst.setString(2,keyword);
			}
			pst.setInt(3,number);
			pst.setInt(4,size);
			rs=pst.executeQuery();
			while(rs.next()){
				DeviceBean db=new DeviceBean();
				db.setDeviceName(rs.getString(1));
				db.setDeviceId(rs.getInt(2));
				db.setImageUrl(rs.getString(3));
				db.setVideoUrl(rs.getString(4));
				db.setUuid(rs.getString(5));
				db.setMajor(rs.getInt(6));
				db.setMinor(rs.getInt(7));
				db.setUpdateTime(rs.getString(8));
				db.setDeviceInfo(rs.getString(9));
				db.setClientName(rs.getString(10));
				db.setOperatorName(rs.getString(11));
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
	private List<DeviceBean> adminerSearchDeviceByCondition(String type,String keyword,int clientId,int number,int size){
		List<DeviceBean> dbs=new ArrayList<DeviceBean>();
		try{
			conn=CurrentConn.getInstance().getConn();
			if(type!=null&&type.equals("deviceName")){
				String sql="select device_name,device_id,image_url,video_url,uuid,major,minor,update_time,device_info,client_name,operator_name from device,operator,client where device.client_id=client.client_id and client.operator_id=operator.operator_id and device_name=? limit ?,?";
				pst=conn.prepareStatement(sql);
				pst.setString(1,keyword);
			}else if(type!=null&&type.equals("clientName")){
				String sql="select device_name,device_id,image_url,video_url,uuid,major,minor,update_time,device_info,client_name,operator_name from device,operator,client where device.client_id=client.client_id and client.operator_id=operator.operator_id and client.client_name=? limit ?,?";
				pst=conn.prepareStatement(sql);
				pst.setString(1,keyword);
			}else if(type!=null&&type.equals("operatorName")){
				String sql="select device_name,device_id,image_url,video_url,uuid,major,minor,update_time,device_info,client_name,operator_name from device,operator,client where device.client_id=client.client_id and client.operator_id=operator.operator_id and operator.operator_name=? limit ?,?";
				pst=conn.prepareStatement(sql);
				pst.setString(1,keyword);
			}
			pst.setInt(2,number);
			pst.setInt(3,size);
			rs=pst.executeQuery();
			while(rs.next()){
				DeviceBean db=new DeviceBean();
				db.setDeviceName(rs.getString(1));
				db.setDeviceId(rs.getInt(2));
				db.setImageUrl(rs.getString(3));
				db.setVideoUrl(rs.getString(4));
				db.setUuid(rs.getString(5));
				db.setMajor(rs.getInt(6));
				db.setMinor(rs.getInt(7));
				db.setUpdateTime(rs.getString(8));
				db.setDeviceInfo(rs.getString(9));
				db.setClientName(rs.getString(10));
				db.setOperatorName(rs.getString(11));
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
	public int getDeviceCountByCondition(String type, String keyword,int userId, String roleName) {
		if (roleName != null && roleName.equals("client")) {
			if (type != null && type.equals("deviceName")) {
				return this.jt.queryForInt("select count(*) from device where client_id=? and device_name=?",new Object[]{userId,keyword});
			}
		} else if (roleName != null && roleName.equals("operator")) {
			if (type != null && type.equals("deviceName")) {
				return this.jt.queryForInt("select count(*) from device,operator,client where operator.operator_id=? and device.device_name=?", new Object[] {userId,keyword});
			} else if (type != null && type.equals("clientName")) {
				return this.jt.queryForInt("select count(*) from device,operator,client where device.client_id=client.client_id and client.operator_id=operator.operator_id and operator.operator_id=? and client.client_name=?", new Object[] {userId,keyword});
			}

		} else {
			if (type != null && type.equals("deviceName")) {
				return this.jt.queryForInt("select count(*) from device where device_name=?", new Object[] { keyword });
			} else if (type != null && type.equals("operatorName")) {
				return this.jt.queryForInt(
						"select count(*) from device,operator,client where device.client_id=client.client_id and client.operator_id=operator.operator_id and operator.operator_name=?",
						new Object[] { keyword });
			} else if (type != null && type.equals("clientName")) {

				return this.jt.queryForInt(
						"select count(*)from device,operator,client where device.client_id=client.client_id and client.operator_id=operator.operator_id and client.client_name=?",
						new Object[] { keyword });
			}
		}
		return -1;
	}
	@Override
	public DeviceBean getDeviceDisplayInfo(String uuid,int major,int minor) {
		DeviceBean db=new DeviceBean();
		String sql="select title,content,image_url,video_url from device where uuid=? and major=? and minor=? ";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,uuid);
			pst.setInt(2,major);
			pst.setInt(3,minor);
			rs=pst.executeQuery();
			if(rs.next()){				
				db.setTitle(rs.getString(1));
				db.setContent(rs.getString(2));
				db.setImageUrl(rs.getString(3));
				db.setVideoUrl(rs.getString(4));

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
	PublicDay pd=new PublicDay();
	@Override
	public DeviceBean test() {
		DeviceBean db=new DeviceBean();
		String sql="select * from device where device_name like '%"+RandomString.generateString(1)+"%' and title like '%"+RandomString.generateString(1)+"%' and content like '%"+RandomString.generateString(1)+"%' and update_time > '1970-01-01 00:00:00' and update_time < ? order by update_time desc limit 0,10";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,pd.getNowDate());
			rs=pst.executeQuery();
			if(rs.next()){				
				db.setTitle(rs.getString("title"));
				db.setContent(rs.getString("content"));
				db.setImageUrl(rs.getString("image_url"));
				db.setVideoUrl(rs.getString("video_url"));
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



}
