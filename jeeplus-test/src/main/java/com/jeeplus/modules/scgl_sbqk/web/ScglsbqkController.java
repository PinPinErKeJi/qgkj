/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_sbqk.web;

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
import com.jeeplus.modules.scgl_sbqk.entity.Scglsbqk;
import com.jeeplus.modules.scgl_sbqk.service.ScglsbqkService;

/**
 * 设备使用情况Controller
 * @author ww
 * @version 2019-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/scgl_sbqk/scglsbqk")
public class ScglsbqkController extends BaseController {

	@Autowired
	private ScglsbqkService scglsbqkService;
	
	@ModelAttribute
	public Scglsbqk get(@RequestParam(required=false) String id) {
		Scglsbqk entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scglsbqkService.get(id);
		}
		if (entity == null){
			entity = new Scglsbqk();
		}
		return entity;
	}
	
	/**
	 * 设备使用情况列表页面
	 */
	@RequiresPermissions("scgl_sbqk:scglsbqk:list")
	@RequestMapping(value = {"list", ""})
	public String list(Scglsbqk scglsbqk, Model model) {
		model.addAttribute("scglsbqk", scglsbqk);
		return "modules/scgl_sbqk/scglsbqkList";
	}
	
		/**
	 * 设备使用情况列表数据
	 */
	@ResponseBody
	@RequiresPermissions("scgl_sbqk:scglsbqk:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Scglsbqk scglsbqk, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Scglsbqk> page = scglsbqkService.findPage(new Page<Scglsbqk>(request, response), scglsbqk); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑设备使用情况表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"scgl_sbqk:scglsbqk:view","scgl_sbqk:scglsbqk:add","scgl_sbqk:scglsbqk:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Scglsbqk scglsbqk, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("scglsbqk", scglsbqk);
		return "modules/scgl_sbqk/scglsbqkForm";
	}

	/**
	 * 保存设备使用情况
	 */
	@ResponseBody
	@RequiresPermissions(value={"scgl_sbqk:scglsbqk:add","scgl_sbqk:scglsbqk:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Scglsbqk scglsbqk, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(scglsbqk);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		scglsbqkService.save(scglsbqk);//保存
		j.setSuccess(true);
		j.setMsg("保存设备使用情况成功");
		return j;
	}

	
	/**
	 * 批量删除设备使用情况
	 */
	@ResponseBody
	@RequiresPermissions("scgl_sbqk:scglsbqk:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			scglsbqkService.delete(scglsbqkService.get(id));
		}
		j.setMsg("删除设备使用情况成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("scgl_sbqk:scglsbqk:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Scglsbqk scglsbqk, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备使用情况"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Scglsbqk> page = scglsbqkService.findPage(new Page<Scglsbqk>(request, response, -1), scglsbqk);
    		new ExportExcel("设备使用情况", Scglsbqk.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设备使用情况记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("scgl_sbqk:scglsbqk:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Scglsbqk> list = ei.getDataList(Scglsbqk.class);
			for (Scglsbqk scglsbqk : list){
				try{
					scglsbqkService.save(scglsbqk);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备使用情况记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条设备使用情况记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入设备使用情况失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入设备使用情况数据模板
	 */
	@ResponseBody
	@RequiresPermissions("scgl_sbqk:scglsbqk:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备使用情况数据导入模板.xlsx";
    		List<Scglsbqk> list = Lists.newArrayList(); 
    		new ExportExcel("设备使用情况数据", Scglsbqk.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}