/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbkqxx.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.jcda_sbkqxx.entity.JcdaSbmcView1;
import com.jeeplus.modules.jcda_sbkqxx.mapper.JcdaSbmcView1Mapper;

/**
 * 设备考勤信息Service
 * @author ww
 * @version 2019-04-08
 */
@Service
@Transactional(readOnly = true)
public class JcdaSbmcView1Service extends TreeService<JcdaSbmcView1Mapper, JcdaSbmcView1> {

	public JcdaSbmcView1 get(String id) {
		return super.get(id);
	}
	
	public List<JcdaSbmcView1> findList(JcdaSbmcView1 jcdaSbmcView1) {
		if (StringUtils.isNotBlank(jcdaSbmcView1.getParentIds())){
			jcdaSbmcView1.setParentIds(","+jcdaSbmcView1.getParentIds()+",");
		}
		return super.findList(jcdaSbmcView1);
	}
	
	@Transactional(readOnly = false)
	public void save(JcdaSbmcView1 jcdaSbmcView1) {
		super.save(jcdaSbmcView1);
	}
	
	@Transactional(readOnly = false)
	public void delete(JcdaSbmcView1 jcdaSbmcView1) {
		super.delete(jcdaSbmcView1);
	}
	
}