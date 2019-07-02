/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_banci.entity;

import com.jeeplus.common.utils.excel.annotation.ExcelField;
import com.jeeplus.core.persistence.DataEntity;

/**
 * 班次设置Entity
 * @author ww
 * @version 2019-04-16
 */
public class Jcdabanci extends DataEntity<Jcdabanci> {
	
	private static final long serialVersionUID = 1L;
	private String banci;		// 班次名称
	private String bancij;		// 简称
	private String banduan;		// 班段
	private String sbsj;		// 上班时间
	private String xbsj;		// 下班时间
	private Double jxs;		// 计小时
	private Double jts;		// 计天数
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
	private String zdy11;		// 自定义11
	private String zdy12;		// 自定义12
	private String zdy13;		// 自定义13
	private String zdy14;		// 自定义14
	private String zdy15;		// 自定义15
	private String zdy16;		// 自定义16
	private String zdy17;		// 自定义17
	private String zdy18;		// 自定义18
	private String zdy19;		// 自定义19
	private String zdy20;		// 自定义20
	private String dkkssj;		// 打卡开始时间
	private String dkjssj;		// 打卡结束时间
	
	public Jcdabanci() {
		super();
	}

	public Jcdabanci(String id){
		super(id);
	}

	@ExcelField(title="班次名称", dictType="banci", align=2, sort=7)
	public String getBanci() {
		return banci;
	}

	public void setBanci(String banci) {
		this.banci = banci;
	}
	
	@ExcelField(title="简称", align=2, sort=8)
	public String getBancij() {
		return bancij;
	}

	public void setBancij(String bancij) {
		this.bancij = bancij;
	}
	
	@ExcelField(title="班段", dictType="banduan", align=2, sort=9)
	public String getBanduan() {
		return banduan;
	}

	public void setBanduan(String banduan) {
		this.banduan = banduan;
	}
	
	@ExcelField(title="上班时间", align=2, sort=10)
	public String getSbsj() {
		return sbsj;
	}

	public void setSbsj(String sbsj) {
		this.sbsj = sbsj;
	}
	
	@ExcelField(title="下班时间", align=2, sort=11)
	public String getXbsj() {
		return xbsj;
	}

	public void setXbsj(String xbsj) {
		this.xbsj = xbsj;
	}
	
	@ExcelField(title="计小时", align=2, sort=12)
	public Double getJxs() {
		return jxs;
	}

	public void setJxs(Double jxs) {
		this.jxs = jxs;
	}
	
	@ExcelField(title="计天数", align=2, sort=13)
	public Double getJts() {
		return jts;
	}

	public void setJts(Double jts) {
		this.jts = jts;
	}
	
	@ExcelField(title="自定义1", align=2, sort=14)
	public String getZdy1() {
		return zdy1;
	}

	public void setZdy1(String zdy1) {
		this.zdy1 = zdy1;
	}
	
	@ExcelField(title="自定义2", align=2, sort=15)
	public String getZdy2() {
		return zdy2;
	}

	public void setZdy2(String zdy2) {
		this.zdy2 = zdy2;
	}
	
	@ExcelField(title="自定义3", align=2, sort=16)
	public String getZdy3() {
		return zdy3;
	}

	public void setZdy3(String zdy3) {
		this.zdy3 = zdy3;
	}
	
	@ExcelField(title="自定义4", align=2, sort=17)
	public String getZdy4() {
		return zdy4;
	}

	public void setZdy4(String zdy4) {
		this.zdy4 = zdy4;
	}
	
	@ExcelField(title="自定义5", align=2, sort=18)
	public String getZdy5() {
		return zdy5;
	}

	public void setZdy5(String zdy5) {
		this.zdy5 = zdy5;
	}
	
	@ExcelField(title="自定义6", align=2, sort=19)
	public String getZdy6() {
		return zdy6;
	}

	public void setZdy6(String zdy6) {
		this.zdy6 = zdy6;
	}
	
	@ExcelField(title="自定义7", align=2, sort=20)
	public String getZdy7() {
		return zdy7;
	}

	public void setZdy7(String zdy7) {
		this.zdy7 = zdy7;
	}
	
	@ExcelField(title="自定义8", align=2, sort=21)
	public String getZdy8() {
		return zdy8;
	}

	public void setZdy8(String zdy8) {
		this.zdy8 = zdy8;
	}
	
	@ExcelField(title="自定义9", align=2, sort=22)
	public String getZdy9() {
		return zdy9;
	}

	public void setZdy9(String zdy9) {
		this.zdy9 = zdy9;
	}
	
	@ExcelField(title="自定义10", align=2, sort=23)
	public String getZdy10() {
		return zdy10;
	}

	public void setZdy10(String zdy10) {
		this.zdy10 = zdy10;
	}
	
	@ExcelField(title="自定义11", align=2, sort=24)
	public String getZdy11() {
		return zdy11;
	}

	public void setZdy11(String zdy11) {
		this.zdy11 = zdy11;
	}
	
	@ExcelField(title="自定义12", align=2, sort=25)
	public String getZdy12() {
		return zdy12;
	}

	public void setZdy12(String zdy12) {
		this.zdy12 = zdy12;
	}
	
	@ExcelField(title="自定义13", align=2, sort=26)
	public String getZdy13() {
		return zdy13;
	}

	public void setZdy13(String zdy13) {
		this.zdy13 = zdy13;
	}
	
	@ExcelField(title="自定义14", align=2, sort=27)
	public String getZdy14() {
		return zdy14;
	}

	public void setZdy14(String zdy14) {
		this.zdy14 = zdy14;
	}
	
	@ExcelField(title="自定义15", align=2, sort=28)
	public String getZdy15() {
		return zdy15;
	}

	public void setZdy15(String zdy15) {
		this.zdy15 = zdy15;
	}
	
	@ExcelField(title="自定义16", align=2, sort=29)
	public String getZdy16() {
		return zdy16;
	}

	public void setZdy16(String zdy16) {
		this.zdy16 = zdy16;
	}
	
	@ExcelField(title="自定义17", align=2, sort=30)
	public String getZdy17() {
		return zdy17;
	}

	public void setZdy17(String zdy17) {
		this.zdy17 = zdy17;
	}
	
	@ExcelField(title="自定义18", align=2, sort=31)
	public String getZdy18() {
		return zdy18;
	}

	public void setZdy18(String zdy18) {
		this.zdy18 = zdy18;
	}
	
	@ExcelField(title="自定义19", align=2, sort=32)
	public String getZdy19() {
		return zdy19;
	}

	public void setZdy19(String zdy19) {
		this.zdy19 = zdy19;
	}
	
	@ExcelField(title="自定义20", align=2, sort=33)
	public String getZdy20() {
		return zdy20;
	}

	public void setZdy20(String zdy20) {
		this.zdy20 = zdy20;
	}
	
	@ExcelField(title="打卡开始时间", align=2, sort=34)
	public String getDkkssj() {
		return dkkssj;
	}

	public void setDkkssj(String dkkssj) {
		this.dkkssj = dkkssj;
	}
	
	@ExcelField(title="打卡结束时间", align=2, sort=35)
	public String getDkjssj() {
		return dkjssj;
	}

	public void setDkjssj(String dkjssj) {
		this.dkjssj = dkjssj;
	}
	
}