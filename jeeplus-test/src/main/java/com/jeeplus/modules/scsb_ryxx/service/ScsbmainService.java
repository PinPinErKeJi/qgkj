/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.scsb_ryxx.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.scsb_ryxx.entity.Scsbmain;
import com.jeeplus.modules.scsb_ryxx.mapper.ScsbmainMapper;

/**
 * 人员信息Service
 * @author ww
 * @version 2019-06-02
 */
@Service
@Transactional(readOnly = true)
public class ScsbmainService extends CrudService<ScsbmainMapper, Scsbmain> {

	public Scsbmain get(String id) {
		return super.get(id);
	}
	
	public List<Scsbmain> findList(Scsbmain scsbmain) {
		return super.findList(scsbmain);
	}
	
	public Page<Scsbmain> findPage(Page<Scsbmain> page, Scsbmain scsbmain) {
		return super.findPage(page, scsbmain);
	}
	
	@Transactional(readOnly = false)
	public void save(Scsbmain scsbmain) {
		super.save(scsbmain);
	}
	
	@Transactional(readOnly = false)
	public void delete(Scsbmain scsbmain) {
		super.delete(scsbmain);
	}
	
}