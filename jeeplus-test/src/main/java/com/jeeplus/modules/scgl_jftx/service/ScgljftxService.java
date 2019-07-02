/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_jftx.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.scgl_jftx.entity.Scgljftx;
import com.jeeplus.modules.scgl_jftx.mapper.ScgljftxMapper;

/**
 * 缴费提醒Service
 * @author ww
 * @version 2019-06-02
 */
@Service
@Transactional(readOnly = true)
public class ScgljftxService extends CrudService<ScgljftxMapper, Scgljftx> {

	public Scgljftx get(String id) {
		return super.get(id);
	}
	
	public List<Scgljftx> findList(Scgljftx scgljftx) {
		return super.findList(scgljftx);
	}
	
	public Page<Scgljftx> findPage(Page<Scgljftx> page, Scgljftx scgljftx) {
		return super.findPage(page, scgljftx);
	}
	
	@Transactional(readOnly = false)
	public void save(Scgljftx scgljftx) {
		super.save(scgljftx);
	}
	
	@Transactional(readOnly = false)
	public void delete(Scgljftx scgljftx) {
		super.delete(scgljftx);
	}
	
}