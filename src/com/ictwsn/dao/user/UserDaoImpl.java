package com.ictwsn.dao.user;

import com.ictwsn.bean.UserBean;
import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import org.springframework.stereotype.Repository;

import java.util.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


@Repository
public class UserDaoImpl extends MySQLBaseDao implements UserDao{
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;


	/**
	 * 添加用户信息
	 * 添加成功返回1
	 * 添加失败返回0
	 * 手机号重复返回-1
	 */
	@Override
	public int addUser(UserBean ub) {
		String sql="select count(0) from User where UserNumber=?";
		int checkResult=this.jt.queryForInt(sql,new Object[]{ub.getUserNumber()});
		if(checkResult==0){//等于0代表不重复,可以添加
			sql="insert into User(UserNumber,UserName,UserRole,UserPhoto,Sex,WorkerNumber,UserSorts,WorkPlace,Position,Title,Email,ArrivalDate," +
                    "ArrivalNumber,ArrivalTime,ArrivalStation,ReturnDate,ReturnNumber,ReturnTime,ReturnStation,Hname,Htype,Dinner,Remark,CheckinTime,CheckoutTime) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			java.sql.Date adate = null;
			if(!ub.getArrivalDate().equals("")){
				adate=strToDate(ub.getArrivalDate());
			}
			java.sql.Time atime = null;
			if(!ub.getArrivalTime().equals("")){
				atime=strToTime(ub.getArrivalTime());
			}
			java.sql.Date rdate = null;
			if(!ub.getReturnDate().equals("")){
				rdate=strToDate(ub.getReturnDate());
			}
			java.sql.Time rtime = null;
			if(!ub.getReturnTime().equals("")){
				rtime=strToTime(ub.getReturnTime());
			}

			int userresult = this.jt.update(sql,new Object[]{ub.getUserNumber(),ub.getUserName(),ub.getUserRole(),ub.getUserPhoto(),ub.getSex(),
                    ub.getWorkerNumber(),ub.getUserSorts(),ub.getWorkPlace(),ub.getPosition(),ub.getTitle(),ub.getEmail(),adate,
                    ub.getArrivalNumber(),atime,ub.getArrivalStation(),rdate, ub.getReturnNumber(),rtime,ub.getReturnStation(),
					ub.getHname(),ub.getHtype(),ub.getDinner(),ub.getRemark(),strToDate(ub.getCheckin()),strToDate(ub.getCheckout())});
			if(userresult == 1){
//				if(ub.getWorkerNumber() != null) {
//					String userRole = "2";
//					this.jt.update("insert into User(UserNumber,UserRole) values (?,?)", new Object[] {ub.getWorkerNumber(),userRole});//将随行人员插入user表
//				}
				return this.jt.update("insert into Signin(UserNumber) values (?)", new Object[] {ub.getUserNumber()}); //将手机号插入签到表-Signin
			}else{
				return 0;
			}
		}else if(checkResult==1){//等于1代表重复,不可以添加
			return -1;
		}
		return 0;
	}

	@Override
	public int deleteUser(String userNumber) {
		int result=this.jt.update("delete from User where UserNumber=?",new Object[]{userNumber});
		return result;
	}

	public int getUserConut(){
		return this.jt.queryForInt("select count(0) from User");
	}

