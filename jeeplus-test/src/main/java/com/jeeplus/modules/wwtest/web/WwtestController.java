/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.wwtest.web;

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
import com.jeeplus.modules.wwtest.entity.Wwtest;
import com.jeeplus.modules.wwtest.service.WwtestService;

/**
 * wwtestController
 * @author ww
 * @version 2019-02-15
 */
@Controller
@RequestMapping(value = "${adminPath}/wwtest/wwtest")
public class WwtestController extends BaseController {

	@Autowired
	private WwtestService wwtestService;
	
	@ModelAttribute
	public Wwtest get(@RequestParam(required=false) String id) {
		Wwtest entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = wwtestService.get(id);
		}
		if (entity == null){
			entity = new Wwtest();
		}
		return entity;
	}
	
	/**
	 * wwtest列表页面
	 */
	@RequiresPermissions("wwtest:wwtest:list")
	@RequestMapping(value = {"list", ""})
	public String list(Wwtest wwtest, Model model) {
		model.addAttribute("wwtest", wwtest);
		return "modules/wwtest/wwtestList";
	}
	
		/**
	 * wwtest列表数据
	 */
	@ResponseBody
	@RequiresPermissions("wwtest:wwtest:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Wwtest wwtest, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Wwtest> page = wwtestService.findPage(new Page<Wwtest>(request, response), wwtest); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑wwtest表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"wwtest:wwtest:view","wwtest:wwtest:add","wwtest:wwtest:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Wwtest wwtest, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("wwtest", wwtest);
		return "modules/wwtest/wwtestForm";
	}

	/**
	 * 保存wwtest
	 */
	@ResponseBody
	@RequiresPermissions(value={"wwtest:wwtest:add","wwtest:wwtest:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Wwtest wwtest, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(wwtest);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		wwtestService.save(wwtest);//保存
		j.setSuccess(true);
		j.setMsg("保存wwtest成功");
		return j;
	}

	
	/**
	 * 批量删除wwtest
	 */
	@ResponseBody
	@RequiresPermissions("wwtest:wwtest:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			wwtestService.delete(wwtestService.get(id));
		}
		j.setMsg("删除wwtest成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("wwtest:wwtest:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Wwtest wwtest, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "wwtest"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Wwtest> page = wwtestService.findPage(new Page<Wwtest>(request, response, -1), wwtest);
    		new ExportExcel("wwtest", Wwtest.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出wwtest记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("wwtest:wwtest:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Wwtest> list = ei.getDataList(Wwtest.class);
			for (Wwtest wwtest : list){
				try{
					wwtestService.save(wwtest);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条wwtest记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条wwtest记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入wwtest失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入wwtest数据模板
	 */
	@ResponseBody
	@RequiresPermissions("wwtest:wwtest:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "wwtest数据导入模板.xlsx";
    		List<Wwtest> list = Lists.newArrayList(); 
    		new ExportExcel("wwtest数据", Wwtest.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}