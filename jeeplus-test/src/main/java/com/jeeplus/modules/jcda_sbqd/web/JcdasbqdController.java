/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_sbqd.web;

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
import com.jeeplus.modules.jcda_sbqd.entity.Jcdasbqd;
import com.jeeplus.modules.jcda_sbqd.service.JcdasbqdService;

/**
 * 设备清单Controller
 * @author ww
 * @version 2019-04-25
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_sbqd/jcdasbqd")
public class JcdasbqdController extends BaseController {

	@Autowired
	private JcdasbqdService jcdasbqdService;
	
	@ModelAttribute
	public Jcdasbqd get(@RequestParam(required=false) String id) {
		Jcdasbqd entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdasbqdService.get(id);
		}
		if (entity == null){
			entity = new Jcdasbqd();
		}
		return entity;
	}
	
	/**
	 * 设备清单列表页面
	 */
	@RequiresPermissions("jcda_sbqd:jcdasbqd:list")
	@RequestMapping(value = {"list", ""})
	public String list(Jcdasbqd jcdasbqd, Model model) {
		model.addAttribute("jcdasbqd", jcdasbqd);
		return "modules/jcda_sbqd/jcdasbqdList";
	}
	
		/**
	 * 设备清单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbqd:jcdasbqd:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Jcdasbqd jcdasbqd, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Jcdasbqd> page = jcdasbqdService.findPage(new Page<Jcdasbqd>(request, response), jcdasbqd); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑设备清单表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"jcda_sbqd:jcdasbqd:view","jcda_sbqd:jcdasbqd:add","jcda_sbqd:jcdasbqd:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Jcdasbqd jcdasbqd, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdasbqd", jcdasbqd);
		return "modules/jcda_sbqd/jcdasbqdForm";
	}

	/**
	 * 保存设备清单
	 */
	@ResponseBody
	@RequiresPermissions(value={"jcda_sbqd:jcdasbqd:add","jcda_sbqd:jcdasbqd:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Jcdasbqd jcdasbqd, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdasbqd);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		jcdasbqdService.save(jcdasbqd);//保存
		j.setSuccess(true);
		j.setMsg("保存设备清单成功");
		return j;
	}

	
	/**
	 * 批量删除设备清单
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbqd:jcdasbqd:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jcdasbqdService.delete(jcdasbqdService.get(id));
		}
		j.setMsg("删除设备清单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbqd:jcdasbqd:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Jcdasbqd jcdasbqd, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备清单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Jcdasbqd> page = jcdasbqdService.findPage(new Page<Jcdasbqd>(request, response, -1), jcdasbqd);
    		new ExportExcel("设备清单", Jcdasbqd.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设备清单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbqd:jcdasbqd:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Jcdasbqd> list = ei.getDataList(Jcdasbqd.class);
			for (Jcdasbqd jcdasbqd : list){
				try{
					jcdasbqdService.save(jcdasbqd);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备清单记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条设备清单记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入设备清单失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入设备清单数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_sbqd:jcdasbqd:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备清单数据导入模板.xlsx";
    		List<Jcdasbqd> list = Lists.newArrayList(); 
    		new ExportExcel("设备清单数据", Jcdasbqd.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}