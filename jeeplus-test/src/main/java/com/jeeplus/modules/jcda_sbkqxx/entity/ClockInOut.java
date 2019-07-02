package com.jeeplus.modules.jcda_sbkqxx.entity;

import java.util.Date;

/**
 * 打卡
 * @author Jin
 *
 */
public class ClockInOut {

	private String id;
	private String sbId;
	private String sbName;
	private String image;
	private String sbxlh;
	private Date date;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSbId() {
		return sbId;
	}
	public void setSbId(String sbId) {
		this.sbId = sbId;
	}
	public String getSbName() {
		return sbName;
	}
	public void setSbName(String sbName) {
		this.sbName = sbName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getSbxlh() {
		return sbxlh;
	}
	public void setSbxlh(String sbxlh) {
		this.sbxlh = sbxlh;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
