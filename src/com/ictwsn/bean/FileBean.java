package com.ictwsn.bean;
/*
文件实体类，实现文件上传下载
 */
public class FileBean {
    private int fileId; //文件ID,自增。
    private String fileDescription;  //文件描述
    private String fileUrl;  //文件存储路径
    private String updateTime;  //文件更新时间
    public int getFileId(){
        return  fileId;
    }
    public void setFileId(int fileId){
        this.fileId = fileId;
    }
    public String getFileDescription(){
        return fileDescription;
    }
    public void setFileDescription(String fileDescription){
        this.fileDescription = fileDescription;
    }
    public String getFileUrl(){
        return fileUrl;
    }
    public void setFileUrl(String fileUrl){
        this.fileUrl = fileUrl;
    }
    public String getUpdateTime(){
        return updateTime;
    }
    public void setUpdateTime(String updateTime){
        this.updateTime = updateTime;
    }


}
