package com.jeeplus.modules.utils;

import com.jeeplus.common.utils.SpringContextHolder;
import com.jeeplus.modules.jcda_sbgl.entity.CmdBaseInfo;
import com.jeeplus.modules.jcda_sbgl.mapper.JcdasbmcMapper;

public final class BusinessUtils {

	private static JcdasbmcMapper deviceMapper = SpringContextHolder.getBean(JcdasbmcMapper.class);
	
	/**
	 * 根据设备id获取发送命令的基础信息
	 * @param id 设备id
	 * @return {@link CmdBaseInfo}（IP，pass,设备序列号）
	 */
	public final static CmdBaseInfo getCmdBaseInfo(String id) {
		return deviceMapper.getCmdBaseInfo(id);
	}
}
