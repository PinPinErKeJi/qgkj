/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_national.web;

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
import com.jeeplus.modules.jcda_national.entity.Jcdanational;
import com.jeeplus.modules.jcda_national.service.JcdanationalService;

/**
 * 民族Controller
 * @author ww
 * @version 2019-04-07
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_national/jcdanational")
public class JcdanationalController extends BaseController {

	@Autowired
	private JcdanationalService jcdanationalService;
	
	@ModelAttribute
	public Jcdanational get(@RequestParam(required=false) String id) {
		Jcdanational entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdanationalService.get(id);
		}
		if (entity == null){
			entity = new Jcdanational();
		}
		return entity;
	}
	
	/**
	 * 民族列表页面
	 */
	@RequiresPermissions("jcda_national:jcdanational:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jcdanational jcdanational, Model model) {
		model.addAttribute("jcdanational", jcdanational);
		return "modules/jcda_national/jcdanationalList";
	}
	
		/**
	 * 民族列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jcda_national:jcdanational:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Jcdanational jcdanational, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jcdanational> page = jcdanationalService.findPage(new Page<Jcdanational>(request, response), jcdanational); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑民族表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"jcda_national:jcdanational:view","jcda_national:jcdanational:add","jcda_national:jcdanational:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdanational jcdanational, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdanational", jcdanational);
		return "modules/jcda_national/jcdanationalForm";
	}

	/**
	 * 保存民族
	 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_national:jcdanational:add","jcda_national:jcdanational:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdanational jcdanational, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdanational);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		jcdanationalService.save(jcdanational);//保存
		j.setSuccess(true);
		j.setMsg("保存民族成功");
		return j;
	}

	
	/**
	 * 批量删除民族
	 */
	@ResponseBody
	@RequiresPermissions("jcda_national:jcdanational:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jcdanationalService.delete(jcdanationalService.get(id));
		}
		j.setMsg("删除民族成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_national:jcdanational:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Jcdanational jcdanational, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "民族"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jcdanational> page = jcdanationalService.findPage(new Page<Jcdanational>(request, response, -1), jcdanational);
    		new ExportExcel("民族", Jcdanational.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出民族记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_national:jcdanational:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jcdanational> list = ei.getDataList(Jcdanational.class);
			for (Jcdanational jcdanational : list){
				try{
					jcdanationalService.save(jcdanational);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条民族记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条民族记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入民族失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入民族数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_national:jcdanational:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "民族数据导入模板.xlsx";
    		List<Jcdanational> list = Lists.newArrayList(); 
    		new ExportExcel("民族数据", Jcdanational.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}