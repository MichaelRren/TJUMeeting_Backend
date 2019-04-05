package com.ictwsn.dao.hotel;

import com.ictwsn.bean.HotelBean;
import com.ictwsn.bean.HotelRoomBean;

import java.util.List;

public interface HotelRoomDao {
	public int addHotelRoom(HotelRoomBean hrb);
	//id 由 String改为int
	public boolean deleteHotelRoom(int hid);
	public int updateHotelRoom(HotelRoomBean hrb);
	public List<HotelRoomBean> searchHotelRoom();

	public List<String> listHtypeByHname(String hanme);
	public int consumeHnumber(String hname, String htype);
	public int resumeHnumber(String hname, String htype);

	/**
	 * 新增
	 */
	public List<HotelRoomBean> searchHotelRoom(int number, int size);	//分页功能的查询
	public HotelRoomBean searchHotelRoomById(int hid);					//按照id查询room

//	public int setUserHotel(HotelRoomBean userRoom);
//	public int setUserHotel(List<HotelRoomBean> userRooms);
}
