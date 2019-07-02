/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ryxx.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeeplus.modules.jcda_national.entity.Jcdanational;
import com.jeeplus.modules.jcda_sbgl_view.entity.JcdaSbglView;
import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工信息Entity
 * @author ww
 * @version 2019-04-07
 */
public class Jcdaygxx extends DataEntity<Jcdaygxx> {
	
	private static final long serialVersionUID = 1L;
	private Syjg jg;		// 组织架构 父类
	private String code;		// 员工编号
	private String name;		// 员工姓名
	private String regcode;		// 登记编号
	private String cardno;		// 卡片编号
	private Date enterdate;		// 入职日期
	private String sex;		// 性别
	private Jcdanational national;		// 民族
	private String certificate;		// 证件编号
	private String telephone;		// 电话号码
	private String personstate;		// 员工状态
	private Date birthdate;		// 出生日期
	private String position;		// 职务
	private String worktpye;		// 工种
	private String photo;		// 照片
	private String remark;		// 备注
	private String address;		// 地址
	private String shift;		// 班次
	private String weekday;		// 周末
	private String attpassword;		// 考勤密码
	private String attpasswordconf;		// 考勤密码确认
	private String regtype;		// 注册类型
	private String regpersonal;		// 个人注册信息
	private JcdaSbglView sb;		// 设备名称
	private String zdy2;		// 自定义2
	private String zdy3;		// 自定义3
	private String zdy4;		// 自定义4
	private String zdy5;		// 自定义5
	private String zdy6;		// 自定义6
	private String zdy7;		// 自定义7
	private String zdy8;		// 自定义8
	private String zdy9;		// 自定义9
	private String zdy10;		// 自定义10
	private Date beginEnterdate;		// 开始 入职日期
	private Date endEnterdate;		// 结束 入职日期
	
	public Jcdaygxx() {
		super();
	}

	public Jcdaygxx(String id){
		super(id);
	}

	public Jcdaygxx(Syjg jg){
		this.jg = jg;
	}

	public Jcdaygxx(JcdaSbglView sb){
		this.sb = sb;
	}
	
	public Syjg getJg() {
		return jg;
	}

	public void setJg(Syjg jg) {
		this.jg = jg;
	}
	
	@ExcelField(title="员工编号", align=2, sort=8)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="员工姓名", align=2, sort=9)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="登记编号", align=2, sort=10)
	public String getRegcode() {
		return regcode;
	}

	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}
	
	@ExcelField(title="卡片编号", align=2, sort=11)
	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="入职日期", align=2, sort=12)
	public Date getEnterdate() {
		return enterdate;
	}

	public void setEnterdate(Date enterdate) {
		this.enterdate = enterdate;
	}
	
	@ExcelField(title="性别", dictType="sex", align=2, sort=13)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@ExcelField(title="民族", fieldType=Jcdanational.class, value="national.name", align=2, sort=14)
	public Jcdanational getNational() {
		return national;
	}

	public void setNational(Jcdanational national) {
		this.national = national;
	}
	
	@ExcelField(title="证件编号", align=2, sort=15)
	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}
	
	@ExcelField(title="电话号码", align=2, sort=16)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
	@ExcelField(title="员工状态", dictType="personstate", align=2, sort=17)
	public String getPersonstate() {
		return personstate;
	}

	public void setPersonstate(String personstate) {
		this.personstate = personstate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="出生日期", align=2, sort=18)
	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
	
	@ExcelField(title="职务", dictType="position", align=2, sort=19)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@ExcelField(title="工种", dictType="worktpye", align=2, sort=20)
	public String getWorktpye() {
		return worktpye;
	}

	public void setWorktpye(String worktpye) {
		this.worktpye = worktpye;
	}
	
	@ExcelField(title="照片", align=2, sort=21)
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@ExcelField(title="备注", align=2, sort=22)
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@ExcelField(title="地址", align=2, sort=23)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@ExcelField(title="班次", align=2, sort=24)
	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}
	
	@ExcelField(title="周末", dictType="weekday", align=2, sort=25)
	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	
	@ExcelField(title="考勤密码", align=2, sort=26)
	public String getAttpassword() {
		return attpassword;
	}

	public void setAttpassword(String attpassword) {
		this.attpassword = attpassword;
	}
	
	@ExcelField(title="考勤密码确认", align=2, sort=27)
	public String getAttpasswordconf() {
		return attpasswordconf;
	}

	public void setAttpasswordconf(String attpasswordconf) {
		this.attpasswordconf = attpasswordconf;
	}
	
	@ExcelField(title="注册类型", dictType="regtype", align=2, sort=28)
	public String getRegtype() {
		return regtype;
	}

	public void setRegtype(String regtype) {
		this.regtype = regtype;
	}
	
	@ExcelField(title="个人注册信息", dictType="regpersonal", align=2, sort=29)
	public String getRegpersonal() {
		return regpersonal;
	}

	public void setRegpersonal(String regpersonal) {
		this.regpersonal = regpersonal;
	}
	
	@ExcelField(title="设备名称", fieldType=JcdaSbglView.class, value="sb.name", align=2, sort=30)
	public JcdaSbglView getSb() {
		return sb;
	}

	public void setSb(JcdaSbglView sb) {
		this.sb = sb;
	}
	
	@ExcelField(title="自定义2", align=2, sort=31)
	public String getZdy2() {
		return zdy2;
	}

	public void setZdy2(String zdy2) {
		this.zdy2 = zdy2;
	}
	
	@ExcelField(title="自定义3", align=2, sort=32)
	public String getZdy3() {
		return zdy3;
	}

	public void setZdy3(String zdy3) {
		this.zdy3 = zdy3;
	}
	
	@ExcelField(title="自定义4", align=2, sort=33)
	public String getZdy4() {
		return zdy4;
	}

	public void setZdy4(String zdy4) {
		this.zdy4 = zdy4;
	}
	
	@ExcelField(title="自定义5", align=2, sort=34)
	public String getZdy5() {
		return zdy5;
	}

	public void setZdy5(String zdy5) {
		this.zdy5 = zdy5;
	}
	
	@ExcelField(title="自定义6", align=2, sort=35)
	public String getZdy6() {
		return zdy6;
	}

	public void setZdy6(String zdy6) {
		this.zdy6 = zdy6;
	}
	
	@ExcelField(title="自定义7", align=2, sort=36)
	public String getZdy7() {
		return zdy7;
	}

	public void setZdy7(String zdy7) {
		this.zdy7 = zdy7;
	}
	
	@ExcelField(title="自定义8", align=2, sort=37)
	public String getZdy8() {
		return zdy8;
	}

	public void setZdy8(String zdy8) {
		this.zdy8 = zdy8;
	}
	
	@ExcelField(title="自定义9", align=2, sort=38)
	public String getZdy9() {
		return zdy9;
	}

	public void setZdy9(String zdy9) {
		this.zdy9 = zdy9;
	}
	
	@ExcelField(title="自定义10", align=2, sort=39)
	public String getZdy10() {
		return zdy10;
	}

	public void setZdy10(String zdy10) {
		this.zdy10 = zdy10;
	}
	
	public Date getBeginEnterdate() {
		return beginEnterdate;
	}

	public void setBeginEnterdate(Date beginEnterdate) {
		this.beginEnterdate = beginEnterdate;
	}
	
	public Date getEndEnterdate() {
		return endEnterdate;
	}

	public void setEndEnterdate(Date endEnterdate) {
		this.endEnterdate = endEnterdate;
	}
		
}