package com.ictwsn.dao.file;

import com.ictwsn.bean.FileBean;
import com.ictwsn.dao.MySQLBaseDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import com.ictwsn.dao.MySQLBaseDao;
import com.ictwsn.util.CurrentConn;
import com.ictwsn.util.RandomString;
import com.ictwsn.util.format.PublicDay;
import org.springframework.stereotype.Service;

@Repository
public class FileDaoImpl extends MySQLBaseDao implements FileDao {

    private Connection conn = null;
    private PreparedStatement pst = null;
    private ResultSet rs=null;

    @Override
    public int addFile(FileBean fb){
        String sql="insert into file(file_description,file_url,update_time)" +
                "values(?,?,?)";
        return this.jt.update(sql,new Object[]{fb.getFileDescription(),fb.getFileUrl(),fb.getUpdateTime()});
    }
    public boolean deleteFile(int fileId){
        int result=this.jt.update("delete from file where file_id=?",new Object[]{fileId});
        return result>0?true:false;
    }

    @Override
    public List<FileBean> searchFile(int number,int size){
        List<FileBean> dbs=new ArrayList<FileBean>();
        String sql="select file_id,file_description,file_url,update_time from file order by file_id desc limit ?,?";
        try{
            conn=CurrentConn.getInstance().getConn();
            pst=conn.prepareStatement(sql);
            pst.setInt(1,number);
            pst.setInt(2,size);
            rs=pst.executeQuery();
            while(rs.next()){
                FileBean fb=new FileBean();
                fb.setFileId(rs.getInt(1));
                fb.setFileDescription(rs.getString(2));
                fb.setFileUrl(rs.getString(3));
                fb.setUpdateTime(rs.getString(4));
                dbs.add(fb);
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

    public FileBean getFileById(int fileId){
        FileBean fb=new FileBean();
        String sql="select file_id,file_description,file_url,update_time from file where file_id=?";
        try{
            conn=CurrentConn.getInstance().getConn();
            pst=conn.prepareStatement(sql);
            pst.setInt(1,fileId);
            rs=pst.executeQuery();
            if(rs.next()){
                fb.setFileDescription(rs.getString(2));
                fb.setFileUrl(rs.getString(3));
                fb.setUpdateTime(rs.getString(4));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            CurrentConn.getInstance().closeResultSet(rs);
            CurrentConn.getInstance().closePreparedStatement(pst);
            CurrentConn.getInstance().closeConnection(conn);
        }
        return fb;
    }
    public int getFileCount(){
        return this.jt.queryForInt("select count(0) from file");
    }
}
