/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ygxx_view.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_ygxx_view.entity.JcdaYgxxView;
import com.jeeplus.modules.jcda_ygxx_view.mapper.JcdaYgxxViewMapper;

/**
 * 人员信息档案Service
 * @author ww
 * @version 2019-04-17
 */
@Service
@Transactional(readOnly = true)
public class JcdaYgxxViewService extends CrudService<JcdaYgxxViewMapper, JcdaYgxxView> {

	public JcdaYgxxView get(String id) {
		return super.get(id);
	}
	
	public List<JcdaYgxxView> findList(JcdaYgxxView jcdaYgxxView) {
		return super.findList(jcdaYgxxView);
	}
	
	public Page<JcdaYgxxView> findPage(Page<JcdaYgxxView> page, JcdaYgxxView jcdaYgxxView) {
		return super.findPage(page, jcdaYgxxView);
	}
	
	@Transactional(readOnly = false)
	public void save(JcdaYgxxView jcdaYgxxView) {
		super.save(jcdaYgxxView);
	}
	
	@Transactional(readOnly = false)
	public void delete(JcdaYgxxView jcdaYgxxView) {
		super.delete(jcdaYgxxView);
	}
	
}