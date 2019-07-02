package org.jeeplus.cmd.employee;
/**
 * 注册成功返回的人员信息
 * @author Jin
 */
public class CallBackPersonInfo {
	/** 人员id */
	private String id;
	/** 卡号 */
	private String idcardNum;
	/** 人员姓名 */
	private String name;
	/** 人员创建时间 */
	private String createTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdcardNum() {
		return idcardNum;
	}
	public void setIdcardNum(String idcardNum) {
		this.idcardNum = idcardNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
