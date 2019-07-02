/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_hmd.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 黑名单Entity
 * @author ww
 * @version 2019-06-02
 */
public class Scglhmd extends DataEntity<Scglhmd> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 姓名
	private String tzm;		// 特征码
	
	public Scglhmd() {
		super();
	}

	public Scglhmd(String id){
		super(id);
	}

	@ExcelField(title="姓名", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="特征码", align=2, sort=8)
	public String getTzm() {
		return tzm;
	}

	public void setTzm(String tzm) {
		this.tzm = tzm;
	}
	
}