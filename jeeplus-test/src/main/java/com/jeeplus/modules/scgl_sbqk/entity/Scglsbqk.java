/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_sbqk.entity;

import com.jeeplus.modules.sys.entity.Office;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 设备使用情况Entity
 * @author ww
 * @version 2019-06-02
 */
public class Scglsbqk extends DataEntity<Scglsbqk> {
	
	private static final long serialVersionUID = 1L;
	private Office org;		// 单位
	private String sbname;		// 设备名称
	private Date sbkssj;		// 设备开始时间
	private Date sbjssj;		// 设备结束时间
	private String sbzt;		// 设备状态
	
	public Scglsbqk() {
		super();
	}

	public Scglsbqk(String id){
		super(id);
	}

	@ExcelField(title="单位", fieldType=Office.class, value="org.name", align=2, sort=7)
	public Office getOrg() {
		return org;
	}

	public void setOrg(Office org) {
		this.org = org;
	}
	
	@ExcelField(title="设备名称", align=2, sort=8)
	public String getSbname() {
		return sbname;
	}

	public void setSbname(String sbname) {
		this.sbname = sbname;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="设备开始时间", align=2, sort=9)
	public Date getSbkssj() {
		return sbkssj;
	}

	public void setSbkssj(Date sbkssj) {
		this.sbkssj = sbkssj;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="设备结束时间", align=2, sort=10)
	public Date getSbjssj() {
		return sbjssj;
	}

	public void setSbjssj(Date sbjssj) {
		this.sbjssj = sbjssj;
	}
	
	@ExcelField(title="设备状态", align=2, sort=11)
	public String getSbzt() {
		return sbzt;
	}

	public void setSbzt(String sbzt) {
		this.sbzt = sbzt;
	}
	
}