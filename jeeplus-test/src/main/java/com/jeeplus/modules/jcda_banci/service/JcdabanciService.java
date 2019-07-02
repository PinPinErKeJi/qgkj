/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_banci.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.jcda_banci.entity.BanCiBean;
import com.jeeplus.modules.jcda_banci.entity.Jcdabanci;
import com.jeeplus.modules.jcda_banci.mapper.JcdabanciMapper;

/**
 * 班次设置Service
 * @author ww
 * @version 2019-04-16
 */
@Service
@Transactional(readOnly = true)
public class JcdabanciService extends CrudService<JcdabanciMapper, Jcdabanci> {

	public Jcdabanci get(String id) {
		return super.get(id);
	}
	
	public List<Jcdabanci> findList(Jcdabanci jcdabanci) {
		return super.findList(jcdabanci);
	}
	
	public Page<Jcdabanci> findPage(Page<Jcdabanci> page, Jcdabanci jcdabanci) {
		return super.findPage(page, jcdabanci);
	}
	
	@Transactional(readOnly = false)
	public void save(Jcdabanci jcdabanci) {
		super.save(jcdabanci);
	}
	
	@Transactional(readOnly = false)
	public void delete(Jcdabanci jcdabanci) {
		super.delete(jcdabanci);
	}
	
	public HashMap<String, BanCiBean> getBancis(){
		List<BanCiBean> list = mapper.findAllBanci();
		if(null == list||list.isEmpty()) return null;
		HashMap<String, BanCiBean> rs = new HashMap<>();
		list.forEach(e->{
			rs.put(e.getType(), e);
		});
		return rs;
	}
}