/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbkqxx.web;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.jeeplus.common.json.AjaxJson;
import com.jeeplus.common.utils.DateUtils;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.utils.excel.ExportExcel;
import com.jeeplus.common.utils.excel.ImportExcel;
import com.jeeplus.core.persistence.Page;
import com.jeeplus.core.web.BaseController;
import com.jeeplus.modules.jcda_sbkqxx.entity.Jcdasbkqxx;
import com.jeeplus.modules.jcda_sbkqxx.service.JcdasbkqxxService;

/**
 * 设备考勤信息Controller
 * @author ww
 * @version 2019-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_sbkqxx/jcdasbkqxx")
public class JcdasbkqxxController extends BaseController {

	@Autowired
	private JcdasbkqxxService jcdasbkqxxService;
	
	@ModelAttribute
	public Jcdasbkqxx get(@RequestParam(required=false) String id) {
		Jcdasbkqxx entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdasbkqxxService.get(id);
		}
		if (entity == null){
			entity = new Jcdasbkqxx();
		}
		return entity;
	}
	
	/**
	 * 设备考勤信息列表页面
	 */
	@RequiresPermissions("jcda_sbkqxx:jcdasbkqxx:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jcdasbkqxx jcdasbkqxx, Model model) {
		model.addAttribute("jcdasbkqxx", jcdasbkqxx);
		return "modules/jcda_sbkqxx/jcdasbkqxxList";
	}
	
		/**
	 * 设备考勤信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbkqxx:jcdasbkqxx:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Jcdasbkqxx jcdasbkqxx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jcdasbkqxx> page = jcdasbkqxxService.findPage(new Page<Jcdasbkqxx>(request, response), jcdasbkqxx); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑设备考勤信息表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"jcda_sbkqxx:jcdasbkqxx:view","jcda_sbkqxx:jcdasbkqxx:add","jcda_sbkqxx:jcdasbkqxx:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdasbkqxx jcdasbkqxx, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdasbkqxx", jcdasbkqxx);
		return "modules/jcda_sbkqxx/jcdasbkqxxForm";
	}
	@ResponseBody
	@RequiresPermissions(value={"jcda_sbkqxx:jcdasbkqxx:add","jcda_sbkqxx:jcdasbkqxx:edit"},logical=Logical.OR)
	@RequestMapping(value = "synchronize")
	public AjaxJson synchronize(String sbId,String startTime,String endTime){
		AjaxJson j = new AjaxJson();
		if(StringUtils.isBlank(startTime)) {
			startTime = "0";
		}
		if(StringUtils.isBlank(endTime)) {
			endTime = "0";
		}
		try {
			jcdasbkqxxService.synchronize(sbId, startTime, endTime);
			j.setSuccess(true);
			j.setMsg("同步成功");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	@ResponseBody
	@RequiresPermissions(value={"jcda_sbkqxx:jcdasbkqxx:add","jcda_sbkqxx:jcdasbkqxx:edit"},logical=Logical.OR)
	@RequestMapping(value = "calculate")
	public AjaxJson calculate(String startTime,String endTime) {
		AjaxJson j = new AjaxJson();
		try {
			jcdasbkqxxService.calculate(startTime, endTime);
			j.setSuccess(true);
			j.setMsg("计算成功");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	/**
	 * 保存设备考勤信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_sbkqxx:jcdasbkqxx:add","jcda_sbkqxx:jcdasbkqxx:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdasbkqxx jcdasbkqxx, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdasbkqxx);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		jcdasbkqxxService.save(jcdasbkqxx);//保存
		j.setSuccess(true);
		j.setMsg("保存设备考勤信息成功");
		return j;
	}

	
	/**
	 * 批量删除设备考勤信息
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbkqxx:jcdasbkqxx:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jcdasbkqxxService.delete(jcdasbkqxxService.get(id));
		}
		j.setMsg("删除设备考勤信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbkqxx:jcdasbkqxx:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Jcdasbkqxx jcdasbkqxx, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备考勤信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jcdasbkqxx> page = jcdasbkqxxService.findPage(new Page<Jcdasbkqxx>(request, response, -1), jcdasbkqxx);
    		new ExportExcel("设备考勤信息", Jcdasbkqxx.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设备考勤信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbkqxx:jcdasbkqxx:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jcdasbkqxx> list = ei.getDataList(Jcdasbkqxx.class);
			for (Jcdasbkqxx jcdasbkqxx : list){
				try{
					jcdasbkqxxService.save(jcdasbkqxx);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备考勤信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条设备考勤信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入设备考勤信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入设备考勤信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbkqxx:jcdasbkqxx:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备考勤信息数据导入模板.xlsx";
    		List<Jcdasbkqxx> list = Lists.newArrayList(); 
    		new ExportExcel("设备考勤信息数据", Jcdasbkqxx.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
}