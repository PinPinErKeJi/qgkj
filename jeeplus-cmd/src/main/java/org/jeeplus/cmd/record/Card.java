package org.jeeplus.cmd.record;

public class Card {

	/** 识别记录id */
	private String id;
	/** 人员姓名 */
	private String name;
	/** 人员id */
	private String personId;
	/** 回调结果。0：回调失败，1：回调成功或未设置回调地址 */
	private Integer state;
	/** 识别成功时的设备时间，Unix毫秒时间戳 */
	private Long time;
	/** 识别出的人员类型，0：passTime时间段内，1：passTime时间段外 */
	private Integer type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
