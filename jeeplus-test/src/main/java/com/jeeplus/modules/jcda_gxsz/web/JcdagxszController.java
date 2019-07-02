/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_gxsz.web;

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
import com.jeeplus.modules.jcda_gxsz.entity.Jcdagxsz;
import com.jeeplus.modules.jcda_gxsz.service.JcdagxszService;

/**
 * 公休设置Controller
 * @author ww
 * @version 2019-04-18
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_gxsz/jcdagxsz")
public class JcdagxszController extends BaseController {

	@Autowired
	private JcdagxszService jcdagxszService;
	
	@ModelAttribute
	public Jcdagxsz get(@RequestParam(required=false) String id) {
		Jcdagxsz entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdagxszService.get(id);
		}
		if (entity == null){
			entity = new Jcdagxsz();
		}
		return entity;
	}
	
	/**
	 * 公休设置列表页面
	 */
	@RequiresPermissions("jcda_gxsz:jcdagxsz:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jcdagxsz jcdagxsz, Model model) {
		model.addAttribute("jcdagxsz", jcdagxsz);
		return "modules/jcda_gxsz/jcdagxszList";
	}
	
		/**
	 * 公休设置列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jcda_gxsz:jcdagxsz:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Jcdagxsz jcdagxsz, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jcdagxsz> page = jcdagxszService.findPage(new Page<Jcdagxsz>(request, response), jcdagxsz); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑公休设置表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"jcda_gxsz:jcdagxsz:view","jcda_gxsz:jcdagxsz:add","jcda_gxsz:jcdagxsz:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdagxsz jcdagxsz, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdagxsz", jcdagxsz);
		return "modules/jcda_gxsz/jcdagxszForm";
	}

	/**
	 * 保存公休设置
	 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_gxsz:jcdagxsz:add","jcda_gxsz:jcdagxsz:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdagxsz jcdagxsz, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdagxsz);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		jcdagxszService.save(jcdagxsz);//保存
		j.setSuccess(true);
		j.setMsg("保存公休设置成功");
		return j;
	}

	
	/**
	 * 批量删除公休设置
	 */
	@ResponseBody
	@RequiresPermissions("jcda_gxsz:jcdagxsz:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jcdagxszService.delete(jcdagxszService.get(id));
		}
		j.setMsg("删除公休设置成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_gxsz:jcdagxsz:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Jcdagxsz jcdagxsz, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "公休设置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jcdagxsz> page = jcdagxszService.findPage(new Page<Jcdagxsz>(request, response, -1), jcdagxsz);
    		new ExportExcel("公休设置", Jcdagxsz.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出公休设置记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_gxsz:jcdagxsz:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jcdagxsz> list = ei.getDataList(Jcdagxsz.class);
			for (Jcdagxsz jcdagxsz : list){
				try{
					jcdagxszService.save(jcdagxsz);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条公休设置记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条公休设置记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入公休设置失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入公休设置数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_gxsz:jcdagxsz:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "公休设置数据导入模板.xlsx";
    		List<Jcdagxsz> list = Lists.newArrayList(); 
    		new ExportExcel("公休设置数据", Jcdagxsz.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}