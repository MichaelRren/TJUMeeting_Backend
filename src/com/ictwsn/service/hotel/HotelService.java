package com.ictwsn.service.hotel;

import java.util.List;

import com.ictwsn.bean.HotelBean;
import com.ictwsn.bean.HotelRoomBean;

public interface HotelService {
	public int addHotel(HotelBean hb);
	public boolean deleteHotel(String hname);
	public int updateHotel(HotelBean hb);
	public List<HotelBean> searchHotel();
	public HotelBean getHurlByHname(String hname);

	/**
	 * 自增
	 */
	public int getHotelCount();									//酒店数目
	public List<HotelBean> searchHotel(int number, int size);	//分页功能的酒店信息查询


//	public int setUserHotel(HotelRoomBean userRoom);
//	public int setUserHotel(List<HotelRoomBean> userRooms);
}
