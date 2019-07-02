package com.jeeplus.modules.jcda_ygda.entity;

/**
 * 员工信息
 * @author Jin
 */
public class EmployeeInfo {
	/**
	 * 所在公司id
	 */
	private String companyId;
	/**
	 * 所在公司名称
	 */
	private String companyName;
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
	 * 员工性别 1:男 2：女
	 */
	private String employeeSex;
	
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getEmployeeSex() {
		return employeeSex;
	}
	public void setEmployeeSex(String employeeSex) {
		this.employeeSex = employeeSex;
	}
}