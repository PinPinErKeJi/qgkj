/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.scsb_ryxx.entity;

import com.jeeplus.modules.sys.entity.Area;
import com.jeeplus.modules.sys.entity.Office;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 人员信息Entity
 * @author ww
 * @version 2019-06-02
 */
public class Scsbmain extends DataEntity<Scsbmain> {
	
	private static final long serialVersionUID = 1L;
	private Area area;		// 区域
	private Office org;		// 机构
	private Office userorg;		// 使用机构
	private String code;		// 工号
	private String name;		// 姓名
	private String idcard;		// 身份证号码
	private String sex;		// 性别
	private String usertype;		// 用户类型
	private String contact;		// 联系方式
	private Office department;		// 部门
	private String headphoto;		// 头像
	private String zdy1;		// 自定义1
	private String zdy2;		// 自定义2
	private String zdy3;		// 自定义3
	private String zdy4;		// 自定义4
	private String zdy5;		// 自定义5
	private String zdy6;		// 自定义6
	private String zdy7;		// 自定义7
	private String zdy8;		// 自定义8
	private String zdy9;		// 自定义9
	private String zdy10;		// 自定义10
	private String personstate;		// 员工状态
	
	public Scsbmain() {
		super();
	}

	public Scsbmain(String id){
		super(id);
	}

	@ExcelField(title="区域", align=2, sort=7)
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	@ExcelField(title="机构", fieldType=Office.class, value="org.name", align=2, sort=8)
	public Office getOrg() {
		return org;
	}

	public void setOrg(Office org) {
		this.org = org;
	}
	
	@ExcelField(title="使用机构", fieldType=Office.class, value="userorg.name", align=2, sort=9)
	public Office getUserorg() {
		return userorg;
	}

	public void setUserorg(Office userorg) {
		this.userorg = userorg;
	}
	
	@ExcelField(title="工号", align=2, sort=10)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="姓名", align=2, sort=11)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="身份证号码", align=2, sort=12)
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	
	@ExcelField(title="性别", align=2, sort=13)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@ExcelField(title="用户类型", dictType="usertype", align=2, sort=14)
	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	
	@ExcelField(title="联系方式", align=2, sort=15)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	@ExcelField(title="部门", align=2, sort=16)
	public Office getDepartment() {
		return department;
	}

	public void setDepartment(Office department) {
		this.department = department;
	}
	
	@ExcelField(title="头像", align=2, sort=17)
	public String getHeadphoto() {
		return headphoto;
	}

	public void setHeadphoto(String headphoto) {
		this.headphoto = headphoto;
	}
	
	@ExcelField(title="自定义1", align=2, sort=18)
	public String getZdy1() {
		return zdy1;
	}

	public void setZdy1(String zdy1) {
		this.zdy1 = zdy1;
	}
	
	@ExcelField(title="自定义2", align=2, sort=19)
	public String getZdy2() {
		return zdy2;
	}

	public void setZdy2(String zdy2) {
		this.zdy2 = zdy2;
	}
	
	@ExcelField(title="自定义3", align=2, sort=20)
	public String getZdy3() {
		return zdy3;
	}

	public void setZdy3(String zdy3) {
		this.zdy3 = zdy3;
	}
	
	@ExcelField(title="自定义4", align=2, sort=21)
	public String getZdy4() {
		return zdy4;
	}

	public void setZdy4(String zdy4) {
		this.zdy4 = zdy4;
	}
	
	@ExcelField(title="自定义5", align=2, sort=22)
	public String getZdy5() {
		return zdy5;
	}

	public void setZdy5(String zdy5) {
		this.zdy5 = zdy5;
	}
	
	@ExcelField(title="自定义6", align=2, sort=23)
	public String getZdy6() {
		return zdy6;
	}

	public void setZdy6(String zdy6) {
		this.zdy6 = zdy6;
	}
	
	@ExcelField(title="自定义7", align=2, sort=24)
	public String getZdy7() {
		return zdy7;
	}

	public void setZdy7(String zdy7) {
		this.zdy7 = zdy7;
	}
	
	@ExcelField(title="自定义8", align=2, sort=25)
	public String getZdy8() {
		return zdy8;
	}

	public void setZdy8(String zdy8) {
		this.zdy8 = zdy8;
	}
	
	@ExcelField(title="自定义9", align=2, sort=26)
	public String getZdy9() {
		return zdy9;
	}

	public void setZdy9(String zdy9) {
		this.zdy9 = zdy9;
	}
	
	@ExcelField(title="自定义10", align=2, sort=27)
	public String getZdy10() {
		return zdy10;
	}

	public void setZdy10(String zdy10) {
		this.zdy10 = zdy10;
	}
	
	@ExcelField(title="员工状态", dictType="personstate", align=2, sort=28)
	public String getPersonstate() {
		return personstate;
	}

	public void setPersonstate(String personstate) {
		this.personstate = personstate;
	}
	
}