/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.wwtest.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.wwtest.entity.Wwtest;
import com.jeeplus.modules.wwtest.mapper.WwtestMapper;

/**
 * wwtestService
 * @author ww
 * @version 2019-02-15
 */
@DS("qnsql")
@Service
@Transactional(readOnly = true)
public class WwtestService extends CrudService<WwtestMapper, Wwtest> {

	public Wwtest get(String id) {
		return super.get(id);
	}
	
	public List<Wwtest> findList(Wwtest wwtest) {
		return super.findList(wwtest);
	}
	
	public Page<Wwtest> findPage(Page<Wwtest> page, Wwtest wwtest) {
		return super.findPage(page, wwtest);
	}
	
	@Transactional(readOnly = false)
	public void save(Wwtest wwtest) {
		super.save(wwtest);
	}
	
	@Transactional(readOnly = false)
	public void delete(Wwtest wwtest) {
		super.delete(wwtest);
	}
	
}