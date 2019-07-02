package org.jeeplus.cmd.employee;
/**
 * 注册人员信息
 * @author Jin
 *
 */
public class RegisterPersonInfo {
	/** 人员id <br>id为人员标识，限制64位。注册人员时，若id不填，则系统会自动生成32位id并返回；
	 * <br>若填了id，系统会以此id为主键存入本地数据库中；若id重复，会报错*/
	private String id;
	/** 卡号	<br>注册时可以不填，长度无限制。若注册人员时填写了卡号，可直接刷对应卡号的卡进行识别，屏幕也会显示与该卡号对应的人员的名字*/
	private String idcardNum;
	/** 人员姓名  <font color="red">不可为空</font>*/
	private String name;
	
	public RegisterPersonInfo() {}
	
	public RegisterPersonInfo(String id, String idcardNum, String name) {
		super();
		this.id = id;
		this.idcardNum = idcardNum;
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public RegisterPersonInfo setId(String id) {
		this.id = id;
		return this;
	}
	public String getIdcardNum() {
		return idcardNum;
	}
	public RegisterPersonInfo setIdcardNum(String idcardNum) {
		this.idcardNum = idcardNum;
		return this;
	}
	public String getName() {
		return name;
	}
	public RegisterPersonInfo setName(String name) {
		this.name = name;
		return this;
	}
}
