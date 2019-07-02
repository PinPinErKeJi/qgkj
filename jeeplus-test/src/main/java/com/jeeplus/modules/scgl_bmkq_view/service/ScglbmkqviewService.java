/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_bmkq_view.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.scgl_bmkq_view.entity.Scglbmkqview;
import com.jeeplus.modules.scgl_bmkq_view.mapper.ScglbmkqviewMapper;

/**
 * 部门考勤Service
 * @author ww
 * @version 2019-06-02
 */
@Service
@Transactional(readOnly = true)
public class ScglbmkqviewService extends CrudService<ScglbmkqviewMapper, Scglbmkqview> {

	public Scglbmkqview get(String id) {
		return super.get(id);
	}
	
	public List<Scglbmkqview> findList(Scglbmkqview scglbmkqview) {
		return super.findList(scglbmkqview);
	}
	
	public Page<Scglbmkqview> findPage(Page<Scglbmkqview> page, Scglbmkqview scglbmkqview) {
		return super.findPage(page, scglbmkqview);
	}
	
	@Transactional(readOnly = false)
	public void save(Scglbmkqview scglbmkqview) {
		super.save(scglbmkqview);
	}
	
	@Transactional(readOnly = false)
	public void delete(Scglbmkqview scglbmkqview) {
		super.delete(scglbmkqview);
	}
	
}