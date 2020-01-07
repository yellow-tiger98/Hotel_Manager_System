package com.hd.model;

/**
 * 酒店入住者模型
 * @author 94366
 *
 */
public class Loader {
	//入住信息id
	private String lodgingInfoId;
	//证件类型
	private String certificate;
	//证件号码
	private String certificateNo;
	//入住者姓名
	private String lodgerName;
	//入住者性别
	private String sex;
	//主登记人标记
	private int isRegister;
	//入住者联系方式
	private String phone;
	//备注
	private String note;
	//入住时间
	private String inTime;
	//退房时间
	private String outTime;
	//入住房间号
	private String roomNo;
	//收取押金
	private String desposit;
	//陪同者添加序号
	private int cpId;
	//入住天数
	private String days;
	
	public String getLodgingInfoId() {
		return lodgingInfoId;
	}
	public void setLodgingInfoId(String lodgingInfoId) {
		this.lodgingInfoId = lodgingInfoId;
	}
	public String getCertificate() {
		return certificate;
	}
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	public String getCertificateNo() {
		return certificateNo;
	}
	public void setCertificateNo(String certificateNo) {
		this.certificateNo = certificateNo;
	}
	public String getLodgerName() {
		return lodgerName;
	}
	public void setLodgerName(String lodgerName) {
		this.lodgerName = lodgerName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getIsRegister() {
		return isRegister;
	}
	public void setIsRegister(int isRegister) {
		this.isRegister = isRegister;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getDesposit() {
		return desposit;
	}
	public void setDesposit(String desposit) {
		this.desposit = desposit;
	}
	public int getCpId() {
		return cpId;
	}
	public void setCpId(int cpId) {
		this.cpId = cpId;
	}
	
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	@Override
	public String toString() {
		return "Loader [lodgingInfoId=" + lodgingInfoId + ", certificate=" + certificate + ", certificateNo="
				+ certificateNo + ", lodgerName=" + lodgerName + ", sex=" + sex + ", isRegister=" + isRegister
				+ ", phone=" + phone + ", note=" + note + ", inTime=" + inTime + ", outTime=" + outTime + ", roomNo="
				+ roomNo + ", desposit=" + desposit + ", cpId=" + cpId + ", days=" + days + "]";
	}
	

	
}