	@Override
	public List<UserBean> searchUser(int number, int size) {
		List<UserBean> ubs=new ArrayList<UserBean>();
		String sql="select * from User Order By ArrivalDate ASC,ArrivalTime ASC limit ?,?";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,number);
			pst.setInt(2,size);
			rs=pst.executeQuery();
			while(rs.next()) {
				UserBean ub=new UserBean();
				ub.setUserNumber(rs.getString(1));
				ub.setUserName(rs.getString(2));
				ub.setUserRole(rs.getString(3));
				ub.setUserPhoto(rs.getString(4));
				ub.setSex(rs.getString(5));
				ub.setWorkerNumber(rs.getString(6));
				ub.setUserSorts(rs.getString(7));
				ub.setWorkPlace(rs.getString(8));
				ub.setPosition(rs.getString(9));
				ub.setTitle(rs.getString(10));
				ub.setEmail(rs.getString(11));

				if(rs.getDate(12) != null) {
					ub.setArrivalDate(rs.getDate(12).toString());
				}

				ub.setArrivalNumber(rs.getString(13));

				if(rs.getTime(14) != null) {
					ub.setArrivalTime(rs.getTime(14).toString());
				}

				ub.setArrivalStation(rs.getString(15));

				if(rs.getDate(16) != null) {
					ub.setReturnDate(rs.getDate(16).toString());
				}

				ub.setReturnNumber(rs.getString(17));

				if(rs.getTime(18)!= null) {
					ub.setReturnTime(rs.getTime(18).toString());
				}
				ub.setReturnStation(rs.getString(19));

				ub.setHname(rs.getString(20));
				ub.setHtype(rs.getString(21));
				ub.setDinner(rs.getString(22));
				ub.setRemark(rs.getString(23));
				ub.setCheckin(rs.getString(24));
				ub.setCheckout(rs.getString(25));
				ubs.add(ub);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return ubs;
	}

