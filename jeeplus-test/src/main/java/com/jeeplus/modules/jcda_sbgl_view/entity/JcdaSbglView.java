/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbgl_view.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 设备名称Entity
 * @author ww
 * @version 2019-04-14
 */
public class JcdaSbglView extends DataEntity<JcdaSbglView> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 设备名称
	
	public JcdaSbglView() {
		super();
	}

	public JcdaSbglView(String id){
		super(id);
	}

	@ExcelField(title="设备名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}