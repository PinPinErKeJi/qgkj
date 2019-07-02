/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_jjrsz.web;

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
import com.jeeplus.modules.jcda_jjrsz.entity.Jcdajjrsz;
import com.jeeplus.modules.jcda_jjrsz.service.JcdajjrszService;

/**
 * 节假日设置Controller
 * @author ww
 * @version 2019-04-16
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_jjrsz/jcdajjrsz")
public class JcdajjrszController extends BaseController {

	@Autowired
	private JcdajjrszService jcdajjrszService;
	
	@ModelAttribute
	public Jcdajjrsz get(@RequestParam(required=false) String id) {
		Jcdajjrsz entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdajjrszService.get(id);
		}
		if (entity == null){
			entity = new Jcdajjrsz();
		}
		return entity;
	}
	
	/**
	 * 节假日设置列表页面
	 */
	@RequiresPermissions("jcda_jjrsz:jcdajjrsz:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jcdajjrsz jcdajjrsz, Model model) {
		model.addAttribute("jcdajjrsz", jcdajjrsz);
		return "modules/jcda_jjrsz/jcdajjrszList";
	}
	
		/**
	 * 节假日设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jcda_jjrsz:jcdajjrsz:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Jcdajjrsz jcdajjrsz, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jcdajjrsz> page = jcdajjrszService.findPage(new Page<Jcdajjrsz>(request, response), jcdajjrsz); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑节假日设置表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"jcda_jjrsz:jcdajjrsz:view","jcda_jjrsz:jcdajjrsz:add","jcda_jjrsz:jcdajjrsz:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdajjrsz jcdajjrsz, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdajjrsz", jcdajjrsz);
		return "modules/jcda_jjrsz/jcdajjrszForm";
	}

	/**
	 * 保存节假日设置
	 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_jjrsz:jcdajjrsz:add","jcda_jjrsz:jcdajjrsz:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdajjrsz jcdajjrsz, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdajjrsz);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		jcdajjrszService.save(jcdajjrsz);//保存
		j.setSuccess(true);
		j.setMsg("保存节假日设置成功");
		return j;
	}

	
	/**
	 * 批量删除节假日设置
	 */
	@ResponseBody
	@RequiresPermissions("jcda_jjrsz:jcdajjrsz:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jcdajjrszService.delete(jcdajjrszService.get(id));
		}
		j.setMsg("删除节假日设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_jjrsz:jcdajjrsz:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Jcdajjrsz jcdajjrsz, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "节假日设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jcdajjrsz> page = jcdajjrszService.findPage(new Page<Jcdajjrsz>(request, response, -1), jcdajjrsz);
    		new ExportExcel("节假日设置", Jcdajjrsz.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出节假日设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_jjrsz:jcdajjrsz:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jcdajjrsz> list = ei.getDataList(Jcdajjrsz.class);
			for (Jcdajjrsz jcdajjrsz : list){
				try{
					jcdajjrszService.save(jcdajjrsz);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条节假日设置记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条节假日设置记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入节假日设置失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入节假日设置数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_jjrsz:jcdajjrsz:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "节假日设置数据导入模板.xlsx";
    		List<Jcdajjrsz> list = Lists.newArrayList(); 
    		new ExportExcel("节假日设置数据", Jcdajjrsz.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}