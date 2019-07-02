/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbgl_view.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_sbgl_view.entity.JcdaSbglView;
import com.jeeplus.modules.jcda_sbgl_view.mapper.JcdaSbglViewMapper;

/**
 * 设备名称Service
 * @author ww
 * @version 2019-04-14
 */
@Service
@Transactional(readOnly = true)
public class JcdaSbglViewService extends CrudService<JcdaSbglViewMapper, JcdaSbglView> {

	public JcdaSbglView get(String id) {
		return super.get(id);
	}
	
	public List<JcdaSbglView> findList(JcdaSbglView jcdaSbglView) {
		return super.findList(jcdaSbglView);
	}
	
	public Page<JcdaSbglView> findPage(Page<JcdaSbglView> page, JcdaSbglView jcdaSbglView) {
		return super.findPage(page, jcdaSbglView);
	}
	
	@Transactional(readOnly = false)
	public void save(JcdaSbglView jcdaSbglView) {
		super.save(jcdaSbglView);
	}
	
	@Transactional(readOnly = false)
	public void delete(JcdaSbglView jcdaSbglView) {
		super.delete(jcdaSbglView);
	}
	
}