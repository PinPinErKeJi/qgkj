/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbkqxx.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 设备考勤信息Entity
 * @author ww
 * @version 2019-04-08
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class JcdaSbmcView1 extends TreeEntity<JcdaSbmcView1> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 设备号
	private String ip;		// IP
	
	private List<Jcdasbkqxx> jcdasbkqxxList = Lists.newArrayList();		// 子表列表
	
	public JcdaSbmcView1() {
		super();
	}

	public JcdaSbmcView1(String id){
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
	
	public  JcdaSbmcView1 getParent() {
			return parent;
	}
	
	@Override
	public void setParent(JcdaSbmcView1 parent) {
		this.parent = parent;
		
	}
	
	public List<Jcdasbkqxx> getJcdasbkqxxList() {
		return jcdasbkqxxList;
	}

	public void setJcdasbkqxxList(List<Jcdasbkqxx> jcdasbkqxxList) {
		this.jcdasbkqxxList = jcdasbkqxxList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}