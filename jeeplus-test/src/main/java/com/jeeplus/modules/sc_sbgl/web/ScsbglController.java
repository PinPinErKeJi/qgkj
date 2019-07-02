/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.sc_sbgl.web;

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
import com.jeeplus.modules.sc_sbgl.entity.Scsbgl;
import com.jeeplus.modules.sc_sbgl.service.ScsbglService;

/**
 * 设备管理Controller
 * @author ww
 * @version 2019-05-31
 */
@Controller
@RequestMapping(value = "${adminPath}/sc_sbgl/scsbgl")
public class ScsbglController extends BaseController {

	@Autowired
	private ScsbglService scsbglService;
	
	@ModelAttribute
	public Scsbgl get(@RequestParam(required=false) String id) {
		Scsbgl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scsbglService.get(id);
		}
		if (entity == null){
			entity = new Scsbgl();
		}
		return entity;
	}
	
	/**
	 * 设备管理列表页面
	 */
	@RequiresPermissions("sc_sbgl:scsbgl:list")
	@RequestMapping(value = {"list", ""})
	public String list(Scsbgl scsbgl, Model model) {
		model.addAttribute("scsbgl", scsbgl);
		return "modules/sc_sbgl/scsbglList";
	}
	
		/**
	 * 设备管理列表数据
	 */
	@ResponseBody
	@RequiresPermissions("sc_sbgl:scsbgl:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Scsbgl scsbgl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Scsbgl> page = scsbglService.findPage(new Page<Scsbgl>(request, response), scsbgl); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑设备管理表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"sc_sbgl:scsbgl:view","sc_sbgl:scsbgl:add","sc_sbgl:scsbgl:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Scsbgl scsbgl, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("scsbgl", scsbgl);
		return "modules/sc_sbgl/scsbglForm";
	}

	/**
	 * 保存设备管理
	 */
	@ResponseBody
	@RequiresPermissions(value={"sc_sbgl:scsbgl:add","sc_sbgl:scsbgl:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Scsbgl scsbgl, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(scsbgl);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		scsbglService.save(scsbgl);//保存
		j.setSuccess(true);
		j.setMsg("保存设备管理成功");
		return j;
	}

	
	/**
	 * 批量删除设备管理
	 */
	@ResponseBody
	@RequiresPermissions("sc_sbgl:scsbgl:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			scsbglService.delete(scsbglService.get(id));
		}
		j.setMsg("删除设备管理成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("sc_sbgl:scsbgl:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Scsbgl scsbgl, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Scsbgl> page = scsbglService.findPage(new Page<Scsbgl>(request, response, -1), scsbgl);
    		new ExportExcel("设备管理", Scsbgl.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设备管理记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public Scsbgl detail(String id) {
		return scsbglService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("sc_sbgl:scsbgl:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Scsbgl> list = ei.getDataList(Scsbgl.class);
			for (Scsbgl scsbgl : list){
				try{
					scsbglService.save(scsbgl);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备管理记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条设备管理记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入设备管理失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入设备管理数据模板
	 */
	@ResponseBody
	@RequiresPermissions("sc_sbgl:scsbgl:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备管理数据导入模板.xlsx";
    		List<Scsbgl> list = Lists.newArrayList(); 
    		new ExportExcel("设备管理数据", Scsbgl.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}