/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbyhxx.web;

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
import com.jeeplus.modules.jcda_sbyhxx.entity.Jcdasbyhxx;
import com.jeeplus.modules.jcda_sbyhxx.service.JcdasbyhxxService;

/**
 * 设备用户信息Controller
 * @author ww
 * @version 2019-04-08
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_sbyhxx/jcdasbyhxx")
public class JcdasbyhxxController extends BaseController {

	@Autowired
	private JcdasbyhxxService jcdasbyhxxService;
	
	@ModelAttribute
	public Jcdasbyhxx get(@RequestParam(required=false) String id) {
		Jcdasbyhxx entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdasbyhxxService.get(id);
		}
		if (entity == null){
			entity = new Jcdasbyhxx();
		}
		return entity;
	}
	
	/**
	 * 设备用户信息列表页面
	 */
	@RequiresPermissions("jcda_sbyhxx:jcdasbyhxx:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jcdasbyhxx jcdasbyhxx, Model model) {
		model.addAttribute("jcdasbyhxx", jcdasbyhxx);
		return "modules/jcda_sbyhxx/jcdasbyhxxList";
	}
	
		/**
	 * 设备用户信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbyhxx:jcdasbyhxx:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Jcdasbyhxx jcdasbyhxx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jcdasbyhxx> page = jcdasbyhxxService.findPage(new Page<Jcdasbyhxx>(request, response), jcdasbyhxx); 
		return getBootstrapData(page);
	}
	
	
	/** 人员信息同步 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_sbyhxx:jcdasbyhxx:add","jcda_sbyhxx:jcdasbyhxx:edit"},logical=Logical.OR)
	@RequestMapping(value = "synchronize")
	public AjaxJson synchronize(String sbId) {
		AjaxJson j = new AjaxJson();
		try {
			jcdasbyhxxService.synchronize(sbId);
			j.setSuccess(true);
			j.setMsg("设备用户信息同步成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 查看，增加，编辑设备用户信息表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"jcda_sbyhxx:jcdasbyhxx:view","jcda_sbyhxx:jcdasbyhxx:add","jcda_sbyhxx:jcdasbyhxx:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdasbyhxx jcdasbyhxx, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdasbyhxx", jcdasbyhxx);
		return "modules/jcda_sbyhxx/jcdasbyhxxForm";
	}

	/**
	 * 保存设备用户信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_sbyhxx:jcdasbyhxx:add","jcda_sbyhxx:jcdasbyhxx:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdasbyhxx jcdasbyhxx, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdasbyhxx);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		try {
			jcdasbyhxxService.save(jcdasbyhxx);//保存
			j.setSuccess(true);
			j.setMsg("保存设备用户信息成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}
		return j;
	}

	
	/**
	 * 批量删除设备用户信息
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbyhxx:jcdasbyhxx:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		try {
			for(String id : idArray){
				jcdasbyhxxService.delete(jcdasbyhxxService.get(id));
			}
			j.setSuccess(true);
			j.setMsg("删除设备用户信息成功");
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg(e.getMessage());
		}
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbyhxx:jcdasbyhxx:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Jcdasbyhxx jcdasbyhxx, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备用户信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jcdasbyhxx> page = jcdasbyhxxService.findPage(new Page<Jcdasbyhxx>(request, response, -1), jcdasbyhxx);
    		new ExportExcel("设备用户信息", Jcdasbyhxx.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设备用户信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbyhxx:jcdasbyhxx:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jcdasbyhxx> list = ei.getDataList(Jcdasbyhxx.class);
			for (Jcdasbyhxx jcdasbyhxx : list){
				try{
					jcdasbyhxxService.save(jcdasbyhxx);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备用户信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条设备用户信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入设备用户信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入设备用户信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbyhxx:jcdasbyhxx:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备用户信息数据导入模板.xlsx";
    		List<Jcdasbyhxx> list = Lists.newArrayList(); 
    		new ExportExcel("设备用户信息数据", Jcdasbyhxx.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}