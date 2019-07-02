/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_sbwl.web;

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
import com.jeeplus.modules.scgl_sbwl.entity.Scglsbwl;
import com.jeeplus.modules.scgl_sbwl.service.ScglsbwlService;

/**
 * 设备维修Controller
 * @author ww
 * @version 2019-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/scgl_sbwl/scglsbwl")
public class ScglsbwlController extends BaseController {

	@Autowired
	private ScglsbwlService scglsbwlService;
	
	@ModelAttribute
	public Scglsbwl get(@RequestParam(required=false) String id) {
		Scglsbwl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scglsbwlService.get(id);
		}
		if (entity == null){
			entity = new Scglsbwl();
		}
		return entity;
	}
	
	/**
	 * 设备维修列表页面
	 */
	@RequiresPermissions("scgl_sbwl:scglsbwl:list")
	@RequestMapping(value = {"list", ""})
	public String list(Scglsbwl scglsbwl, Model model) {
		model.addAttribute("scglsbwl", scglsbwl);
		return "modules/scgl_sbwl/scglsbwlList";
	}
	
		/**
	 * 设备维修列表数据
	 */
	@ResponseBody
	@RequiresPermissions("scgl_sbwl:scglsbwl:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Scglsbwl scglsbwl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Scglsbwl> page = scglsbwlService.findPage(new Page<Scglsbwl>(request, response), scglsbwl); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑设备维修表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"scgl_sbwl:scglsbwl:view","scgl_sbwl:scglsbwl:add","scgl_sbwl:scglsbwl:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Scglsbwl scglsbwl, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("scglsbwl", scglsbwl);
		return "modules/scgl_sbwl/scglsbwlForm";
	}

	/**
	 * 保存设备维修
	 */
	@ResponseBody
	@RequiresPermissions(value={"scgl_sbwl:scglsbwl:add","scgl_sbwl:scglsbwl:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Scglsbwl scglsbwl, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(scglsbwl);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		scglsbwlService.save(scglsbwl);//保存
		j.setSuccess(true);
		j.setMsg("保存设备维修成功");
		return j;
	}

	
	/**
	 * 批量删除设备维修
	 */
	@ResponseBody
	@RequiresPermissions("scgl_sbwl:scglsbwl:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			scglsbwlService.delete(scglsbwlService.get(id));
		}
		j.setMsg("删除设备维修成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("scgl_sbwl:scglsbwl:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Scglsbwl scglsbwl, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备维修"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Scglsbwl> page = scglsbwlService.findPage(new Page<Scglsbwl>(request, response, -1), scglsbwl);
    		new ExportExcel("设备维修", Scglsbwl.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设备维修记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("scgl_sbwl:scglsbwl:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Scglsbwl> list = ei.getDataList(Scglsbwl.class);
			for (Scglsbwl scglsbwl : list){
				try{
					scglsbwlService.save(scglsbwl);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备维修记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条设备维修记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入设备维修失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入设备维修数据模板
	 */
	@ResponseBody
	@RequiresPermissions("scgl_sbwl:scglsbwl:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备维修数据导入模板.xlsx";
    		List<Scglsbwl> list = Lists.newArrayList(); 
    		new ExportExcel("设备维修数据", Scglsbwl.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}