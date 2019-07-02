/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ygda.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.jeeplus.modules.jcda_ygda.entity.Jcdaryda;
import com.jeeplus.modules.jcda_ygda.service.JcdarydaService;

/**
 * 人员档案Controller
 * @author ww
 * @version 2019-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_ygda/jcdaryda")
public class JcdarydaController extends BaseController {

	@Autowired
	private JcdarydaService jcdarydaService;
	
	@ModelAttribute
	public Jcdaryda get(@RequestParam(required=false) String id) {
		Jcdaryda entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdarydaService.get(id);
		}
		if (entity == null){
			entity = new Jcdaryda();
		}
		return entity;
	}
	
	/**
	 * 人员档案列表页面
	 */
	@RequiresPermissions("jcda_ygda:jcdaryda:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jcdaryda jcdaryda, Model model) {
		model.addAttribute("jcdaryda", jcdaryda);
		return "modules/jcda_ygda/jcdarydaList";
	}
	
		/**
	 * 人员档案列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ygda:jcdaryda:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Jcdaryda jcdaryda, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jcdaryda> page = jcdarydaService.findPage(new Page<Jcdaryda>(request, response), jcdaryda); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑人员档案表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"jcda_ygda:jcdaryda:view","jcda_ygda:jcdaryda:add","jcda_ygda:jcdaryda:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdaryda jcdaryda, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdaryda", jcdaryda);
		return "modules/jcda_ygda/jcdarydaForm";
	}

	/**
	 * 保存人员档案
	 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_ygda:jcdaryda:add","jcda_ygda:jcdaryda:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdaryda jcdaryda, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdaryda);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		jcdarydaService.save(jcdaryda);//保存
		j.setSuccess(true);
		j.setMsg("保存人员档案成功");
		return j;
	}

	
	/**
	 * 批量删除人员档案
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ygda:jcdaryda:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jcdarydaService.delete(jcdarydaService.get(id));
		}
		j.setMsg("删除人员档案成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ygda:jcdaryda:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Jcdaryda jcdaryda, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "人员档案"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jcdaryda> page = jcdarydaService.findPage(new Page<Jcdaryda>(request, response, -1), jcdaryda);
    		new ExportExcel("人员档案", Jcdaryda.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出人员档案记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_ygda:jcdaryda:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jcdaryda> list = ei.getDataList(Jcdaryda.class);
			for (Jcdaryda jcdaryda : list){
				try{
					jcdarydaService.save(jcdaryda);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条人员档案记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条人员档案记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入人员档案失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入人员档案数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ygda:jcdaryda:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "人员档案数据导入模板.xlsx";
    		List<Jcdaryda> list = Lists.newArrayList(); 
    		new ExportExcel("人员档案数据", Jcdaryda.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}