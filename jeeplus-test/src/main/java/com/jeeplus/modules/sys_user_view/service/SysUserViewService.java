/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.sys_user_view.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.database.datasource.annotation.DS;
import com.jeeplus.modules.sys_user_view.entity.SysUserView;
import com.jeeplus.modules.sys_user_view.mapper.SysUserViewMapper;

/**
 * sys_user_viewService
 * @author ww
 * @version 2019-02-16
 */
@DS("master")
@Service
@Transactional(readOnly = true)
public class SysUserViewService extends CrudService<SysUserViewMapper, SysUserView> {

	public SysUserView get(String id) {
		return super.get(id);
	}
	
	public List<SysUserView> findList(SysUserView sysUserView) {
		return super.findList(sysUserView);
	}
	
	public Page<SysUserView> findPage(Page<SysUserView> page, SysUserView sysUserView) {
		return super.findPage(page, sysUserView);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUserView sysUserView) {
		super.save(sysUserView);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUserView sysUserView) {
		super.delete(sysUserView);
	}
	
}