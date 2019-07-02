/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_rkyq_view.entity;

import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工考勤Entity
 * @author ww
 * @version 2019-06-02
 */
public class Scglrkyqview extends DataEntity<Scglrkyqview> {
	
	private static final long serialVersionUID = 1L;
	private Office org;		// 机构
	private Office userorg;		// 使用机构
	private String code;		// 工号
	private User name;		// 姓名
	private Date indate;		// 进时间
	private Date outdate;		// 出时间
	
	public Scglrkyqview() {
		super();
	}

	public Scglrkyqview(String id){
		super(id);
	}

	@ExcelField(title="机构", fieldType=Office.class, value="org.name", align=2, sort=7)
	public Office getOrg() {
		return org;
	}

	public void setOrg(Office org) {
		this.org = org;
	}
	
	@ExcelField(title="使用机构", fieldType=Office.class, value="userorg.name", align=2, sort=8)
	public Office getUserorg() {
		return userorg;
	}

	public void setUserorg(Office userorg) {
		this.userorg = userorg;
	}
	
	@ExcelField(title="工号", fieldType=String.class, value="", align=2, sort=9)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="姓名", align=2, sort=10)
	public User getName() {
		return name;
	}

	public void setName(User name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="进时间", align=2, sort=11)
	public Date getIndate() {
		return indate;
	}

	public void setIndate(Date indate) {
		this.indate = indate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="出时间", align=2, sort=12)
	public Date getOutdate() {
		return outdate;
	}

	public void setOutdate(Date outdate) {
		this.outdate = outdate;
	}
	
}