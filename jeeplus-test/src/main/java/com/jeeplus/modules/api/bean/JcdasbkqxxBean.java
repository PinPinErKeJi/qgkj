package com.jeeplus.modules.api.bean;

import java.io.Serializable;
import java.util.Date;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.jcda_sbkqxx.entity.JcdaSbmcView1;

public class JcdasbkqxxBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private JcdaSbmcView1Bean sb;		// 设备名称 父类
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

	public JcdaSbmcView1Bean getSb() {
		return sb;
	}
	public void setSb(JcdaSbmcView1Bean sb) {
		this.sb = sb;
	}
	public String getSbxlh() {
		return sbxlh;
	}
	public void setSbxlh(String sbxlh) {
		this.sbxlh = sbxlh;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getZdy1() {
		return zdy1;
	}
	public void setZdy1(String zdy1) {
		this.zdy1 = zdy1;
	}
	public String getZdy2() {
		return zdy2;
	}
	public void setZdy2(String zdy2) {
		this.zdy2 = zdy2;
	}
	public String getZdy3() {
		return zdy3;
	}
	public void setZdy3(String zdy3) {
		this.zdy3 = zdy3;
	}
	public String getZdy4() {
		return zdy4;
	}
	public void setZdy4(String zdy4) {
		this.zdy4 = zdy4;
	}
	public String getZdy5() {
		return zdy5;
	}
	public void setZdy5(String zdy5) {
		this.zdy5 = zdy5;
	}
	public String getZdy6() {
		return zdy6;
	}
	public void setZdy6(String zdy6) {
		this.zdy6 = zdy6;
	}
	public String getZdy7() {
		return zdy7;
	}
	public void setZdy7(String zdy7) {
		this.zdy7 = zdy7;
	}
	public String getZdy8() {
		return zdy8;
	}
	public void setZdy8(String zdy8) {
		this.zdy8 = zdy8;
	}
	public Date getZdy9() {
		return zdy9;
	}
	public void setZdy9(Date zdy9) {
		this.zdy9 = zdy9;
	}
	public Date getZdy10() {
		return zdy10;
	}
	public void setZdy10(Date zdy10) {
		this.zdy10 = zdy10;
	}
	public Date getZdy11() {
		return zdy11;
	}
	public void setZdy11(Date zdy11) {
		this.zdy11 = zdy11;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
