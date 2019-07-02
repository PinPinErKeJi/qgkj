/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_sbqk.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.scgl_sbqk.entity.Scglsbqk;
import com.jeeplus.modules.scgl_sbqk.mapper.ScglsbqkMapper;

/**
 * 设备使用情况Service
 * @author ww
 * @version 2019-06-02
 */
@Service
@Transactional(readOnly = true)
public class ScglsbqkService extends CrudService<ScglsbqkMapper, Scglsbqk> {

	public Scglsbqk get(String id) {
		return super.get(id);
	}
	
	public List<Scglsbqk> findList(Scglsbqk scglsbqk) {
		return super.findList(scglsbqk);
	}
	
	public Page<Scglsbqk> findPage(Page<Scglsbqk> page, Scglsbqk scglsbqk) {
		return super.findPage(page, scglsbqk);
	}
	
	@Transactional(readOnly = false)
	public void save(Scglsbqk scglsbqk) {
		super.save(scglsbqk);
	}
	
	@Transactional(readOnly = false)
	public void delete(Scglsbqk scglsbqk) {
		super.delete(scglsbqk);
	}
	
}