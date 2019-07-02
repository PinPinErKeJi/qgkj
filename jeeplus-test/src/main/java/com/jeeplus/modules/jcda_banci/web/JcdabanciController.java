/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_banci.web;

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
import com.jeeplus.modules.jcda_banci.entity.Jcdabanci;
import com.jeeplus.modules.jcda_banci.service.JcdabanciService;

/**
 * 班次设置Controller
 * @author ww
 * @version 2019-04-16
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_banci/jcdabanci")
public class JcdabanciController extends BaseController {

	@Autowired
	private JcdabanciService jcdabanciService;
	
	@ModelAttribute
	public Jcdabanci get(@RequestParam(required=false) String id) {
		Jcdabanci entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdabanciService.get(id);
		}
		if (entity == null){
			entity = new Jcdabanci();
		}
		return entity;
	}
	
	/**
	 * 班次设置列表页面
	 */
	@RequiresPermissions("jcda_banci:jcdabanci:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jcdabanci jcdabanci, Model model) {
		model.addAttribute("jcdabanci", jcdabanci);
		return "modules/jcda_banci/jcdabanciList";
	}
	
		/**
	 * 班次设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jcda_banci:jcdabanci:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Jcdabanci jcdabanci, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jcdabanci> page = jcdabanciService.findPage(new Page<Jcdabanci>(request, response), jcdabanci); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑班次设置表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"jcda_banci:jcdabanci:view","jcda_banci:jcdabanci:add","jcda_banci:jcdabanci:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdabanci jcdabanci, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdabanci", jcdabanci);
		return "modules/jcda_banci/jcdabanciForm";
	}

	/**
	 * 保存班次设置
	 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_banci:jcdabanci:add","jcda_banci:jcdabanci:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdabanci jcdabanci, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdabanci);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		jcdabanciService.save(jcdabanci);//保存
		j.setSuccess(true);
		j.setMsg("保存班次设置成功");
		return j;
	}

	
	/**
	 * 批量删除班次设置
	 */
	@ResponseBody
	@RequiresPermissions("jcda_banci:jcdabanci:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jcdabanciService.delete(jcdabanciService.get(id));
		}
		j.setMsg("删除班次设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_banci:jcdabanci:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Jcdabanci jcdabanci, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "班次设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jcdabanci> page = jcdabanciService.findPage(new Page<Jcdabanci>(request, response, -1), jcdabanci);
    		new ExportExcel("班次设置", Jcdabanci.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出班次设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_banci:jcdabanci:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jcdabanci> list = ei.getDataList(Jcdabanci.class);
			for (Jcdabanci jcdabanci : list){
				try{
					jcdabanciService.save(jcdabanci);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条班次设置记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条班次设置记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入班次设置失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入班次设置数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_banci:jcdabanci:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "班次设置数据导入模板.xlsx";
    		List<Jcdabanci> list = Lists.newArrayList(); 
    		new ExportExcel("班次设置数据", Jcdabanci.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}