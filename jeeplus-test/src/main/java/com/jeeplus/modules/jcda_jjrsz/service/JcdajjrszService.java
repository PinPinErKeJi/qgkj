/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_jjrsz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_jjrsz.entity.Jcdajjrsz;
import com.jeeplus.modules.jcda_jjrsz.mapper.JcdajjrszMapper;

/**
 * 节假日设置Service
 * @author ww
 * @version 2019-04-16
 */
@Service
@Transactional(readOnly = true)
public class JcdajjrszService extends CrudService<JcdajjrszMapper, Jcdajjrsz> {

	public Jcdajjrsz get(String id) {
		return super.get(id);
	}
	
	public List<Jcdajjrsz> findList(Jcdajjrsz jcdajjrsz) {
		return super.findList(jcdajjrsz);
	}
	
	public Page<Jcdajjrsz> findPage(Page<Jcdajjrsz> page, Jcdajjrsz jcdajjrsz) {
		return super.findPage(page, jcdajjrsz);
	}
	
	@Transactional(readOnly = false)
	public void save(Jcdajjrsz jcdajjrsz) {
		super.save(jcdajjrsz);
	}
	
	@Transactional(readOnly = false)
	public void delete(Jcdajjrsz jcdajjrsz) {
		super.delete(jcdajjrsz);
	}
	
}