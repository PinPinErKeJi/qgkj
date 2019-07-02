/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_hmd.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.scgl_hmd.entity.Scglhmd;
import com.jeeplus.modules.scgl_hmd.mapper.ScglhmdMapper;

/**
 * 黑名单Service
 * @author ww
 * @version 2019-06-02
 */
@Service
@Transactional(readOnly = true)
public class ScglhmdService extends CrudService<ScglhmdMapper, Scglhmd> {

	public Scglhmd get(String id) {
		return super.get(id);
	}
	
	public List<Scglhmd> findList(Scglhmd scglhmd) {
		return super.findList(scglhmd);
	}
	
	public Page<Scglhmd> findPage(Page<Scglhmd> page, Scglhmd scglhmd) {
		return super.findPage(page, scglhmd);
	}
	
	@Transactional(readOnly = false)
	public void save(Scglhmd scglhmd) {
		super.save(scglhmd);
	}
	
	@Transactional(readOnly = false)
	public void delete(Scglhmd scglhmd) {
		super.delete(scglhmd);
	}
	
}