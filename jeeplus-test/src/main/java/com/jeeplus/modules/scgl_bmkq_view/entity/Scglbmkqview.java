/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_bmkq_view.entity;

import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 部门考勤Entity
 * @author ww
 * @version 2019-06-02
 */
public class Scglbmkqview extends DataEntity<Scglbmkqview> {
	
	private static final long serialVersionUID = 1L;
	private Office userorg;		// 使用机构
	private Office dept;		// 部门
	private Integer rs;		// 应到人数
	private Integer sjrs;		// 实到人数
	private Integer qjrs;		// 请假人数
	private String iyear;		// 年
	private String imonth;		// 月
	
	public Scglbmkqview() {
		super();
	}

	public Scglbmkqview(String id){
		super(id);
	}

	@ExcelField(title="使用机构", fieldType=Office.class, value="userorg.name", align=2, sort=7)
	public Office getUserorg() {
		return userorg;
	}

	public void setUserorg(Office userorg) {
		this.userorg = userorg;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="dept.name", align=2, sort=8)
	public Office getDept() {
		return dept;
	}

	public void setDept(Office dept) {
		this.dept = dept;
	}
	
	@ExcelField(title="应到人数", align=2, sort=9)
	public Integer getRs() {
		return rs;
	}

	public void setRs(Integer rs) {
		this.rs = rs;
	}
	
	@ExcelField(title="实到人数", align=2, sort=10)
	public Integer getSjrs() {
		return sjrs;
	}

	public void setSjrs(Integer sjrs) {
		this.sjrs = sjrs;
	}
	
	@ExcelField(title="请假人数", align=2, sort=11)
	public Integer getQjrs() {
		return qjrs;
	}

	public void setQjrs(Integer qjrs) {
		this.qjrs = qjrs;
	}
	
	@ExcelField(title="年", align=2, sort=12)
	public String getIyear() {
		return iyear;
	}

	public void setIyear(String iyear) {
		this.iyear = iyear;
	}
	
	@ExcelField(title="月", align=2, sort=13)
	public String getImonth() {
		return imonth;
	}

	public void setImonth(String imonth) {
		this.imonth = imonth;
	}
	
}