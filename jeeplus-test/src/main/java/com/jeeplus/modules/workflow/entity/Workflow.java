/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.workflow.entity;


import com.jeeplus.modules.act.entity.ActEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * workflowEntity
 * @author ww
 * @version 2019-02-15
 */
public class Workflow extends ActEntity<Workflow> {
	
	private static final long serialVersionUID = 1L;
	private String procInsId;		// 流程实例id
	private String code;		// 编码
	private String name;		// 名称
	
	public Workflow() {
		super();
	}

	public Workflow(String id){
		super(id);
	}

	@ExcelField(title="流程实例id", align=2, sort=7)
	public String getProcInsId() {
		return procInsId;
	}

	public void setProcInsId(String procInsId) {
		this.procInsId = procInsId;
	}
	
	@ExcelField(title="编码", align=2, sort=8)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="名称", align=2, sort=9)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}