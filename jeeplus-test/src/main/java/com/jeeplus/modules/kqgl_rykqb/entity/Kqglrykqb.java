/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.kqgl_rykqb.entity;

import com.jeeplus.modules.sys.entity.Office;
import com.jeeplus.modules.jcda_ygxx_view.entity.JcdaYgxxView;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 员工考勤表Entity
 * @author ww
 * @version 2019-04-17
 */
public class Kqglrykqb extends DataEntity<Kqglrykqb> {
	
	private static final long serialVersionUID = 1L;
	private Office jg;		// 机构
	private Office bm;		// 部门
	private String code;		// 人员编码
	private JcdaYgxxView xm;		// 姓名
	private String sbmc;		// 设备名称
	private String sbxlh;		// 设备序列号
	private String kqsj;		// 考勤时间
	private String kqnyr;		// 考勤年月日
	private String xq;		// 星期
	private String zp;		// 照片
	private String zt;		// 状态
	private String czy;		// 操作员
	private String zdy1;		// 自定义1
	private String zdy2;		// 自定义2
	private String zdy3;		// 自定义3
	private String zdy4;		// 自定义4
	private String zdy5;		// 自定义5
	private String zdy6;		// 自定义6
	private String ryzt;		// 人员状态(无用)
	private Double sc;		// 时长
	private Double ts;		// 天数
	private String bb;		// 班别
	
	public Kqglrykqb() {
		super();
	}

	public Kqglrykqb(String id){
		super(id);
	}

	@ExcelField(title="机构", fieldType=Office.class, value="jg.name", align=2, sort=7)
	public Office getJg() {
		return jg;
	}

	public void setJg(Office jg) {
		this.jg = jg;
	}
	
	@ExcelField(title="部门", fieldType=Office.class, value="bm.name", align=2, sort=8)
	public Office getBm() {
		return bm;
	}

	public void setBm(Office bm) {
		this.bm = bm;
	}
	
	@ExcelField(title="人员编码", align=2, sort=9)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@ExcelField(title="姓名", fieldType=JcdaYgxxView.class, value="xm.name", align=2, sort=10)
	public JcdaYgxxView getXm() {
		return xm;
	}

	public void setXm(JcdaYgxxView xm) {
		this.xm = xm;
	}
	
	@ExcelField(title="设备名称", align=2, sort=11)
	public String getSbmc() {
		return sbmc;
	}

	public void setSbmc(String sbmc) {
		this.sbmc = sbmc;
	}
	
	@ExcelField(title="设备序列号", align=2, sort=12)
	public String getSbxlh() {
		return sbxlh;
	}

	public void setSbxlh(String sbxlh) {
		this.sbxlh = sbxlh;
	}
	
	@ExcelField(title="考勤时间", align=2, sort=13)
	public String getKqsj() {
		return kqsj;
	}

	public void setKqsj(String kqsj) {
		this.kqsj = kqsj;
	}
	
	@ExcelField(title="考勤年月日", align=2, sort=14)
	public String getKqnyr() {
		return kqnyr;
	}

	public void setKqnyr(String kqnyr) {
		this.kqnyr = kqnyr;
	}
	
	@ExcelField(title="星期", align=2, sort=15)
	public String getXq() {
		return xq;
	}

	public void setXq(String xq) {
		this.xq = xq;
	}
	
	@ExcelField(title="照片", align=2, sort=16)
	public String getZp() {
		return zp;
	}

	public void setZp(String zp) {
		this.zp = zp;
	}
	
	@ExcelField(title="状态", dictType="kqzt", align=2, sort=17)
	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
	
	@ExcelField(title="操作员", align=2, sort=18)
	public String getCzy() {
		return czy;
	}

	public void setCzy(String czy) {
		this.czy = czy;
	}
	
	@ExcelField(title="自定义1", align=2, sort=19)
	public String getZdy1() {
		return zdy1;
	}

	public void setZdy1(String zdy1) {
		this.zdy1 = zdy1;
	}
	
	@ExcelField(title="自定义2", align=2, sort=20)
	public String getZdy2() {
		return zdy2;
	}

	public void setZdy2(String zdy2) {
		this.zdy2 = zdy2;
	}
	
	@ExcelField(title="自定义3", align=2, sort=21)
	public String getZdy3() {
		return zdy3;
	}

	public void setZdy3(String zdy3) {
		this.zdy3 = zdy3;
	}
	
	@ExcelField(title="自定义4", align=2, sort=22)
	public String getZdy4() {
		return zdy4;
	}

	public void setZdy4(String zdy4) {
		this.zdy4 = zdy4;
	}
	
	@ExcelField(title="自定义5", align=2, sort=23)
	public String getZdy5() {
		return zdy5;
	}

	public void setZdy5(String zdy5) {
		this.zdy5 = zdy5;
	}
	
	@ExcelField(title="自定义6", align=2, sort=24)
	public String getZdy6() {
		return zdy6;
	}

	public void setZdy6(String zdy6) {
		this.zdy6 = zdy6;
	}
	
	@ExcelField(title="人员状态", dictType="personstate", align=2, sort=25)
	public String getRyzt() {
		return ryzt;
	}

	public void setRyzt(String ryzt) {
		this.ryzt = ryzt;
	}
	
	@ExcelField(title="时长", align=2, sort=26)
	public Double getSc() {
		return sc;
	}

	public void setSc(Double sc) {
		this.sc = sc;
	}
	
	@ExcelField(title="天数", align=2, sort=27)
	public Double getTs() {
		return ts;
	}

	public void setTs(Double ts) {
		this.ts = ts;
	}
	
	@ExcelField(title="班别", dictType="banci", align=2, sort=28)
	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}
	
}