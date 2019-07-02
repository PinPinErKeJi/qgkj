package com.jeeplus.modules.jcda_sbkqxx.entity;

import java.util.Date;

public class AttendanceRecordInfo {

	private String id;
	/**
	 * 员工id
	 */
	private String employeeId;
	/**
	 * 员工名称
	 */
	private String employeeName;
	/**
	 * 员工编号
	 */
	private String employeeCode;
	/**
	 * 打卡时间
	 */
	private Date date;
	/**
	 * 拍摄照片
	 */
	private String photo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
}
