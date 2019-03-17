package com.ictwsn.util;

import java.sql.Connection;
import java.sql.PreparedStatement; 
import java.sql.SQLException;

import com.ictwsn.bean.DeviceBean;

public class InsertData {
	private static InsertData instance=null;
	private InsertData(){}
	public static InsertData getInstance(){
		if(instance==null){
			instance=new InsertData();
		}
		return instance;
	}
	private Connection conn = null;
	private PreparedStatement pst = null; 
	
	String randStr="";
	public DeviceBean insert() {
		DeviceBean db=new DeviceBean();
		String sql="insert into device(client_id,device_name,uuid,major,minor,title,content,update_time,video_url,image_url,device_info)" +
				"values(?,?,?,?,?,?,?,?,?,?,?)";
		try{
			conn=CurrentConn.getInstance().getConn();
			for(int i=0;i<10000;i++){
				pst=conn.prepareStatement(sql); 
				randStr=RandomString.generateString(80);
				pst.setInt(1,1);
				pst.setString(2,randStr);
				pst.setString(3,"FDA50693-A4E2-4FB1-AFCF-C6EB07647825");
				pst.setInt(4,19991);
				pst.setInt(5,10001);
				pst.setString(6,randStr);
				pst.setString(7,randStr);
				pst.setString(8,"2017-01-01 00:00:00");
				pst.setString(9, randStr);
				pst.setString(10,randStr);
				pst.setString(11,randStr);
				pst.executeUpdate();
			}
			
		 
		}catch(SQLException e){
			e.printStackTrace();
		}finally{ 
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return db;
	}
 
}
