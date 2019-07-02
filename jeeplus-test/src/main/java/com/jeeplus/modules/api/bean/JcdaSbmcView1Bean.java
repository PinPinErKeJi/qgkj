/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.api.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 设备考勤信息Entity
 * @author ww
 * @version 2019-04-08
 */
//@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class JcdaSbmcView1Bean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;		// 设备ID
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}