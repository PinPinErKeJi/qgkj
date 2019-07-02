package com.jeeplus.modules.jcda_banci.entity;

public class BanCiBean {

	private String id;
	private String type;
	private String onestart;//上午打卡开始时间
	private String oneend;// 上午打卡结束时间
	private String twostart;// 下午打卡开始
	private String twoend;// 下午结束时间
	private Double jts;
	private Double jxs;
	private String amap="";
	public String getId() {
		return id;
	}
	public BanCiBean setId(String id) {
		this.id = id;
		return this;
	}
	public String getType() {
		return type;
	}
	public BanCiBean setType(String type) {
		this.type = type;
		return this;
	}
	public String getOnestart() {
		return onestart;
	}
	public BanCiBean setOnestart(String onestart) {
		this.onestart = onestart;
		return this;
	}
	public String getOneend() {
		return oneend;
	}
	public BanCiBean setOneend(String oneend) {
		this.oneend = oneend;
		return this;
	}
	public String getTwostart() {
		return twostart;
	}
	public BanCiBean setTwostart(String twostart) {
		this.twostart = twostart;
		return this;
	}
	public String getTwoend() {
		return twoend;
	}
	public BanCiBean setTwoend(String twoend) {
		this.twoend = twoend;
		return this;
	}
	public Double getJts() {
		return jts;
	}
	public BanCiBean setJts(Double jts) {
		this.jts = jts;
		return this;
	}
	public Double getJxs() {
		return jxs;
	}
	public BanCiBean setJxs(Double jxs) {
		this.jxs = jxs;
		return this;
	}
	public String getAmap() {
		return amap;
	}
	public void setAmap(String amap) {
		this.amap = amap;
	}
	
}
