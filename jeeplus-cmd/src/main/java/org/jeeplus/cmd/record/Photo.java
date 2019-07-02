package org.jeeplus.cmd.record;

public class Photo {

	/** 识别记录id */
	private String id;
	/** 人员姓名 */
	private String name;
	/** 人员id 陌生人/识别失败显示id为STRANGERBABY*/
	private String personId;
	/** 现场抓拍照片存储在设备内的路径。当设备内现场照存储满3G时，会自动删除较早的1G现场照；若有需要请及时存储 */
	private String path;
	/** 回调结果。0：回调失败，1：回调成功或未设置回调地址 */
	private Integer state;
	/** 识别成功时的设备时间，Unix毫秒时间戳 */
	private Long time;
	/** 识别出的人员类型，0：时间段内，1：时间段外，2：陌生人/识别失败 */
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
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
