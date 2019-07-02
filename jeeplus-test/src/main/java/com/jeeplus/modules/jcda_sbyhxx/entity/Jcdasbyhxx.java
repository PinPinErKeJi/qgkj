/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbyhxx.entity;


import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 设备用户信息Entity
 * @author ww
 * @version 2019-04-08
 */
public class Jcdasbyhxx extends DataEntity<Jcdasbyhxx> {
	
	private static final long serialVersionUID = 1L;
	private JcdaSbmcView sb;		// 设备名称 父类
	private String sbxlh;		// 设备序列号
	private String code;		// 用户编码
	private String name;		// 用户名称
	private String tzm;		// 特征码
	private String zt;		// 状态
	private String zdy1;		// 自定义1 faceId1
	private String zdy2;		// 自定义2 faceId2
	private String zdy3;		// 自定义3 faceId3
	private String zdy4;		// 自定义4 photo1
	private String zdy5;		// 自定义5 photo2
	private String zdy6;		// 自定义6 photo3
	private String zdy7;		// 自定义7
	private String zdy8;		// 自定义8
	private String zdy9;		// 自定义9
	private String zdy10;		// 自定义10
	
	public Jcdasbyhxx() {
		super();
	}

	public Jcdasbyhxx(String id){
		super(id);
	}

	public Jcdasbyhxx(JcdaSbmcView sb){
		this.sb = sb;
	}

	public JcdaSbmcView getSb() {
		return sb;
	}

	public void setSb(JcdaSbmcView sb) {
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
	
	@ExcelField(title="特征码", align=2, sort=11)
	public String getTzm() {
		return tzm;
	}

	public void setTzm(String tzm) {
		this.tzm = tzm;
	}
	
	@ExcelField(title="状态", align=2, sort=12)
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
	
	@ExcelField(title="自定义1", align=2, sort=13)
	public String getZdy1() {
		return zdy1;
	}

	public void setZdy1(String zdy1) {
		this.zdy1 = zdy1;
	}
	
	@ExcelField(title="自定义2", align=2, sort=14)
	public String getZdy2() {
		return zdy2;
	}

	public void setZdy2(String zdy2) {
		this.zdy2 = zdy2;
	}
	
	@ExcelField(title="自定义3", align=2, sort=15)
	public String getZdy3() {
		return zdy3;
	}

	public void setZdy3(String zdy3) {
		this.zdy3 = zdy3;
	}
	
	@ExcelField(title="自定义4", align=2, sort=16)
	public String getZdy4() {
		return zdy4;
	}

	public void setZdy4(String zdy4) {
		this.zdy4 = zdy4;
	}
	
	@ExcelField(title="自定义5", align=2, sort=17)
	public String getZdy5() {
		return zdy5;
	}

	public void setZdy5(String zdy5) {
		this.zdy5 = zdy5;
	}
	
	@ExcelField(title="自定义6", align=2, sort=18)
	public String getZdy6() {
		return zdy6;
	}

	public void setZdy6(String zdy6) {
		this.zdy6 = zdy6;
	}
	
	@ExcelField(title="自定义7", align=2, sort=19)
	public String getZdy7() {
		return zdy7;
	}

	public void setZdy7(String zdy7) {
		this.zdy7 = zdy7;
	}
	
	@ExcelField(title="自定义8", align=2, sort=20)
	public String getZdy8() {
		return zdy8;
	}

	public void setZdy8(String zdy8) {
		this.zdy8 = zdy8;
	}
	
	@ExcelField(title="自定义9", align=2, sort=21)
	public String getZdy9() {
		return zdy9;
	}

	public void setZdy9(String zdy9) {
		this.zdy9 = zdy9;
	}
	
	@ExcelField(title="自定义10", align=2, sort=22)
	public String getZdy10() {
		return zdy10;
	}

	public void setZdy10(String zdy10) {
		this.zdy10 = zdy10;
	}
	
}