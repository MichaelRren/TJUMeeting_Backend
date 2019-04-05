package com.ictwsn.service.file;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.ictwsn.bean.FileBean;
import com.ictwsn.dao.file.FileDao;




@Service
public class FileServiceImpl implements FileService {

    @Resource FileDao dao;

    @Override
    public int addFile(FileBean db){
        return dao.addFile(db);
    }
    @Override
    public boolean deleteFile(int FileId){	 //删除设备
        return dao.deleteFile(FileId);
    }
    @Override
    public List<FileBean> searchFile(int number, int size){//查询设备列表
        return dao.searchFile(number,size);
    }
    @Override
    public FileBean getFileById(int fileId){
        return dao.getFileById(fileId);
    }
    @Override
    public int getFileCount(){
        return dao.getFileCount();
    }
}
