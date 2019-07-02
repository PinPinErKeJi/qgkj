/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a> All rights reserved.
 */
package com.jeeplus.modules.scgl_rkyq_view.web;

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
import com.jeeplus.modules.scgl_rkyq_view.entity.Scglrkyqview;
import com.jeeplus.modules.scgl_rkyq_view.service.ScglrkyqviewService;

/**
 * 员工考勤Controller
 * @author ww
 * @version 2019-06-02
 */
@Controller
@RequestMapping(value = "${adminPath}/scgl_rkyq_view/scglrkyqview")
public class ScglrkyqviewController extends BaseController {

	@Autowired
	private ScglrkyqviewService scglrkyqviewService;
	
	@ModelAttribute
	public Scglrkyqview get(@RequestParam(required=false) String id) {
		Scglrkyqview entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = scglrkyqviewService.get(id);
		}
		if (entity == null){
			entity = new Scglrkyqview();
		}
		return entity;
	}
	
	/**
	 * 员工考勤列表页面
	 */
	@RequiresPermissions("scgl_rkyq_view:scglrkyqview:list")
	@RequestMapping(value = {"list", ""})
	public String list(Scglrkyqview scglrkyqview, Model model) {
		model.addAttribute("scglrkyqview", scglrkyqview);
		return "modules/scgl_rkyq_view/scglrkyqviewList";
	}
	
		/**
	 * 员工考勤列表数据
	 */
	@ResponseBody
	@RequiresPermissions("scgl_rkyq_view:scglrkyqview:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Scglrkyqview scglrkyqview, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Scglrkyqview> page = scglrkyqviewService.findPage(new Page<Scglrkyqview>(request, response), scglrkyqview); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑员工考勤表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	@RequiresPermissions(value={"scgl_rkyq_view:scglrkyqview:view","scgl_rkyq_view:scglrkyqview:add","scgl_rkyq_view:scglrkyqview:edit"},logical=Logical.OR)
	@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, Scglrkyqview scglrkyqview, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("scglrkyqview", scglrkyqview);
		return "modules/scgl_rkyq_view/scglrkyqviewForm";
	}

	/**
	 * 保存员工考勤
	 */
	@ResponseBody
	@RequiresPermissions(value={"scgl_rkyq_view:scglrkyqview:add","scgl_rkyq_view:scglrkyqview:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Scglrkyqview scglrkyqview, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(scglrkyqview);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		scglrkyqviewService.save(scglrkyqview);//保存
		j.setSuccess(true);
		j.setMsg("保存员工考勤成功");
		return j;
	}

	
	/**
	 * 批量删除员工考勤
	 */
	@ResponseBody
	@RequiresPermissions("scgl_rkyq_view:scglrkyqview:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			scglrkyqviewService.delete(scglrkyqviewService.get(id));
		}
		j.setMsg("删除员工考勤成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("scgl_rkyq_view:scglrkyqview:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(Scglrkyqview scglrkyqview, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工考勤"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Scglrkyqview> page = scglrkyqviewService.findPage(new Page<Scglrkyqview>(request, response, -1), scglrkyqview);
    		new ExportExcel("员工考勤", Scglrkyqview.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工考勤记录失败！失败信息："+e.getMessage());
		}
			return j;
    }
    
    @ResponseBody
    @RequestMapping(value = "detail")
	public Scglrkyqview detail(String id) {
		return scglrkyqviewService.get(id);
	}
	

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("scgl_rkyq_view:scglrkyqview:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Scglrkyqview> list = ei.getDataList(Scglrkyqview.class);
			for (Scglrkyqview scglrkyqview : list){
				try{
					scglrkyqviewService.save(scglrkyqview);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条员工考勤记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条员工考勤记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入员工考勤失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入员工考勤数据模板
	 */
	@ResponseBody
	@RequiresPermissions("scgl_rkyq_view:scglrkyqview:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工考勤数据导入模板.xlsx";
    		List<Scglrkyqview> list = Lists.newArrayList(); 
    		new ExportExcel("员工考勤数据", Scglrkyqview.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }
	

}