/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_jjrsz.entity;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 节假日设置Entity
 * @author ww
 * @version 2019-04-16
 */
public class Jcdajjrsz extends DataEntity<Jcdajjrsz> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 节日名称
	private String kssj;		// 开始时间
	private String jssj;		// 结束时间
	
	public Jcdajjrsz() {
		super();
	}

	public Jcdajjrsz(String id){
		super(id);
	}

	@ExcelField(title="节日名称", align=2, sort=7)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="开始时间", align=2, sort=8)
	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	
	@ExcelField(title="结束时间", align=2, sort=9)
	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	
}