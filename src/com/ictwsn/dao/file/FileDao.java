package com.ictwsn.dao.file;

import java.util.List;

import com.ictwsn.bean.FileBean;

public interface FileDao {
    public int addFile(FileBean db);		 //添加设备信息
    public boolean deleteFile(int FileId);	 //删除设备
    public List<FileBean> searchFile(int number,int size);		 	 //查询设备列表
    public FileBean getFileById(int fileId);
    public int getFileCount();
}
