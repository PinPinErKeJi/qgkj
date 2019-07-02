/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbkqxx.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 设备考勤信息Entity
 * @author ww
 * @version 2019-04-08
 */
public class Jcdasbkqxx extends DataEntity<Jcdasbkqxx> {
	
	private static final long serialVersionUID = 1L;
	private JcdaSbmcView1 sb;		// 设备名称 父类
	private String sbxlh;		// 设备序列号
	private String code;		// 用户编码
	private String name;		// 用户名称
	private Date date;		// 时间
	private String zdy1;		// 自定义1 // 人员id
	private String zdy2;		// 自定义2 // 识别出的人员类型 0：时间段内，1：时间段外，2：陌生人/识别失败
	private String zdy3;		// 自定义3  来源 ：刷卡识别 | 人脸识别
	private String zdy4;		// 自定义4 图片
	private String zdy5;		// 自定义5
	private String zdy6;		// 自定义6
	private String zdy7;		// 自定义7
	private String zdy8;		// 自定义8
	private Date zdy9;		// 自定义9
	private Date zdy10;		// 自定义10
	private Date zdy11;		// 自定义11
	private Date beginDate;		// 开始 时间
	private Date endDate;		// 结束 时间
	
	public Jcdasbkqxx() {
		super();
	}

	public Jcdasbkqxx(String id){
		super(id);
	}

	public Jcdasbkqxx(JcdaSbmcView1 sb){
		this.sb = sb;
	}

	public JcdaSbmcView1 getSb() {
		return sb;
	}

	public void setSb(JcdaSbmcView1 sb) {
		this.sb = sb;
	}
	
	@ExcelField(title="设备序列号", align=2, sort=8)
	public String getSbxlh() {
		return sbxlh;
	}

	public void setSbxlh(String sbxlh) {
		this.sbxlh = sbxlh;
	}
	
	@ExcelField(title="用户编码", align=2, sort=9)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="用户名称", align=2, sort=10)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="时间", align=2, sort=11)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@ExcelField(title="自定义1", align=2, sort=12)
	public String getZdy1() {
		return zdy1;
	}

	public void setZdy1(String zdy1) {
		this.zdy1 = zdy1;
	}
	
	@ExcelField(title="自定义2", align=2, sort=13)
	public String getZdy2() {
		return zdy2;
	}

	public void setZdy2(String zdy2) {
		this.zdy2 = zdy2;
	}
	
	@ExcelField(title="自定义3", align=2, sort=14)
	public String getZdy3() {
		return zdy3;
	}

	public void setZdy3(String zdy3) {
		this.zdy3 = zdy3;
	}
	
	@ExcelField(title="自定义4", align=2, sort=15)
	public String getZdy4() {
		return zdy4;
	}

	public void setZdy4(String zdy4) {
		this.zdy4 = zdy4;
	}
	
	@ExcelField(title="自定义5", align=2, sort=16)
	public String getZdy5() {
		return zdy5;
	}

	public void setZdy5(String zdy5) {
		this.zdy5 = zdy5;
	}
	
	@ExcelField(title="自定义6", align=2, sort=17)
	public String getZdy6() {
		return zdy6;
	}

	public void setZdy6(String zdy6) {
		this.zdy6 = zdy6;
	}
	
	@ExcelField(title="自定义7", align=2, sort=18)
	public String getZdy7() {
		return zdy7;
	}

	public void setZdy7(String zdy7) {
		this.zdy7 = zdy7;
	}
	
	@ExcelField(title="自定义8", align=2, sort=19)
	public String getZdy8() {
		return zdy8;
	}

	public void setZdy8(String zdy8) {
		this.zdy8 = zdy8;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="自定义9", align=2, sort=20)
	public Date getZdy9() {
		return zdy9;
	}

	public void setZdy9(Date zdy9) {
		this.zdy9 = zdy9;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="自定义10", align=2, sort=21)
	public Date getZdy10() {
		return zdy10;
	}

	public void setZdy10(Date zdy10) {
		this.zdy10 = zdy10;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="自定义11", align=2, sort=22)
	public Date getZdy11() {
		return zdy11;
	}

	public void setZdy11(Date zdy11) {
		this.zdy11 = zdy11;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
		
}