package com.ictwsn.service.hotel;

import com.ictwsn.bean.HotelBean;
import com.ictwsn.bean.HotelRoomBean;

import java.util.List;

public interface HotelRoomService {
	public int addHotelRoom(HotelRoomBean hrb);
	public boolean deleteHotelRoom(int hid);
	public int updateHotelRoom(HotelRoomBean hrb);
	public List<HotelRoomBean> searchHotelRoom();
	public List<String> listHtypeByHname(String hanme); //根据酒店名称查找酒店房间类型
	public int consumeHnumber(String hname, String htype); //酒店房间减一
	public int resumeHnumber(String hname, String htype); //酒店房间加一

	/**
	 * 新增
	 */
	public List<HotelRoomBean> searchHotelRoom(int number, int size);
	public HotelRoomBean searchHotelRoomById(int hid);

//	public int setUserHotel(HotelRoomBean userRoom);
//	public int setUserHotel(List<HotelRoomBean> userRooms);
}
