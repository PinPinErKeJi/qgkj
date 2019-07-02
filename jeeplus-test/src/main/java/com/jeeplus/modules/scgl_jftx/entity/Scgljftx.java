/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_jftx.entity;

import com.jeeplus.modules.sys.entity.Office;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 缴费提醒Entity
 * @author ww
 * @version 2019-06-02
 */
public class Scgljftx extends DataEntity<Scgljftx> {
	
	private static final long serialVersionUID = 1L;
	private Office userorg;		// 使用单位
	private String sbmc;		// 设备名称
	private Date dqdate;		// 预计到期时间
	private Date beginDqdate;		// 开始 预计到期时间
	private Date endDqdate;		// 结束 预计到期时间
	
	public Scgljftx() {
		super();
	}

	public Scgljftx(String id){
		super(id);
	}

	@ExcelField(title="使用单位", align=2, sort=7)
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
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="预计到期时间", align=2, sort=9)
	public Date getDqdate() {
		return dqdate;
	}

	public void setDqdate(Date dqdate) {
		this.dqdate = dqdate;
	}
	
	public Date getBeginDqdate() {
		return beginDqdate;
	}

	public void setBeginDqdate(Date beginDqdate) {
		this.beginDqdate = beginDqdate;
	}
	
	public Date getEndDqdate() {
		return endDqdate;
	}

	public void setEndDqdate(Date endDqdate) {
		this.endDqdate = endDqdate;
	}
		
}