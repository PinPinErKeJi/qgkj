/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ygda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_ygda.entity.Jcdaryda;
import com.jeeplus.modules.jcda_ygda.mapper.JcdarydaMapper;

/**
 * 人员档案Service
 * @author ww
 * @version 2019-04-25
 */
@Service
@Transactional(readOnly = true)
public class JcdarydaService extends CrudService<JcdarydaMapper, Jcdaryda> {

	public Jcdaryda get(String id) {
		return super.get(id);
	}
	
	public List<Jcdaryda> findList(Jcdaryda jcdaryda) {
		return super.findList(jcdaryda);
	}
	
	public Page<Jcdaryda> findPage(Page<Jcdaryda> page, Jcdaryda jcdaryda) {
		return super.findPage(page, jcdaryda);
	}
	
	@Transactional(readOnly = false)
	public void save(Jcdaryda jcdaryda) {
		super.save(jcdaryda);
	}
	
	@Transactional(readOnly = false)
	public void delete(Jcdaryda jcdaryda) {
		super.delete(jcdaryda);
	}
	
}