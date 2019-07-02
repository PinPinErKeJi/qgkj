/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.workflow.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.service.CrudService;
import com.jeeplus.modules.workflow.entity.Workflow;
import com.jeeplus.modules.workflow.mapper.WorkflowMapper;
import com.jeeplus.database.datasource.annotation.DS;

/**
 * workflowService
 * @author ww
 * @version 2019-02-15
 */
@DS("master")
@Service
@Transactional(readOnly = true)
public class WorkflowService extends CrudService<WorkflowMapper, Workflow> {

	public Workflow get(String id) {
		return super.get(id);
	}
	
	public List<Workflow> findList(Workflow workflow) {
		return super.findList(workflow);
	}
	
	public Page<Workflow> findPage(Page<Workflow> page, Workflow workflow) {
		return super.findPage(page, workflow);
	}
	
	@Transactional(readOnly = false)
	public void save(Workflow workflow) {
		super.save(workflow);
	}
	
	@Transactional(readOnly = false)
	public void delete(Workflow workflow) {
		super.delete(workflow);
	}
	
}