	public List<UserBean> searchUserASCReturnTime(int number, int size){
		List<UserBean> ubs=new ArrayList<UserBean>();
		String sql="select * from User Order By ReturnDate ASC,ReturnTime ASC limit ?,?";
		try {
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setInt(1,number);
			pst.setInt(2,size);
			rs=pst.executeQuery();
			while(rs.next()) {
				UserBean ub=new UserBean();
				ub.setUserNumber(rs.getString(1));
				ub.setUserName(rs.getString(2));
				ub.setUserRole(rs.getString(3));
				ub.setUserPhoto(rs.getString(4));
				ub.setSex(rs.getString(5));
				ub.setWorkerNumber(rs.getString(6));
				ub.setUserSorts(rs.getString(7));
				ub.setWorkPlace(rs.getString(8));
				ub.setPosition(rs.getString(9));
				ub.setTitle(rs.getString(10));
				ub.setEmail(rs.getString(11));

				if(rs.getDate(12) != null) {
					ub.setArrivalDate(rs.getDate(12).toString());
				}

				ub.setArrivalNumber(rs.getString(13));

				if(rs.getTime(14) != null) {
					ub.setArrivalTime(rs.getTime(14).toString());
				}

				ub.setArrivalStation(rs.getString(15));

				if(rs.getDate(16) != null) {
					ub.setReturnDate(rs.getDate(16).toString());
				}

				ub.setReturnNumber(rs.getString(17));

				if(rs.getTime(18)!= null) {
					ub.setReturnTime(rs.getTime(18).toString());
				}
				ub.setReturnStation(rs.getString(19));

				ub.setHname(rs.getString(20));
				ub.setHtype(rs.getString(21));
				ub.setDinner(rs.getString(22));
				ub.setRemark(rs.getString(23));
				ub.setCheckin(rs.getString(24));
				ub.setCheckout(rs.getString(25));
				ubs.add(ub);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return ubs;
	}

	@Override
	public int updateUser(UserBean ub) {
			java.sql.Date adate = null;
			if(!ub.getArrivalDate().equals("")){
				adate=strToDate(ub.getArrivalDate());
			}
			java.sql.Time atime = null;
			if(!ub.getArrivalTime().equals("")){
				atime=strToTime(ub.getArrivalTime());
			}
			java.sql.Date rdate = null;
			if(!ub.getReturnDate().equals("")){
				rdate=strToDate(ub.getReturnDate());
			}
			java.sql.Time rtime = null;
			if(!ub.getReturnTime().equals("")){
				rtime=strToTime(ub.getReturnTime());
			}
		int result = this.jt.update("update User set UserName=?,UserRole=?,UserPhoto=?,Sex=?,WorkerNumber=?,UserSorts=?,WorkPlace=?,Position=?," +
						"Title=?,Email=?,ArrivalDate=?,ArrivalNumber=?,ArrivalTime=?,ArrivalStation=?,ReturnDate=?,ReturnNumber=?," +
						"ReturnTime=?,ReturnStation=?,Hname=?,Htype=?,Dinner=?,Remark=?,CheckinTime=?,CheckoutTime=? where UserNumber=?",
				new Object[]{ub.getUserName(),ub.getUserRole(),ub.getUserPhoto(),ub.getSex(),ub.getWorkerNumber(),ub.getUserSorts(),ub.getWorkPlace(),
						ub.getPosition(),ub.getTitle(),ub.getEmail(),adate,ub.getArrivalNumber(), atime,ub.getArrivalStation(),
						rdate,ub.getReturnNumber(),rtime,ub.getReturnStation(),ub.getHname(),ub.getHtype(),ub.getDinner(),ub.getRemark(),
						strToDate(ub.getCheckin()),strToDate(ub.getCheckout()),
						ub.getUserNumber()});
		return result;
	}

	/**
	 * 新增更新宾馆信息
	 * @param userBean
	 * @return
	 */
	@Override
	public int updateUserHotel(UserBean userBean) {

		int result = this.jt.update("update User set Hname=?,Htype=? where UserNumber=?",
				new Object[]{userBean.getHname(), userBean.getHtype(), userBean.getUserNumber()});

		return result;
	}


	@Override
	public int checkUserNumber(String userNumber) {
	    String sql = "select count(0) from User where UserNumber=?";
		int result = this.jt.queryForInt(sql, new Object[]{userNumber});
		return result;
	}
	
    public static java.sql.Date strToDate(String strDate) {  
        String str = strDate;  
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
        java.util.Date d = null;  
        try {  
            d = format.parse(str);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        java.sql.Date date = new java.sql.Date(d.getTime());  
        return date;  
    } 
    
    public static java.sql.Time strToTime(String strTime) {
        String str = strTime;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        java.util.Date d = null;  
        try {  
            d = format.parse(str);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        java.sql.Time time = new java.sql.Time(d.getTime());  
        return time.valueOf(str);  
    }

	@Override
	public UserBean searchUserByUserNumber(String userNumber) {
		UserBean ub=new UserBean();
		String sql="select * from User where UserNumber=?";
		try{
			conn=CurrentConn.getInstance().getConn();
			pst=conn.prepareStatement(sql);
			pst.setString(1,userNumber);
			rs=pst.executeQuery();
			if(rs.next()){
				ub.setUserNumber(rs.getString(1));
				ub.setUserName(rs.getString(2));
				ub.setUserRole(rs.getString(3));
				ub.setUserPhoto(rs.getString(4));
				ub.setSex(rs.getString(5));
				ub.setWorkerNumber(rs.getString(6));
				ub.setUserSorts(rs.getString(7));
				ub.setWorkPlace(rs.getString(8));
				ub.setPosition(rs.getString(9));
				ub.setTitle(rs.getString(10));
				ub.setEmail(rs.getString(11));

				if(rs.getDate(12) != null) {
					ub.setArrivalDate(rs.getDate(12).toString());
				}

				ub.setArrivalNumber(rs.getString(13));

				if(rs.getTime(14) != null) {
					ub.setArrivalTime(rs.getTime(14).toString());
				}

				ub.setArrivalStation(rs.getString(15));

				if(rs.getDate(16) != null) {
					ub.setReturnDate(rs.getDate(16).toString());
				}

				ub.setReturnNumber(rs.getString(17));

				if(rs.getTime(18)!= null) {
					ub.setReturnTime(rs.getTime(18).toString());
				}
				ub.setReturnStation(rs.getString(19));

				ub.setHname(rs.getString(20));
				ub.setHtype(rs.getString(21));
				ub.setDinner(rs.getString(22));
				ub.setRemark(rs.getString(23));
				ub.setCheckin(rs.getString(24));
				ub.setCheckout(rs.getString(25));
			}

		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return ub;
	}

	public List<UserBean> searchUserByCondition(String type, String keyword, int number, int size) {
		List<UserBean> ubs = new ArrayList<UserBean>();
		if (type != null) {
			String sql = "select * from User where " + type + " = ? Order By ArrivalDate ASC,ArrivalTime ASC limit ?,?";
			try {
				conn = CurrentConn.getInstance().getConn();
				pst = conn.prepareStatement(sql);
				pst.setString(1, keyword);
				pst.setInt(2, number);
				pst.setInt(3, size);
				rs = pst.executeQuery();
				while (rs.next()) {
					UserBean ub = new UserBean();
					ub.setUserNumber(rs.getString(1));
					ub.setUserName(rs.getString(2));
					ub.setUserRole(rs.getString(3));
					ub.setUserPhoto(rs.getString(4));
					ub.setSex(rs.getString(5));
					ub.setWorkerNumber(rs.getString(6));
					ub.setUserSorts(rs.getString(7));
					ub.setWorkPlace(rs.getString(8));
					ub.setPosition(rs.getString(9));
					ub.setTitle(rs.getString(10));
					ub.setEmail(rs.getString(11));

					if(rs.getDate(12) != null) {
						ub.setArrivalDate(rs.getDate(12).toString());
					}

					ub.setArrivalNumber(rs.getString(13));

					if(rs.getTime(14) != null) {
						ub.setArrivalTime(rs.getTime(14).toString());
					}

					ub.setArrivalStation(rs.getString(15));

					if(rs.getDate(16) != null) {
						ub.setReturnDate(rs.getDate(16).toString());
					}

					ub.setReturnNumber(rs.getString(17));

					if(rs.getTime(18)!= null) {
						ub.setReturnTime(rs.getTime(18).toString());
					}
					ub.setReturnStation(rs.getString(19));

					ub.setHname(rs.getString(20));
					ub.setHtype(rs.getString(21));
					ub.setDinner(rs.getString(22));
					ub.setRemark(rs.getString(23));
					ub.setCheckin(rs.getString(24));
					ub.setCheckout(rs.getString(25));
					ubs.add(ub);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CurrentConn.getInstance().closeResultSet(rs);
				CurrentConn.getInstance().closePreparedStatement(pst);
				CurrentConn.getInstance().closeConnection(conn);
			}
		}
		return ubs;
	}

	public int setUserHotel(UserBean ub){
		int result = this.jt.update("update User set Hname=?,Htype=?where UserNumber=?",
				new Object[]{ub.getHname(),ub.getHtype(),
						ub.getUserNumber()});
		return result;
	}

    /**
     * 导出Excel条件查找
     * @param type
     * @param keyword
     * @return
     */
	public List<UserBean> searchUserByCondition(String type, String keyword) {
		List<UserBean> ubs = new ArrayList<UserBean>();
		if (type != null) {
			String sql = "select * from User where " + type + " = ? Order By ArrivalDate ASC,ArrivalTime ASC";
			try {
				conn = CurrentConn.getInstance().getConn();
				pst = conn.prepareStatement(sql);
				pst.setString(1, keyword);
				rs = pst.executeQuery();
				while (rs.next()) {
					UserBean ub = new UserBean();
					ub.setUserNumber(rs.getString(1));
					ub.setUserName(rs.getString(2));
					ub.setUserRole(rs.getString(3));
					ub.setUserPhoto(rs.getString(4));
					ub.setSex(rs.getString(5));
					ub.setWorkerNumber(rs.getString(6));
					ub.setUserSorts(rs.getString(7));
					ub.setWorkPlace(rs.getString(8));
					ub.setPosition(rs.getString(9));
					ub.setTitle(rs.getString(10));
					ub.setEmail(rs.getString(11));

					if(rs.getDate(12) != null) {
						ub.setArrivalDate(rs.getDate(12).toString());
					}

					ub.setArrivalNumber(rs.getString(13));

					if(rs.getTime(14) != null) {
						ub.setArrivalTime(rs.getTime(14).toString());
					}

					ub.setArrivalStation(rs.getString(15));

					if(rs.getDate(16) != null) {
						ub.setReturnDate(rs.getDate(16).toString());
					}

					ub.setReturnNumber(rs.getString(17));

					if(rs.getTime(18)!= null) {
						ub.setReturnTime(rs.getTime(18).toString());
					}
					ub.setReturnStation(rs.getString(19));

					ub.setHname(rs.getString(20));
					ub.setHtype(rs.getString(21));
					ub.setDinner(rs.getString(22));
					ub.setRemark(rs.getString(23));
					ub.setCheckin(rs.getString(24));
					ub.setCheckout(rs.getString(25));
					ubs.add(ub);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				CurrentConn.getInstance().closeResultSet(rs);
				CurrentConn.getInstance().closePreparedStatement(pst);
				CurrentConn.getInstance().closeConnection(conn);
			}
		}
		return ubs;
	}

    /**
     * 新增，查询所有用户，报到组导出excel使用
     * @return
     */
    @Override
    public List<UserBean> searchUser() {
        List<UserBean> ubs=new ArrayList<UserBean>();
        String sql="select * from User Order By ArrivalDate ASC,ArrivalTime ASC";
        try {
            conn=CurrentConn.getInstance().getConn();
            pst=conn.prepareStatement(sql);
            rs=pst.executeQuery();
            while(rs.next()) {
                UserBean ub=new UserBean();
                ub.setUserNumber(rs.getString(1));
                ub.setUserName(rs.getString(2));
                ub.setUserRole(rs.getString(3));
                ub.setUserPhoto(rs.getString(4));
                ub.setSex(rs.getString(5));
                ub.setWorkerNumber(rs.getString(6));
                ub.setUserSorts(rs.getString(7));
                ub.setWorkPlace(rs.getString(8));
                ub.setPosition(rs.getString(9));
                ub.setTitle(rs.getString(10));
                ub.setEmail(rs.getString(11));

                if(rs.getDate(12) != null) {
                    ub.setArrivalDate(rs.getDate(12).toString());
                }

                ub.setArrivalNumber(rs.getString(13));

                if(rs.getTime(14) != null) {
                    ub.setArrivalTime(rs.getTime(14).toString());
                }

                ub.setArrivalStation(rs.getString(15));

                if(rs.getDate(16) != null) {
                    ub.setReturnDate(rs.getDate(16).toString());
                }

                ub.setReturnNumber(rs.getString(17));

                if(rs.getTime(18)!= null) {
                    ub.setReturnTime(rs.getTime(18).toString());
                }
                ub.setReturnStation(rs.getString(19));

                ub.setHname(rs.getString(20));
                ub.setHtype(rs.getString(21));
                ub.setDinner(rs.getString(22));
                ub.setRemark(rs.getString(23));
                ub.setCheckin(rs.getString(24));
                ub.setCheckout(rs.getString(25));
                ubs.add(ub);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            CurrentConn.getInstance().closeResultSet(rs);
            CurrentConn.getInstance().closePreparedStatement(pst);
            CurrentConn.getInstance().closeConnection(conn);
        }
        return ubs;
    }

	@Override
	public int getVIPCount() {
		return this.jt.queryForInt("select count(*) from User  where UserRole=1");
	}

	@Override
	public int getUserCount() {
		return this.jt.queryForInt("select count(*) from User  where UserRole=0");
	}

}
