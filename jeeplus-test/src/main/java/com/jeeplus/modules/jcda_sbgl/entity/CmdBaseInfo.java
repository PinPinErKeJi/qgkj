package com.jeeplus.modules.jcda_sbgl.entity;

import com.jeeplus.common.utils.StringUtils;

/**
 * 命令基础
 * @author Jin
 */
public class CmdBaseInfo {
	/** 设备IP */
	private String ip;
	/** 设备密码 */
	private String pass;
	/** 设备序列号 */
	private String deviceKey;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getDeviceKey() {
		return deviceKey;
	}
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}
	/**
	 * 设备是否可用（ip与pass不可为空）
	 * @return 
	 */
	public boolean isabled() {
		return StringUtils.isNotBlank(ip)&&StringUtils.isNotBlank(pass);
	}
}
