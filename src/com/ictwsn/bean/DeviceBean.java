package com.ictwsn.bean;
/**
 * 设备实体类
 * @author YangYanan
 * @desc
 * @date 2017-8-18
 */
public class DeviceBean {
	private String deviceName;//设备名称
	private int deviceId;//设备ID,自增
	private int clientId;//所属用户ID,外键
	private String uuid;//设备UUID
	private int major;//设备major
	private int minor;//设备minor
	private String videoUrl;//设备视频文件路径
	private String imageUrl;//设备图片文件路径
	private String updateTime;//设备更新时间
	private String title;//设备展示信息标题
	private String content;//设备展示信息内容
	private String deviceInfo;//设备描述
	private String clientName;
	private String operatorName;
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public int getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getMajor() {
		return major;
	}
	public void setMajor(int major) {
		this.major = major;
	}
	public int getMinor() {
		return minor;
	}
	public void setMinor(int minor) {
		this.minor = minor;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		if(updateTime!=null&&updateTime.length()>=19)
			this.updateTime=updateTime.substring(0,19);
		else
			this.updateTime = updateTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDeviceInfo() {
		return deviceInfo;
	}
	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	@Override
	public String toString() {
		return "DeviceBean [deviceName=" + deviceName + ", deviceId="
				+ deviceId + ", clientId=" + clientId + ", uuid=" + uuid
				+ ", major=" + major + ", minor=" + minor + ", videoUrl="
				+ videoUrl + ", imageUrl=" + imageUrl + ", updateTime="
				+ updateTime + ", title=" + title + ", content=" + content
				+ ", deviceInfo=" + deviceInfo + ", clientName=" + clientName
				+ ", operatorName=" + operatorName + "]";
	}
	
}
