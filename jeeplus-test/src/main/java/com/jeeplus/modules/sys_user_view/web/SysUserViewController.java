/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.sys_user_view.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.modules.sys_user_view.entity.SysUserView;
import com.jeeplus.modules.sys_user_view.service.SysUserViewService;

/**
 * sys_user_viewController
 * @author ww
 * @version 2019-02-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys_user_view/sysUserView")
public class SysUserViewController extends BaseController {

	@Autowired
	private SysUserViewService sysUserViewService;
	
	@ModelAttribute
	public SysUserView get(@RequestParam(required=false) String id) {
		SysUserView entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserViewService.get(id);
		}
		if (entity == null){
			entity = new SysUserView();
		}
		return entity;
	}
	
	/**
	 * sys_user_view列表页面
	 */
	@RequiresPermissions("sys_user_view:sysUserView:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysUserView sysUserView, Model model) {
		model.addAttribute("sysUserView", sysUserView);
		return "modules/sys_user_view/sysUserViewList";
	}
	
		/**
	 * sys_user_view列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sys_user_view:sysUserView:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SysUserView sysUserView, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserView> page = sysUserViewService.findPage(new Page<SysUserView>(request, response), sysUserView); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑sys_user_view表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"sys_user_view:sysUserView:view","sys_user_view:sysUserView:add","sys_user_view:sysUserView:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, SysUserView sysUserView, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("sysUserView", sysUserView);
		return "modules/sys_user_view/sysUserViewForm";
	}

	/**
	 * 保存sys_user_view
	 */
	@ResponseBody
	@RequiresPermissions(value={"sys_user_view:sysUserView:add","sys_user_view:sysUserView:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(SysUserView sysUserView, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(sysUserView);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		sysUserViewService.save(sysUserView);//保存
		j.setSuccess(true);
		j.setMsg("保存sys_user_view成功");
		return j;
	}

	
	/**
	 * 批量删除sys_user_view
	 */
	@ResponseBody
	@RequiresPermissions("sys_user_view:sysUserView:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysUserViewService.delete(sysUserViewService.get(id));
		}
		j.setMsg("删除sys_user_view成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sys_user_view:sysUserView:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(SysUserView sysUserView, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "sys_user_view"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysUserView> page = sysUserViewService.findPage(new Page<SysUserView>(request, response, -1), sysUserView);
    		new ExportExcel("sys_user_view", SysUserView.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出sys_user_view记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("sys_user_view:sysUserView:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysUserView> list = ei.getDataList(SysUserView.class);
			for (SysUserView sysUserView : list){
				try{
					sysUserViewService.save(sysUserView);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条sys_user_view记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条sys_user_view记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入sys_user_view失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入sys_user_view数据模板
	 */
	@ResponseBody
	@RequiresPermissions("sys_user_view:sysUserView:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "sys_user_view数据导入模板.xlsx";
    		List<SysUserView> list = Lists.newArrayList(); 
    		new ExportExcel("sys_user_view数据", SysUserView.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}