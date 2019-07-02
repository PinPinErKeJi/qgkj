/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_sbwl.entity;

import com.jeeplus.modules.sys.entity.Office;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 设备维修Entity
 * @author ww
 * @version 2019-06-02
 */
public class Scglsbwl extends DataEntity<Scglsbwl> {
	
	private static final long serialVersionUID = 1L;
	private Office userorg;		// 申请单位
	private String sbmc;		// 设备名称
	private String sbwxnr;		// 设备维修内容
	private String wxsqr;		// 申请人
	private Date wxsqsj;		// 申请时间
	private Date beginWxsqsj;		// 开始 申请时间
	private Date endWxsqsj;		// 结束 申请时间
	
	public Scglsbwl() {
		super();
	}

	public Scglsbwl(String id){
		super(id);
	}

	@ExcelField(title="申请单位", fieldType=Office.class, value="userorg.name", align=2, sort=7)
	public Office getUserorg() {
		return userorg;
	}

	public void setUserorg(Office userorg) {
		this.userorg = userorg;
	}
	
	@ExcelField(title="设备名称", align=2, sort=8)
	public String getSbmc() {
		return sbmc;
	}

	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
	}
	
	@ExcelField(title="设备维修内容", align=2, sort=9)
	public String getSbwxnr() {
		return sbwxnr;
	}

	public void setSbwxnr(String sbwxnr) {
		this.sbwxnr = sbwxnr;
	}
	
	@ExcelField(title="申请人", align=2, sort=10)
	public String getWxsqr() {
		return wxsqr;
	}

	public void setWxsqr(String wxsqr) {
		this.wxsqr = wxsqr;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="申请时间", align=2, sort=11)
	public Date getWxsqsj() {
		return wxsqsj;
	}

	public void setWxsqsj(Date wxsqsj) {
		this.wxsqsj = wxsqsj;
	}
	
	public Date getBeginWxsqsj() {
		return beginWxsqsj;
	}

	public void setBeginWxsqsj(Date beginWxsqsj) {
		this.beginWxsqsj = beginWxsqsj;
	}
	
	public Date getEndWxsqsj() {
		return endWxsqsj;
	}

	public void setEndWxsqsj(Date endWxsqsj) {
		this.endWxsqsj = endWxsqsj;
	}
		
}