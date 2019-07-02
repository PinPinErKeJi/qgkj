/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_gxsz.entity;

import com.jeeplus.core.persistence.DataEntity;
import com.jeeplus.modules.jcda_banci.entity.BanCiBean;

import java.util.List;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.excel.annotation.ExcelField;

/**
 * 公休设置Entity
 * @author ww
 * @version 2019-04-18
 */
public class Jcdagxsz extends DataEntity<Jcdagxsz> {
	
	private static final long serialVersionUID = 1L;
	private String weekday;		// 公休
	private String gxfs;		// 公休范围
	private String sbtkssjj;		// 上午开始时间（进）
	private String sbtjssjj;		// 上午结束时间（进）
	private String sbtkssjc;		// 上午开始时间（出）
	private String sbtjssjc;		// 上午结束时间（出）
	private String xwkssjj;		// 下午开始时间（进）
	private String xwjssjj;		// 下午结束时间（进）
	private String xwkssjc;		// 下午开始时间（出）
	private String xwjssjc;		// 下午结束时间（出）
	private Double swsc;		// 上午时长
	private Double swts;		// 上午天数
	private Double xwsc;		// 下午时长
	private Double xwts;		// 下午天数
	
	public String[] getDutyDays() {
		if(this.gxfs==null) return new String[] {};
		return this.gxfs.split(",");
	}
	
	public List<BanCiBean> getDuty(String week) {
		String[] ds = getDutyDays();
		List<BanCiBean> banCiBeans = Lists.newArrayList();
		for (int i = 0; i < ds.length; i++) {
			if(ds[i].contains(week)) {
				if(ds[i].contains("上午")) {
					BanCiBean banCiBean = new BanCiBean();
					banCiBean.setId(this.id).setOnestart(this.sbtkssjj).setOneend(this.sbtjssjj)
						.setTwoend(sbtjssjc).setTwostart(sbtkssjc).setJts(swts).setJxs(swsc).setAmap("AM");
					banCiBeans.add(banCiBean);
				}else {
					BanCiBean banCiBean = new BanCiBean();
					banCiBean.setId(this.id).setOnestart(this.xwkssjj).setOneend(this.xwjssjj)
					.setTwoend(xwjssjc).setTwostart(xwkssjc).setJts(xwts).setJxs(xwsc).setAmap("PM");
					banCiBeans.add(banCiBean);
				}
			}
		}
		return banCiBeans;
	}
	public Jcdagxsz() {
		super();
	}

	public Jcdagxsz(String id){
		super(id);
	}

	@ExcelField(title="公休", dictType="weekday", align=2, sort=7)
	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}
	
	@ExcelField(title="公休范围", dictType="gxfw", align=2, sort=8)
	public String getGxfs() {
		return gxfs;
	}

	public void setGxfs(String gxfs) {
		this.gxfs = gxfs;
	}
	
	@ExcelField(title="上午开始时间（进）", align=2, sort=9)
	public String getSbtkssjj() {
		return sbtkssjj;
	}

	public void setSbtkssjj(String sbtkssjj) {
		this.sbtkssjj = sbtkssjj;
	}
	
	@ExcelField(title="上午结束时间（进）", align=2, sort=10)
	public String getSbtjssjj() {
		return sbtjssjj;
	}

	public void setSbtjssjj(String sbtjssjj) {
		this.sbtjssjj = sbtjssjj;
	}
	
	@ExcelField(title="上午开始时间（出）", align=2, sort=11)
	public String getSbtkssjc() {
		return sbtkssjc;
	}

	public void setSbtkssjc(String sbtkssjc) {
		this.sbtkssjc = sbtkssjc;
	}
	
	@ExcelField(title="上午结束时间（出）", align=2, sort=12)
	public String getSbtjssjc() {
		return sbtjssjc;
	}

	public void setSbtjssjc(String sbtjssjc) {
		this.sbtjssjc = sbtjssjc;
	}
	
	@ExcelField(title="下午开始时间（进）", align=2, sort=13)
	public String getXwkssjj() {
		return xwkssjj;
	}

	public void setXwkssjj(String xwkssjj) {
		this.xwkssjj = xwkssjj;
	}
	
	@ExcelField(title="下午结束时间（进）", align=2, sort=14)
	public String getXwjssjj() {
		return xwjssjj;
	}

	public void setXwjssjj(String xwjssjj) {
		this.xwjssjj = xwjssjj;
	}
	
	@ExcelField(title="下午开始时间（出）", align=2, sort=15)
	public String getXwkssjc() {
		return xwkssjc;
	}

	public void setXwkssjc(String xwkssjc) {
		this.xwkssjc = xwkssjc;
	}
	
	@ExcelField(title="下午结束时间（出）", align=2, sort=16)
	public String getXwjssjc() {
		return xwjssjc;
	}

	public void setXwjssjc(String xwjssjc) {
		this.xwjssjc = xwjssjc;
	}
	
	@ExcelField(title="上午时长", align=2, sort=17)
	public Double getSwsc() {
		return swsc;
	}

	public void setSwsc(Double swsc) {
		this.swsc = swsc;
	}
	
	@ExcelField(title="上午天数", align=2, sort=18)
	public Double getSwts() {
		return swts;
	}

	public void setSwts(Double swts) {
		this.swts = swts;
	}
	
	@ExcelField(title="下午时长", align=2, sort=19)
	public Double getXwsc() {
		return xwsc;
	}

	public void setXwsc(Double xwsc) {
		this.xwsc = xwsc;
	}
	
	@ExcelField(title="下午天数", align=2, sort=20)
	public Double getXwts() {
		return xwts;
	}

	public void setXwts(Double xwts) {
		this.xwts = xwts;
	}
	
}