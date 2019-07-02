/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbyhxx.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.jcda_sbyhxx.entity.JcdaSbmcView;
import com.jeeplus.modules.jcda_sbyhxx.mapper.JcdaSbmcViewMapper;

/**
 * 设备用户信息Service
 * @author ww
 * @version 2019-04-08
 */
@Service
@Transactional(readOnly = true)
public class JcdaSbmcViewService extends TreeService<JcdaSbmcViewMapper, JcdaSbmcView> {

	public JcdaSbmcView get(String id) {
		return super.get(id);
	}
	
	public List<JcdaSbmcView> findList(JcdaSbmcView jcdaSbmcView) {
		if (StringUtils.isNotBlank(jcdaSbmcView.getParentIds())){
			jcdaSbmcView.setParentIds(","+jcdaSbmcView.getParentIds()+",");
		}
		return super.findList(jcdaSbmcView);
	}
	
	@Transactional(readOnly = false)
	public void save(JcdaSbmcView jcdaSbmcView) {
		super.save(jcdaSbmcView);
	}
	
	@Transactional(readOnly = false)
	public void delete(JcdaSbmcView jcdaSbmcView) {
		super.delete(jcdaSbmcView);
	}
	
}