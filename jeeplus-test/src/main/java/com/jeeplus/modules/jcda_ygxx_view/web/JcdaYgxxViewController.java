/**
 * Copyright &copy; 2015-2020 <a href="http://www.hzqnwl.com/">qnwl</a></a> All rights reserved.
 */
package com.jeeplus.modules.jcda_ygxx_view.web;

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
import com.jeeplus.modules.jcda_ygxx_view.entity.JcdaYgxxView;
import com.jeeplus.modules.jcda_ygxx_view.service.JcdaYgxxViewService;

/**
 * 人员信息档案Controller
 * @author ww
 * @version 2019-04-17
 */
@Controller
@RequestMapping(value = "${adminPath}/jcda_ygxx_view/jcdaYgxxView")
public class JcdaYgxxViewController extends BaseController {

	@Autowired
	private JcdaYgxxViewService jcdaYgxxViewService;
	
	@ModelAttribute
	public JcdaYgxxView get(@RequestParam(required=false) String id) {
		JcdaYgxxView entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = jcdaYgxxViewService.get(id);
		}
		if (entity == null){
			entity = new JcdaYgxxView();
		}
		return entity;
	}
	
	/**
	 * 人员信息档案列表页面
	 */
/*	@RequiresPermissions("jcda_ygxx_view:jcdaYgxxView:list")*/
	@RequestMapping(value = {"list", ""})
	public String list(JcdaYgxxView jcdaYgxxView, Model model) {
		model.addAttribute("jcdaYgxxView", jcdaYgxxView);
		return "modules/jcda_ygxx_view/jcdaYgxxViewList";
	}
	
		/**
	 * 人员信息档案列表数据
	 */
	@ResponseBody
	/*@RequiresPermissions("jcda_ygxx_view:jcdaYgxxView:list")*/
	@RequestMapping(value = "data")
	public Map<String, Object> data(JcdaYgxxView jcdaYgxxView, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<JcdaYgxxView> page = jcdaYgxxViewService.findPage(new Page<JcdaYgxxView>(request, response), jcdaYgxxView); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑人员信息档案表单页面
	 * params:
	 * 	mode: add, edit, view, 代表三种模式的页面
	 */
	/*@RequiresPermissions(value={"jcda_ygxx_view:jcdaYgxxView:view","jcda_ygxx_view:jcdaYgxxView:add","jcda_ygxx_view:jcdaYgxxView:edit"},logical=Logical.OR)
	*/@RequestMapping(value = "form/{mode}")
	public String form(@PathVariable String mode, JcdaYgxxView jcdaYgxxView, Model model) {
		model.addAttribute("mode", mode);
		model.addAttribute("jcdaYgxxView", jcdaYgxxView);
		return "modules/jcda_ygxx_view/jcdaYgxxViewForm";
	}

	/**
	 * 保存人员信息档案
	 */
	@ResponseBody
	/*@RequiresPermissions(value={"jcda_ygxx_view:jcdaYgxxView:add","jcda_ygxx_view:jcdaYgxxView:edit"},logical=Logical.OR)
	*/@RequestMapping(value = "save")
	public AjaxJson save(JcdaYgxxView jcdaYgxxView, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		/**
		 * 后台hibernate-validation插件校验
		 */
		String errMsg = beanValidator(jcdaYgxxView);
		if (StringUtils.isNotBlank(errMsg)){
			j.setSuccess(false);
			j.setMsg(errMsg);
			return j;
		}
		//新增或编辑表单保存
		jcdaYgxxViewService.save(jcdaYgxxView);//保存
		j.setSuccess(true);
		j.setMsg("保存人员信息档案成功");
		return j;
	}

	
	/**
	 * 批量删除人员信息档案
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ygxx_view:jcdaYgxxView:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			jcdaYgxxViewService.delete(jcdaYgxxViewService.get(id));
		}
		j.setMsg("删除人员信息档案成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ygxx_view:jcdaYgxxView:export")
    @RequestMapping(value = "export")
    public AjaxJson exportFile(JcdaYgxxView jcdaYgxxView, HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "人员信息档案"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<JcdaYgxxView> page = jcdaYgxxViewService.findPage(new Page<JcdaYgxxView>(request, response, -1), jcdaYgxxView);
    		new ExportExcel("人员信息档案", JcdaYgxxView.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出人员信息档案记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@ResponseBody
	@RequiresPermissions("jcda_ygxx_view:jcdaYgxxView:import")
    @RequestMapping(value = "import")
   	public AjaxJson importFile(@RequestParam("file")MultipartFile file, HttpServletResponse response, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<JcdaYgxxView> list = ei.getDataList(JcdaYgxxView.class);
			for (JcdaYgxxView jcdaYgxxView : list){
				try{
					jcdaYgxxViewService.save(jcdaYgxxView);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条人员信息档案记录。");
			}
			j.setMsg( "已成功导入 "+successNum+" 条人员信息档案记录"+failureMsg);
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导入人员信息档案失败！失败信息："+e.getMessage());
		}
		return j;
    }
	
	/**
	 * 下载导入人员信息档案数据模板
	 */
	@ResponseBody
	@RequiresPermissions("jcda_ygxx_view:jcdaYgxxView:import")
    @RequestMapping(value = "import/template")
     public AjaxJson importFileTemplate(HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "人员信息档案数据导入模板.xlsx";
    		List<JcdaYgxxView> list = Lists.newArrayList(); 
    		new ExportExcel("人员信息档案数据", JcdaYgxxView.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg( "导入模板下载失败！失败信息："+e.getMessage());
		}
		return j;
    }

}