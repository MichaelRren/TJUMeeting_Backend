package com.ictwsn.bean;

//酒店房间信息类
public class HotelRoomBean {
	private int hid; //id 自增
	private String hname; //酒店名称，与Hotel表中的Hname对应
	private String htype; //房型
	private int hprice;  //价格
	private int hnumber; //余量

	public int getHid() {
		return hid;
	}
	public void setHid(int hid) {
		this.hid = hid;
	}
	public String getHname() {
		return hname;
	}
	public void setHname(String hname) {
		this.hname = hname;
	}
	public String getHtype() {
		return htype;
	}
	public void setHtype(String htype) {
		this.htype = htype;
	}
	public int getHprice() {
		return hprice;
	}
	public void setHprice(int hprice) {
		this.hprice = hprice;
	}
	public int getHnumber() {
		return hnumber;
	}
	public void setHnumber(int hnumber) {
		this.hnumber = hnumber;
	}
	

}
