/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ryxx.entity;

import java.util.List;
import com.google.common.collect.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jeeplus.core.persistence.TreeEntity;

/**
 * 人员信息Entity
 * @author ww
 * @version 2019-04-07
 */
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})
public class Syjg extends TreeEntity<Syjg> {
	
	private static final long serialVersionUID = 1L;
	private String area;		// 归属区域
	private String code;		// 区域编码
	private String type;		// 机构类型
	private String grade;		// 机构等级
	private String address;		// 联系地址
	private String zipCode;		// 邮政编码
	private String master;		// 负责人
	private String phone;		// 电话
	private String fax;		// 传真
	private String email;		// 邮箱
	private String useable;		// 是否启用
	private String primaryPerson;		// 主负责人
	private String deputyPerson;		// 副负责人
	
	private List<Jcdaygxx> jcdaygxxList = Lists.newArrayList();		// 子表列表
	
	public Syjg() {
		super();
	}

	public Syjg(String id){
		super(id);
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
	}
	
	public String getPrimaryPerson() {
		return primaryPerson;
	}

	public void setPrimaryPerson(String primaryPerson) {
		this.primaryPerson = primaryPerson;
	}
	
	public String getDeputyPerson() {
		return deputyPerson;
	}

	public void setDeputyPerson(String deputyPerson) {
		this.deputyPerson = deputyPerson;
	}
	
	public  Syjg getParent() {
			return parent;
	}
	
	@Override
	public void setParent(Syjg parent) {
		this.parent = parent;
		
	}
	
	public List<Jcdaygxx> getJcdaygxxList() {
		return jcdaygxxList;
	}

	public void setJcdaygxxList(List<Jcdaygxx> jcdaygxxList) {
		this.jcdaygxxList = jcdaygxxList;
	}
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}