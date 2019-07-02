/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbqd.entity;

import com.jeeplus.modules.sys.entity.Area;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 设备清单Entity
 * @author ww
 * @version 2019-04-25
 */
public class Jcdasbqd extends DataEntity<Jcdasbqd> {
	
	private static final long serialVersionUID = 1L;
	private String sbmc;		// 设备名称
	private String sbid;		// 设备id
	private String sbxlh;		// 设备序列号
	private String sbip;		// 设备ip地址
	private Area sbqy;		// 设备区域
	private String sbdiz;		// 设备地址
	private String sbzt;		// 设备状态
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
	
	public Jcdasbqd() {
		super();
	}

	public Jcdasbqd(String id){
		super(id);
	}

	@ExcelField(title="设备名称", align=2, sort=7)
	public String getSbmc() {
		return sbmc;
	}

	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
	}
	
	@ExcelField(title="设备id", align=2, sort=8)
	public String getSbid() {
		return sbid;
	}

	public void setSbid(String sbid) {
		this.sbid = sbid;
	}
	
	@ExcelField(title="设备序列号", align=2, sort=9)
	public String getSbxlh() {
		return sbxlh;
	}

	public void setSbxlh(String sbxlh) {
		this.sbxlh = sbxlh;
	}
	
	@ExcelField(title="设备ip地址", align=2, sort=10)
	public String getSbip() {
		return sbip;
	}

	public void setSbip(String sbip) {
		this.sbip = sbip;
	}
	
	@ExcelField(title="设备区域", align=2, sort=11)
	public Area getSbqy() {
		return sbqy;
	}

	public void setSbqy(Area sbqy) {
		this.sbqy = sbqy;
	}
	
	@ExcelField(title="设备地址", align=2, sort=12)
	public String getSbdiz() {
		return sbdiz;
	}

	public void setSbdiz(String sbdiz) {
		this.sbdiz = sbdiz;
	}
	
	@ExcelField(title="设备状态", align=2, sort=13)
	public String getSbzt() {
		return sbzt;
	}

	public void setSbzt(String sbzt) {
		this.sbzt = sbzt;
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
	
}