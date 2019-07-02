/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_jftx.web;

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
import com.jeeplus.modules.scgl_jftx.entity.Scgljftx;
import com.jeeplus.modules.scgl_jftx.service.ScgljftxService;

/**
 * 缴费提醒Controller
 * @author ww
 * @version 2019-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/scgl_jftx/scgljftx")
public class ScgljftxController extends BaseController {

	@Autowired
	private ScgljftxService scgljftxService;
	
	@ModelAttribute
	public Scgljftx get(@RequestParam(required=false) String id) {
		Scgljftx entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scgljftxService.get(id);
		}
		if (entity == null){
			entity = new Scgljftx();
		}
		return entity;
	}
	
	/**
	 * 缴费提醒列表页面
	 */
	@RequiresPermissions("scgl_jftx:scgljftx:list")
	@RequestMapping(value = {"list", ""})
	public String list(Scgljftx scgljftx, Model model) {
		model.addAttribute("scgljftx", scgljftx);
		return "modules/scgl_jftx/scgljftxList";
	}
	
		/**
	 * 缴费提醒列表数据
	 */
	@ResponseBody
	@RequiresPermissions("scgl_jftx:scgljftx:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Scgljftx scgljftx, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Scgljftx> page = scgljftxService.findPage(new Page<Scgljftx>(request, response), scgljftx); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑缴费提醒表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"scgl_jftx:scgljftx:view","scgl_jftx:scgljftx:add","scgl_jftx:scgljftx:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Scgljftx scgljftx, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("scgljftx", scgljftx);
		return "modules/scgl_jftx/scgljftxForm";
	}

	/**
	 * 保存缴费提醒
	 */
	@ResponseBody
	@RequiresPermissions(value={"scgl_jftx:scgljftx:add","scgl_jftx:scgljftx:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Scgljftx scgljftx, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(scgljftx);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		scgljftxService.save(scgljftx);//保存
		j.setSuccess(true);
		j.setMsg("保存缴费提醒成功");
		return j;
	}

	
	/**
	 * 批量删除缴费提醒
	 */
	@ResponseBody
	@RequiresPermissions("scgl_jftx:scgljftx:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			scgljftxService.delete(scgljftxService.get(id));
		}
		j.setMsg("删除缴费提醒成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("scgl_jftx:scgljftx:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Scgljftx scgljftx, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "缴费提醒"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Scgljftx> page = scgljftxService.findPage(new Page<Scgljftx>(request, response, -1), scgljftx);
    		new ExportExcel("缴费提醒", Scgljftx.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出缴费提醒记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("scgl_jftx:scgljftx:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Scgljftx> list = ei.getDataList(Scgljftx.class);
			for (Scgljftx scgljftx : list){
				try{
					scgljftxService.save(scgljftx);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条缴费提醒记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条缴费提醒记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入缴费提醒失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入缴费提醒数据模板
	 */
	@ResponseBody
	@RequiresPermissions("scgl_jftx:scgljftx:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "缴费提醒数据导入模板.xlsx";
    		List<Scgljftx> list = Lists.newArrayList(); 
    		new ExportExcel("缴费提醒数据", Scgljftx.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}