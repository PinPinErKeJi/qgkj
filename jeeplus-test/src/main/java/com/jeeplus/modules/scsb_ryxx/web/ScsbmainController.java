/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.scsb_ryxx.web;

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
import com.jeeplus.modules.scsb_ryxx.entity.Scsbmain;
import com.jeeplus.modules.scsb_ryxx.service.ScsbmainService;

/**
 * 人员信息Controller
 * @author ww
 * @version 2019-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/scsb_ryxx/scsbmain")
public class ScsbmainController extends BaseController {

	@Autowired
	private ScsbmainService scsbmainService;
	
	@ModelAttribute
	public Scsbmain get(@RequestParam(required=false) String id) {
		Scsbmain entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scsbmainService.get(id);
		}
		if (entity == null){
			entity = new Scsbmain();
		}
		return entity;
	}
	
	/**
	 * 人员信息列表页面
	 */
	@RequiresPermissions("scsb_ryxx:scsbmain:list")
	@RequestMapping(value = {"list", ""})
	public String list(Scsbmain scsbmain, Model model) {
		model.addAttribute("scsbmain", scsbmain);
		return "modules/scsb_ryxx/scsbmainList";
	}
	
		/**
	 * 人员信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("scsb_ryxx:scsbmain:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Scsbmain scsbmain, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Scsbmain> page = scsbmainService.findPage(new Page<Scsbmain>(request, response), scsbmain); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑人员信息表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"scsb_ryxx:scsbmain:view","scsb_ryxx:scsbmain:add","scsb_ryxx:scsbmain:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Scsbmain scsbmain, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("scsbmain", scsbmain);
		return "modules/scsb_ryxx/scsbmainForm";
	}

	/**
	 * 保存人员信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"scsb_ryxx:scsbmain:add","scsb_ryxx:scsbmain:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Scsbmain scsbmain, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(scsbmain);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		scsbmainService.save(scsbmain);//保存
		j.setSuccess(true);
		j.setMsg("保存人员信息成功");
		return j;
	}

	
	/**
	 * 批量删除人员信息
	 */
	@ResponseBody
	@RequiresPermissions("scsb_ryxx:scsbmain:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			scsbmainService.delete(scsbmainService.get(id));
		}
		j.setMsg("删除人员信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("scsb_ryxx:scsbmain:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Scsbmain scsbmain, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "人员信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Scsbmain> page = scsbmainService.findPage(new Page<Scsbmain>(request, response, -1), scsbmain);
    		new ExportExcel("人员信息", Scsbmain.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出人员信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("scsb_ryxx:scsbmain:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Scsbmain> list = ei.getDataList(Scsbmain.class);
			for (Scsbmain scsbmain : list){
				try{
					scsbmainService.save(scsbmain);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条人员信息记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条人员信息记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入人员信息失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入人员信息数据模板
	 */
	@ResponseBody
	@RequiresPermissions("scsb_ryxx:scsbmain:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "人员信息数据导入模板.xlsx";
    		List<Scsbmain> list = Lists.newArrayList(); 
    		new ExportExcel("人员信息数据", Scsbmain.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}