/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbgl.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.jcda_sbgl.entity.Jcdasbglwz;
import com.jeeplus.modules.jcda_sbgl.mapper.JcdasbglwzMapper;

/**
 * 设备管理Service
 * @author ww
 * @version 2019-04-07
 */
@Service
@Transactional(readOnly = true)
public class JcdasbglwzService extends TreeService<JcdasbglwzMapper, Jcdasbglwz> {

	public Jcdasbglwz get(String id) {
		return super.get(id);
	}
	
	public List<Jcdasbglwz> findList(Jcdasbglwz jcdasbglwz) {
		if (StringUtils.isNotBlank(jcdasbglwz.getParentIds())){
			jcdasbglwz.setParentIds(","+jcdasbglwz.getParentIds()+",");
		}
		return super.findList(jcdasbglwz);
	}
	
	@Transactional(readOnly = false)
	public void save(Jcdasbglwz jcdasbglwz) {
		super.save(jcdasbglwz);
	}
	
	@Transactional(readOnly = false)
	public void delete(Jcdasbglwz jcdasbglwz) {
		super.delete(jcdasbglwz);
	}
	
}