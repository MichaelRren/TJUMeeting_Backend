package com.ictwsn.dao.operator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import com.ictwsn.bean.ClientBean;
import com.ictwsn.bean.OperatorBean;
import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;

@Repository
public class OperatorDaoImpl extends MySQLBaseDao implements OperatorDao {
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs=null;

	@Override
	/**
	 * 添加终端用户,如果用户名已存在,返回-1
	 * 否则返回insert语句执行结果
	 */
	public int addClient(ClientBean cb) {
		int result=this.jt.queryForInt("select count(0) from client where client_name=?",new Object[]{cb.getName()});
		if(result>0)
			return -1;
		String sql="insert into client(client_name,client_password,client_phone,client_email,operator_id,role_id) values(?,?,?,?,?,?)";
		return this.jt.update(sql,new Object[]{cb.getName(),cb.getPassword(),cb.getPhone(),cb.getEmail(),cb.getOperatorId(),cb.getRoleId()}); 

	}

	@Override
	public boolean deleteClient(int clientId) {
		int result=this.jt.update("delete from client where client_id=?",new Object[]{clientId});
		return result>0?true:false;
	}
	@Override
	public List<ClientBean> searchClient(int userId,int number,int size,String roleName) {
		if(roleName!=null&&roleName.equals("operator")){
			return this.operatorSearchClient(userId,number,size,roleName);
		}else if(roleName!=null&&roleName.equals("adminer")){
			return this.adminerSearchClient(userId,number,size,roleName);
		}else{
			return null;
		}
	}
	private List<ClientBean> operatorSearchClient(int userId,int number,int size,String roleName) {
		String sql="select client_id,client_name,client_password,client_phone,client_email,client.operator_id,operator_name from client,operator where operator.operator_id=client.operator_id and operator.operator_id=? order by client_id desc limit ?,?";
		List<ClientBean> cbList = jt.query(sql,new Object[]{userId,number,size},new ResultSetExtractor<List<ClientBean>>() {
			public List<ClientBean> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<ClientBean> list = new ArrayList<ClientBean>();
				while (rs.next()) {
					ClientBean cb=new ClientBean();
					cb.setId(rs.getInt(1));
					cb.setName(rs.getString(2));
					cb.setPassword(rs.getString(3));
					cb.setPhone(rs.getString(4));
					cb.setEmail(rs.getString(5));
					cb.setOperatorId(rs.getInt(6));
					cb.setOperatorName(rs.getString(7));
					list.add(cb);
				}
				return list;
			}
		});
		return cbList;
	}

	private List<ClientBean> adminerSearchClient(int userId,int number,int size,String roleName) {
		String sql="select client_id,client_name,client_password,client_phone,client_email,client.operator_id,operator_name from client,operator where client.operator_id=operator.operator_id order by client_id desc limit ?,?";
		List<ClientBean> cbList = jt.query(sql,new Object[]{number,size},new ResultSetExtractor<List<ClientBean>>() {
			public List<ClientBean> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<ClientBean> list = new ArrayList<ClientBean>();
				while (rs.next()) {
					ClientBean cb=new ClientBean();
					cb.setId(rs.getInt(1));
					cb.setName(rs.getString(2));
					cb.setPassword(rs.getString(3));
					cb.setPhone(rs.getString(4));
					cb.setEmail(rs.getString(5));
					cb.setOperatorId(rs.getInt(6));
					cb.setOperatorName(rs.getString(7));
					list.add(cb);
				}
				return list;
			}
		});
		return cbList;
	}

	@Override
	public ClientBean getClientById(int clientId) {
		final ClientBean cb=new ClientBean();
		String sql="select client_id,client_name,client_password,client_phone,client_email,role_id,operator_id from client where client_id=?";
		jt.query(sql,new Object[]{clientId},new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				cb.setId(rs.getInt(1));
				cb.setName(rs.getString(2));
				cb.setPassword(rs.getString(3));
				cb.setPhone(rs.getString(4));
				cb.setEmail(rs.getString(5));
				cb.setRoleId(rs.getInt(6));
				cb.setOperatorId(rs.getInt(7));
			}
		});
		return cb;
	}

	@Override
	public boolean updateClient(ClientBean cb) {
		int result=this.jt.update("update client set client_name=?,client_password=?,client_phone=?,client_email=?,role_id=?,operator_id=? where client_id=?",
				new Object[]{cb.getName(),cb.getPassword(),cb.getPhone(),cb.getEmail(),cb.getRoleId(),cb.getOperatorId(),cb.getId()});
		return result>0?true:false;
	}

	@Override
	public int getClientCount(int userId,String roleName) {
		if(roleName!=null&&roleName.equals("operator")){
			return this.jt.queryForInt("select count(0) from client where operator_id=?",new Object[]{userId});
		}else{
			return this.jt.queryForInt("select count(0) from client");

		}
	}

	@Override
	public List<ClientBean> searchClientByCondition(String type,String keyword,int userId,String roleName,int number, int size) {
		if (roleName != null && roleName.equals("operator")) {
			return this.operatorSearchClientByCondition(type, keyword, userId,number,size);
		} else {
			return this.adminerSearchClientByCondition(type, keyword,userId,number,size);
		}
	}

	private List<ClientBean> adminerSearchClientByCondition(String type,String keyword,int userId,int number,int size) {
		List<ClientBean> cbs = new ArrayList<ClientBean>();
		try {
			conn = CurrentConn.getInstance().getConn();
			if (type!=null&& type.equals("clientName")) {
				String sql = "select client_id,client_name,client_password,client_phone,client_email,client.operator_id,operator_name from operator,client where operator.operator_id=client.operator_id and client_name=? limit ?,?";
				pst = conn.prepareStatement(sql);
				pst.setString(1,keyword);
				pst.setInt(2,number);
				pst.setInt(3,size);
				rs=pst.executeQuery();
				while(rs.next()){
					ClientBean cb=new ClientBean();
					cb.setId(rs.getInt(1));
					cb.setName(rs.getString(2));
					cb.setPassword(rs.getString(3));
					cb.setPhone(rs.getString(4));
					cb.setEmail(rs.getString(5));
					cb.setOperatorId(rs.getInt(6));
					cb.setOperatorName(rs.getString(7));
					cbs.add(cb);
				}
			} else if(type!=null&&type.equals("operatorName")) {
				String sql = "select client_id,client_name,client_password,client_phone,client_email,client.operator_id,operator_name from client,operator where operator.operator_id=client.operator_id and operator_name=? limit ?,?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, keyword);
				pst.setInt(2,number);
				pst.setInt(3,size);
				rs=pst.executeQuery();
				while(rs.next()){
					ClientBean cb=new ClientBean();
					cb.setId(rs.getInt(1));
					cb.setName(rs.getString(2));
					cb.setPassword(rs.getString(3));
					cb.setPhone(rs.getString(4));
					cb.setEmail(rs.getString(5));
					cb.setOperatorId(rs.getInt(6));
					cb.setOperatorName(rs.getString(7));
					cbs.add(cb);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return cbs;
	}

	private List<ClientBean> operatorSearchClientByCondition(String type, String keyword, int userId, int number,int size) {
		List<ClientBean> cbs = new ArrayList<ClientBean>();
		try {
			conn = CurrentConn.getInstance().getConn();
			if (type != null && type.equals("clientName")) {
				String sql = "select client_id,client_name,client_password,client_phone,client_email,client.operator_id,operator_name from client,operator where operator.operator_id=client.operator_id and operator.operator_id=? and client_name=? limit ?,?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1,userId);
				pst.setString(2,keyword);
				pst.setInt(3,number);
				pst.setInt(4,size);
				rs=pst.executeQuery();
				while(rs.next()){
					ClientBean cb=new ClientBean();
					cb.setId(rs.getInt(1));
					cb.setName(rs.getString(2));
					cb.setPassword(rs.getString(3));
					cb.setPhone(rs.getString(4));
					cb.setEmail(rs.getString(5));
					cb.setOperatorId(rs.getInt(6));
					cb.setOperatorName(rs.getString(7));
					cbs.add(cb);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CurrentConn.getInstance().closeResultSet(rs);
			CurrentConn.getInstance().closePreparedStatement(pst);
			CurrentConn.getInstance().closeConnection(conn);
		}
		return cbs;
	}


	@Override
	public int getClientCountByCondition(String type, String keyword, int userId, String roleName){
		if(roleName!=null&&roleName.equals("operator")){
			if(type!=null&&type.equals("clientName")){
				return this.jt.queryForInt("select count(0) from client,operator where operator.operator_id=? and client.client_name=?",new Object[]{userId,keyword});
			}
		}else{
			if(type!=null&&type.equals("clientName")){
				return this.jt.queryForInt("select count(0) from client where client.client_name=?",new Object[]{keyword});
			}else if(type!=null&&type.equals("operatorName")){
				return this.jt.queryForInt("select count(0) from client,operator where operator.operator_id=client.operator_id and operator.operator_name=?",new Object[]{keyword});
			}
		}
		return -1;
	}

	@Override
	public String query_uuidMajor(int operatorId) {
		final OperatorBean ob=new OperatorBean();
		String sql = "select operator_uuid,operator_major from operator where operator_id=?";
		jt.query(sql,new Object[]{operatorId},new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				ob.setUuid(rs.getString(1));
				ob.setMajor(rs.getInt(2));
			}
		});
		return ob.getUuid()+"#"+ob.getMajor();
	}



}
