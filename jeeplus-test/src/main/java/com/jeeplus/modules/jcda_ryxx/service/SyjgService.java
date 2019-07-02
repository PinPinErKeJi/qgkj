/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ryxx.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.service.TreeService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.jcda_ryxx.entity.Syjg;
import com.jeeplus.modules.jcda_ryxx.mapper.SyjgMapper;

/**
 * 人员信息Service
 * @author ww
 * @version 2019-04-07
 */
@Service
@Transactional(readOnly = true)
public class SyjgService extends TreeService<SyjgMapper, Syjg> {

	public Syjg get(String id) {
		return super.get(id);
	}
	
	public List<Syjg> findList(Syjg syjg) {
		if (StringUtils.isNotBlank(syjg.getParentIds())){
			syjg.setParentIds(","+syjg.getParentIds()+",");
		}
		return super.findList(syjg);
	}
	
	@Transactional(readOnly = false)
	public void save(Syjg syjg) {
		super.save(syjg);
	}
	
	@Transactional(readOnly = false)
	public void delete(Syjg syjg) {
		super.delete(syjg);
	}
	
}