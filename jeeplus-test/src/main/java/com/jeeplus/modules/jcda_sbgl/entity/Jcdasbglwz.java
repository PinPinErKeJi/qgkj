/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbgl.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 设备管理Entity
 * @author ww
 * @version 2019-04-07
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Jcdasbglwz extends TreeEntity<Jcdasbglwz> {
	
	private static final long serialVersionUID = 1L;
	
	private List<Jcdasbmc> jcdasbmcList = Lists.newArrayList();		// 子表列表
	
	public Jcdasbglwz() {
		super();
	}

	public Jcdasbglwz(String id){
		super(id);
	}

	public  Jcdasbglwz getParent() {
			return parent;
	}
	
	@Override
	public void setParent(Jcdasbglwz parent) {
		this.parent = parent;
		
	}
	
	public List<Jcdasbmc> getJcdasbmcList() {
		return jcdasbmcList;
	}

	public void setJcdasbmcList(List<Jcdasbmc> jcdasbmcList) {
		this.jcdasbmcList = jcdasbmcList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}