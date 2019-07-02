/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.workflow.web;

import java.util.List;
import java.util.Map;


import com.google.common.collect.Maps;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.modules.act.service.ActProcessService;
import com.jeeplus.modules.act.service.ActTaskService;
import com.jeeplus.modules.workflow.entity.Workflow;
import com.jeeplus.modules.workflow.service.WorkflowService;

/**
 * workflowController
 * @author ww
 * @version 2019-02-15
 */
@Controller
@RequestMapping(value = "${adminPath}/workflow/workflow")
public class WorkflowController extends BaseController {

	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private ActProcessService actProcessService;
	@Autowired
	private ActTaskService actTaskService;
	
	@ModelAttribute
	public Workflow get(@RequestParam(required=false) String id) {
		Workflow entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = workflowService.get(id);
		}
		if (entity == null){
			entity = new Workflow();
		}
		return entity;
	}
	

	/**
	 * 查看，增加，编辑workflow表单页面
	 */
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Workflow workflow, Model model) {
		model.addAttribute("workflow", workflow);
		if("add".equals(mode) || "edit".equals(mode)){
			return "modules/workflow/workflowForm";
		}else{//audit
			return "modules/workflow/workflowAudit";
		}
	}

	/**
	 * 保存workflow
	 */
	@ResponseBody
	@RequestMapping(value = "save")
	public AjaxJson save(Workflow workflow, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(workflow);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}


		/**
		 * 流程审批
		 */
		if (StringUtils.isBlank(workflow.getId())){
			//新增或编辑表单保存
			workflowService.save(workflow);//保存
			// 启动流程
			ProcessDefinition p = actProcessService.getProcessDefinition(workflow.getAct().getProcDefId());
			String title = workflow.getCurrentUser().getName()+"在"+ DateUtils.getDateTime()+"发起"+p.getName();
			actTaskService.startProcess(p.getKey(),  "workflow", workflow.getId(), title);
			j.setMsg("发起流程审批成功!");
			j.getBody().put("targetUrl",  "/act/task/process/");
		}else{
			//新增或编辑表单保存
			workflowService.save(workflow);//保存
			workflow.getAct().setComment(("yes".equals(workflow.getAct().getFlag())?"[重新申请] ":"[销毁申请] "));
			// 完成流程任务
			Map<String, Object> vars = Maps.newHashMap();
			vars.put("reapply", "yes".equals(workflow.getAct().getFlag())? true : false);
			actTaskService.complete(workflow.getAct().getTaskId(), workflow.getAct().getProcInsId(), workflow.getAct().getComment(), workflow.getContent(), vars);
			j.setMsg("提交成功！");
			j.getBody().put("targetUrl",  "/act/task/todo/");
		}

		return j;
	}
	


}