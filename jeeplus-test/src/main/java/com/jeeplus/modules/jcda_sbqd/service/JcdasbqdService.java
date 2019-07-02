/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbqd.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_sbqd.entity.Jcdasbqd;
import com.jeeplus.modules.jcda_sbqd.mapper.JcdasbqdMapper;

/**
 * 设备清单Service
 * @author ww
 * @version 2019-04-25
 */
@Service
@Transactional(readOnly = true)
public class JcdasbqdService extends CrudService<JcdasbqdMapper, Jcdasbqd> {

	public Jcdasbqd get(String id) {
		return super.get(id);
	}
	
	public List<Jcdasbqd> findList(Jcdasbqd jcdasbqd) {
		return super.findList(jcdasbqd);
	}
	
	public Page<Jcdasbqd> findPage(Page<Jcdasbqd> page, Jcdasbqd jcdasbqd) {
		return super.findPage(page, jcdasbqd);
	}
	
	@Transactional(readOnly = false)
	public void save(Jcdasbqd jcdasbqd) {
		super.save(jcdasbqd);
	}
	
	@Transactional(readOnly = false)
	public void delete(Jcdasbqd jcdasbqd) {
		super.delete(jcdasbqd);
	}
	
}