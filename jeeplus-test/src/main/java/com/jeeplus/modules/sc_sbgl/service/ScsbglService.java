/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.sc_sbgl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.sc_sbgl.entity.Scsbgl;
import com.jeeplus.modules.sc_sbgl.mapper.ScsbglMapper;

/**
 * 设备管理Service
 * @author ww
 * @version 2019-05-31
 */
@Service
@Transactional(readOnly = true)
public class ScsbglService extends CrudService<ScsbglMapper, Scsbgl> {

	
	public Scsbgl get(String id) {
		Scsbgl scsbgl = super.get(id);
		return scsbgl;
	}
	
	public List<Scsbgl> findList(Scsbgl scsbgl) {
		return super.findList(scsbgl);
	}
	
	public Page<Scsbgl> findPage(Page<Scsbgl> page, Scsbgl scsbgl) {
		return super.findPage(page, scsbgl);
	}
	
	@Transactional(readOnly = false)
	public void save(Scsbgl scsbgl) {
		super.save(scsbgl);
	}
	
	@Transactional(readOnly = false)
	public void delete(Scsbgl scsbgl) {
		super.delete(scsbgl);
	}

	//查询设备总个
	public int selectSbCount(String arry) {
		return mapper.selectSbCount(arry);
	}
	//修改设备状态
	@Transactional(readOnly=false)
	public void updateSbState(String state, String arry) {
		mapper.updateSbState(state, arry);
	}

}