/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_rkyq_view.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.scgl_rkyq_view.entity.Scglrkyqview;
import com.jeeplus.modules.scgl_rkyq_view.mapper.ScglrkyqviewMapper;

/**
 * 员工考勤Service
 * @author ww
 * @version 2019-06-02
 */
@Service
@Transactional(readOnly = true)
public class ScglrkyqviewService extends CrudService<ScglrkyqviewMapper, Scglrkyqview> {

	
	public Scglrkyqview get(String id) {
		Scglrkyqview scglrkyqview = super.get(id);
		return scglrkyqview;
	}
	
	public List<Scglrkyqview> findList(Scglrkyqview scglrkyqview) {
		return super.findList(scglrkyqview);
	}
	
	public Page<Scglrkyqview> findPage(Page<Scglrkyqview> page, Scglrkyqview scglrkyqview) {
		return super.findPage(page, scglrkyqview);
	}
	
	@Transactional(readOnly = false)
	public void save(Scglrkyqview scglrkyqview) {
		super.save(scglrkyqview);
	}
	
	@Transactional(readOnly = false)
	public void delete(Scglrkyqview scglrkyqview) {
		super.delete(scglrkyqview);
	}
	
}