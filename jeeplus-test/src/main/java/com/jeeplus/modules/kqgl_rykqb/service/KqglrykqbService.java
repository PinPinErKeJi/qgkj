/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.kqgl_rykqb.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.kqgl_rykqb.entity.Kqglrykqb;
import com.jeeplus.modules.kqgl_rykqb.mapper.KqglrykqbMapper;

/**
 * 员工考勤表Service
 * @author ww
 * @version 2019-04-17
 */
@Service
@Transactional(readOnly = true)
public class KqglrykqbService extends CrudService<KqglrykqbMapper, Kqglrykqb> {

	public Kqglrykqb get(String id) {
		return super.get(id);
	}
	
	public List<Kqglrykqb> findList(Kqglrykqb kqglrykqb) {
		return super.findList(kqglrykqb);
	}
	
	public Page<Kqglrykqb> findPage(Page<Kqglrykqb> page, Kqglrykqb kqglrykqb) {
		return super.findPage(page, kqglrykqb);
	}
	
	@Transactional(readOnly = false)
	public void save(Kqglrykqb kqglrykqb) {
		super.save(kqglrykqb);
	}
	
	@Transactional(readOnly = false)
	public void delete(Kqglrykqb kqglrykqb) {
		super.delete(kqglrykqb);
	}
	
}