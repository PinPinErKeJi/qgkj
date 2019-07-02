/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbyhxx.entity;

import java.util.List;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 设备用户信息Entity
 * @author ww
 * @version 2019-04-08
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class JcdaSbmcView extends TreeEntity<JcdaSbmcView> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 设备号
	private String ip;		// IP
	
	private List<Jcdasbyhxx> jcdasbyhxxList = Lists.newArrayList();		// 子表列表
	
	public JcdaSbmcView() {
		super();
	}

	public JcdaSbmcView(String id){
		super(id);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public  JcdaSbmcView getParent() {
			return parent;
	}
	
	@Override
	public void setParent(JcdaSbmcView parent) {
		this.parent = parent;
		
	}
	
	public List<Jcdasbyhxx> getJcdasbyhxxList() {
		return jcdasbyhxxList;
	}

	public void setJcdasbyhxxList(List<Jcdasbyhxx> jcdasbyhxxList) {
		this.jcdasbyhxxList = jcdasbyhxxList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}