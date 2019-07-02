/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_gxsz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_gxsz.entity.Jcdagxsz;
import com.jeeplus.modules.jcda_gxsz.mapper.JcdagxszMapper;

/**
 * 公休设置Service
 * @author ww
 * @version 2019-04-18
 */
@Service
@Transactional(readOnly = true)
public class JcdagxszService extends CrudService<JcdagxszMapper, Jcdagxsz> {

	public Jcdagxsz get(String id) {
		return super.get(id);
	}
	
	public List<Jcdagxsz> findList(Jcdagxsz jcdagxsz) {
		return super.findList(jcdagxsz);
	}
	
	public Page<Jcdagxsz> findPage(Page<Jcdagxsz> page, Jcdagxsz jcdagxsz) {
		return super.findPage(page, jcdagxsz);
	}
	
	@Transactional(readOnly = false)
	public void save(Jcdagxsz jcdagxsz) {
		super.save(jcdagxsz);
	}
	
	@Transactional(readOnly = false)
	public void delete(Jcdagxsz jcdagxsz) {
		super.delete(jcdagxsz);
	}
	
}