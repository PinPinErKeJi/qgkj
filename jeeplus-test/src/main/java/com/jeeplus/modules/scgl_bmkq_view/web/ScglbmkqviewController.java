/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_bmkq_view.web;

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
import com.jeeplus.modules.scgl_bmkq_view.entity.Scglbmkqview;
import com.jeeplus.modules.scgl_bmkq_view.service.ScglbmkqviewService;

/**
 * 部门考勤Controller
 * @author ww
 * @version 2019-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/scgl_bmkq_view/scglbmkqview")
public class ScglbmkqviewController extends BaseController {

	@Autowired
	private ScglbmkqviewService scglbmkqviewService;
	
	@ModelAttribute
	public Scglbmkqview get(@RequestParam(required=false) String id) {
		Scglbmkqview entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scglbmkqviewService.get(id);
		}
		if (entity == null){
			entity = new Scglbmkqview();
		}
		return entity;
	}
	
	/**
	 * 部门考勤列表页面
	 */
	@RequiresPermissions("scgl_bmkq_view:scglbmkqview:list")
	@RequestMapping(value = {"list", ""})
	public String list(Scglbmkqview scglbmkqview, Model model) {
		model.addAttribute("scglbmkqview", scglbmkqview);
		return "modules/scgl_bmkq_view/scglbmkqviewList";
	}
	
		/**
	 * 部门考勤列表数据
	 */
	@ResponseBody
	@RequiresPermissions("scgl_bmkq_view:scglbmkqview:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Scglbmkqview scglbmkqview, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Scglbmkqview> page = scglbmkqviewService.findPage(new Page<Scglbmkqview>(request, response), scglbmkqview); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑部门考勤表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"scgl_bmkq_view:scglbmkqview:view","scgl_bmkq_view:scglbmkqview:add","scgl_bmkq_view:scglbmkqview:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Scglbmkqview scglbmkqview, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("scglbmkqview", scglbmkqview);
		return "modules/scgl_bmkq_view/scglbmkqviewForm";
	}

	/**
	 * 保存部门考勤
	 */
	@ResponseBody
	@RequiresPermissions(value={"scgl_bmkq_view:scglbmkqview:add","scgl_bmkq_view:scglbmkqview:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Scglbmkqview scglbmkqview, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(scglbmkqview);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		scglbmkqviewService.save(scglbmkqview);//保存
		j.setSuccess(true);
		j.setMsg("保存部门考勤成功");
		return j;
	}

	
	/**
	 * 批量删除部门考勤
	 */
	@ResponseBody
	@RequiresPermissions("scgl_bmkq_view:scglbmkqview:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			scglbmkqviewService.delete(scglbmkqviewService.get(id));
		}
		j.setMsg("删除部门考勤成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("scgl_bmkq_view:scglbmkqview:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Scglbmkqview scglbmkqview, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "部门考勤"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Scglbmkqview> page = scglbmkqviewService.findPage(new Page<Scglbmkqview>(request, response, -1), scglbmkqview);
    		new ExportExcel("部门考勤", Scglbmkqview.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出部门考勤记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("scgl_bmkq_view:scglbmkqview:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Scglbmkqview> list = ei.getDataList(Scglbmkqview.class);
			for (Scglbmkqview scglbmkqview : list){
				try{
					scglbmkqviewService.save(scglbmkqview);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条部门考勤记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条部门考勤记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入部门考勤失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入部门考勤数据模板
	 */
	@ResponseBody
	@RequiresPermissions("scgl_bmkq_view:scglbmkqview:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "部门考勤数据导入模板.xlsx";
    		List<Scglbmkqview> list = Lists.newArrayList(); 
    		new ExportExcel("部门考勤数据", Scglbmkqview.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}