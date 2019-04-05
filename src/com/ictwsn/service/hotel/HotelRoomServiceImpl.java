package com.ictwsn.service.hotel;

import com.ictwsn.bean.HotelBean;
import com.ictwsn.bean.HotelRoomBean;
import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.dao.hotel.HotelRoomDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HotelRoomServiceImpl extends MySQLBaseDao implements HotelRoomService{

	@Resource HotelRoomDao dao;

	@Override
	public int addHotelRoom(HotelRoomBean hrb) {
		return dao.addHotelRoom(hrb);
	}

	@Override
	public boolean deleteHotelRoom(int hid) {
		return dao.deleteHotelRoom(hid);
	}

	@Override
	public int updateHotelRoom(HotelRoomBean hrb) {
		return dao.updateHotelRoom(hrb);
	}

	@Override
	public List<HotelRoomBean> searchHotelRoom() {
		return dao.searchHotelRoom();
	}

	//输入hanme 酒店名称
	//return 剩余数量大于0的房间类型
	public List<String> listHtypeByHname(String hanme){
		return dao.listHtypeByHname(hanme);
	}

	public int consumeHnumber(String hname, String htype){
		return dao.consumeHnumber(hname,htype);
	}

	public int resumeHnumber(String hname,String htype){
		return dao.resumeHnumber(hname,htype);
	}

	/**
	 * 新增
	 */
	public List<HotelRoomBean> searchHotelRoom(int number, int size){return dao.searchHotelRoom(number, size);}

	public HotelRoomBean searchHotelRoomById(int hid){return dao.searchHotelRoomById(hid);}

//	public int setUserHotel(HotelRoomBean userRoom){
//		return dao.setUserHotel(userRoom);
//	}
//
//	public int setUserHotel(List<HotelRoomBean> userRooms){
//		return dao.setUserHotel(userRooms);
//	}

}
