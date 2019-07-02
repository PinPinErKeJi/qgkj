/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_national.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_national.entity.Jcdanational;
import com.jeeplus.modules.jcda_national.mapper.JcdanationalMapper;

/**
 * 民族Service
 * @author ww
 * @version 2019-04-07
 */
@Service
@Transactional(readOnly = true)
public class JcdanationalService extends CrudService<JcdanationalMapper, Jcdanational> {

	public Jcdanational get(String id) {
		return super.get(id);
	}
	
	public List<Jcdanational> findList(Jcdanational jcdanational) {
		return super.findList(jcdanational);
	}
	
	public Page<Jcdanational> findPage(Page<Jcdanational> page, Jcdanational jcdanational) {
		return super.findPage(page, jcdanational);
	}
	
	@Transactional(readOnly = false)
	public void save(Jcdanational jcdanational) {
		super.save(jcdanational);
	}
	
	@Transactional(readOnly = false)
	public void delete(Jcdanational jcdanational) {
		super.delete(jcdanational);
	}
	
}