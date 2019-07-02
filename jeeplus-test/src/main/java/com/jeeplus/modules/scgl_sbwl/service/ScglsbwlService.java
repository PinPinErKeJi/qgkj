/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_sbwl.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.scgl_sbwl.entity.Scglsbwl;
import com.jeeplus.modules.scgl_sbwl.mapper.ScglsbwlMapper;

/**
 * 设备维修Service
 * @author ww
 * @version 2019-06-02
 */
@Service
@Transactional(readOnly = true)
public class ScglsbwlService extends CrudService<ScglsbwlMapper, Scglsbwl> {

	public Scglsbwl get(String id) {
		return super.get(id);
	}
	
	public List<Scglsbwl> findList(Scglsbwl scglsbwl) {
		return super.findList(scglsbwl);
	}
	
	public Page<Scglsbwl> findPage(Page<Scglsbwl> page, Scglsbwl scglsbwl) {
		return super.findPage(page, scglsbwl);
	}
	
	@Transactional(readOnly = false)
	public void save(Scglsbwl scglsbwl) {
		super.save(scglsbwl);
	}
	
	@Transactional(readOnly = false)
	public void delete(Scglsbwl scglsbwl) {
		super.delete(scglsbwl);
	}
	
}