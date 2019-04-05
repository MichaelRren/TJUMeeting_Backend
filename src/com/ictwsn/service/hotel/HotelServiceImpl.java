package com.ictwsn.service.hotel;

import java.util.List;

import javax.annotation.Resource;

import com.ictwsn.bean.HotelRoomBean;
import org.springframework.stereotype.Service;

import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.bean.HotelBean;
import com.ictwsn.dao.hotel.HotelDao;

@Service
public class HotelServiceImpl extends MySQLBaseDao implements HotelService{

	@Resource HotelDao dao;
	@Override
	public int addHotel(HotelBean hb) {
		return dao.addHotel(hb);
	}

	@Override
	public boolean deleteHotel(String hname) {
		return dao.deleteHotel(hname);
	}

	@Override
	public int updateHotel(HotelBean hb) {
		return dao.updateHotel(hb);
	}

	@Override
	public List<HotelBean> searchHotel() {
		return dao.searchHotel();
	}


	public HotelBean getHurlByHname(String hname){
		return dao.getHurlByHname(hname);
	}

	/**
	 * 自增
	 * @return
	 */
	@Override
	public int getHotelCount()
	{
		return dao.getHotelCount();
	}

	@Override
	public List<HotelBean> searchHotel(int number, int size)
	{
		return dao.searchHotel(number, size);
	}

//	public int setUserHotel(HotelRoomBean userRoom){
//		return dao.setUserHotel(userRoom);
//	}
//
//	public int setUserHotel(List<HotelRoomBean> userRooms){
//		return dao.setUserHotel(userRooms);
//	}

}
